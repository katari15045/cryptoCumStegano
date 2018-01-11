import javafx.application.Platform;
import java.lang.Thread;
import javafx.stage.Stage;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javax.crypto.spec.SecretKeySpec;
import javafx.concurrent.Task;
import java.lang.Exception;

public class SecureTunnelCreator
{
	private Stage stage = null;
	private ProgressIndicator progressIndicator = null;
	private Label labelProgress = null;
	private Button buttonNext = null;
	private GridPane gridPane = null;
	private Scene scene = null;

	private BackGroundTask backGroundTask = null;
	private Thread backGroundThread = null;
	static SecretKeySpec symKey = null;

	private DiffieHellman dh = null;
	private Thread dhThread = null;
	private MySocket socket = null;
	private Thread socketThread = null;

	public void start(Stage stage)
	{
		String dataToSend = null;

		this.stage = stage;
		initialize();
		backGroundThread.start();
	}

	private void initialize()
	{
		progressIndicator = new ProgressIndicator();
		progressIndicator.setProgress(-1.0);
		labelProgress = new Label();
		buttonNext = new Button();
		buttonNext.setText("Next");
		buttonNext.setDisable(true);
		buttonNext.setOnAction( new Finisher(stage) );

		gridPane = new GridPane();
		gridPane.add(progressIndicator, 0, 0);
		gridPane.add(labelProgress, 0, 1);
		gridPane.add(buttonNext, 0, 2);
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setMargin(progressIndicator, new Insets(30, 0, 0, 0));
		GridPane.setMargin(labelProgress, new Insets(20, 0, 0, 0));
		GridPane.setMargin(buttonNext, new Insets(60, 0, 0, 0));

		scene = new Scene(gridPane, Constants.WIND_COLS, Constants.WIND_ROWS);
		stage.setScene(scene);
		stage.show();
		
		socket = new MySocket();
		socketThread = new Thread(socket);
		dh = new DiffieHellman(socketThread, socket);
		dhThread = new Thread(dh);

		backGroundTask = new BackGroundTask(buttonNext, labelProgress, dh, dhThread);
		progressIndicator.progressProperty().bind( backGroundTask.progressProperty() );
		backGroundThread = new Thread(backGroundTask);

		labelProgress.textProperty().bind( dh.messageProperty() );
	}

	void updateMessage(String message)
	{
		dh.updateMessage(message);
	}

	void updateProgress(double workDone, double max)
	{
		backGroundTask.updateProgress(workDone, max);
	}
}

class BackGroundTask extends Task<Void>
{	
	private DiffieHellman dh = null;
	private Thread dhThread = null;
	private Button buttonNext = null;
	private Label labelProgress = null;
	private MessageExtractor messageExtractor = null;
	private Thread extractorThread = null;

	private MySocket socket = null;
	private Thread socketThread = null;
	private String locationStr = null, imgStr = null;

	public BackGroundTask(Button buttonNext, Label labelProgress, DiffieHellman dh, Thread dhThread)
	{
		this.buttonNext = buttonNext;
		this.labelProgress = labelProgress;
		this.dh = dh;
		this.dhThread = dhThread;
	}

	@Override
	public Void call()
	{
		byte[] secretBytes = null;		
		
		try
		{
			dhThread.start();
			dhThread.join();

			if( MySocket.status == MySocket.SERVER_UNAVAILABLE )
			{
				dh.updateMessage("Make sure the sender has uploaded the public key you've sent!");
				updateProgress(0.0, 1.0);
				buttonNext.setText("Retry");
				buttonNext.setDisable(false);
				return null;
			}

			secretBytes = dh.getSecret();
			dh.updateMessage("Generating Symmetric Key...");
			System.out.println("Generating Symmetric Key...");
			SecureTunnelCreator.symKey = new SecretKeySpec(secretBytes, 0, 32, Constants.SYM_ALGO);
			buttonNext.setDisable(false);
			dh.updateMessage("Symmetric Key Generated!");	
			System.out.println("Symmetric Key generated!\n");
			receiveData();

			messageExtractor = new MessageExtractor(locationStr, imgStr);
			extractorThread = new Thread(messageExtractor);
			dh.updateMessage("Extracting data...");
			extractorThread.start();
			extractorThread.join();

			buttonNext.setDisable(false);
			dh.updateMessage("Data extracted : " + MessageExtractor.extractedMessage);
			System.out.println("Extracted message : " + MessageExtractor.extractedMessage);
			updateProgress(1.0, 1.0);	
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	private void receiveData()
	{
		try
		{
			socket = new MySocket( DiffieHellman.getActiveSocket() );
			socket.setMode( MySocket.POST_SYM_KEY );
			socketThread = new Thread(socket);
			socketThread.start();
			socketThread.join();

			locationStr = socket.getData();
			imgStr = socket.getImageString();

		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
        protected void updateProgress(double workDone, double max)
        {
                super.updateProgress(workDone, max);
        }
}

class Finisher implements EventHandler<ActionEvent>
{
	private Stage stage = null;

	public Finisher(Stage stage)	
	{
		this.stage = stage;
	}

	@Override
	public void handle(ActionEvent event)
	{

		if( MySocket.status == MySocket.SERVER_UNAVAILABLE )
		{
			MyFileChooser.createSecureTunnel();
			return;
		}

		System.out.println("\nSee you!\n");
		Platform.exit();
		System.exit(0);
	}
}



















