/*
 *	Tutorial 	-> 	https://www.javatpoint.com/example-of-sending-attachment-with-email-using-java-mail-api
 *	Download mail.jar from http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-eeplat-419426.html#javamail-1.4.7-oth-JPR
 *  Place it here - /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext
 *
 */

import java.util.Random;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import java.lang.Thread;
import java.lang.Exception;

public class MyEmail
{
	private Thread thread = null;
	private ProgressIndicator progressIndicator = null;
	private Label labelProgress = null;
	private Button buttonNext = null;
	private GridPane gridPane = null;
	private Scene scene = null;
	private Stage stage = null;
	
	private BackGroundEmail backGroundEmail = null;
	private Thread backGroundThread = null;
	static String otp = null;

	void sendOTP(String toEmailID, Stage stage)
	{
		this.stage = stage;
		initialize();
		backGroundEmail = new BackGroundEmail(toEmailID, stage, BackGroundEmail.OTP, buttonNext);
		startBackGroundThread();
	}

	void sendAttachment(String toEmailID, Stage stage)
	{
		this.stage = stage;
		initialize();
		backGroundEmail = new BackGroundEmail(toEmailID, stage, BackGroundEmail.ATTACHMENT, buttonNext);
		startBackGroundThread();
	}

	private void startBackGroundThread()
	{
		backGroundThread = new Thread(backGroundEmail);
		progressIndicator.progressProperty().bind( backGroundEmail.progressProperty() );
		labelProgress.textProperty().bind( backGroundEmail.messageProperty() );
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
		buttonNext.setOnAction( new PostEmailHandler(stage) );

		gridPane = new GridPane();
		gridPane.add(progressIndicator, 0,  0);
		gridPane.add(labelProgress, 0, 1);
		gridPane.add(buttonNext, 0, 2);
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setMargin(labelProgress, new Insets(20, 0, 0, 0));
		GridPane.setMargin(buttonNext, new Insets(60, 0, 0, 0));
		
		scene = new Scene(gridPane, Constants.WIND_COLS, Constants.WIND_ROWS);
		stage.setScene(scene);
		stage.show();
	}
}

class BackGroundEmail extends Task<Void>
{
	private Stage stage = null;
	private String toEmailID = null;
	static int mode = -1;
	private Thread thread = null;
	private Button buttonNext = null;

	static int OTP = 0;
	static int ATTACHMENT = 1;

	public BackGroundEmail(String toEmailID, Stage stage, int mode, Button buttonNext)
	{
		this.toEmailID = toEmailID;
		this.stage = stage;
		BackGroundEmail.mode = mode;
		this.buttonNext = buttonNext;
	}

	@Override
	public Void call()
	{
		if( BackGroundEmail.mode == OTP )
		{
			sendOTP();
		}

		else if( BackGroundEmail.mode == ATTACHMENT )
		{
			sendAttachment();
		}

		return null;
	}	
	
	public void sendOTP()
	{
		OTPSender otpSender = null;
				
		try
		{
			updateMessage("Sending OTP to " + toEmailID + "...");	
			this.stage = stage;
			MyEmail.otp = getOTP();
		
			otpSender = new OTPSender(toEmailID);
			thread = new Thread(otpSender);
			thread.start();
			thread.join();

			updateMessage("OTP sent!");
			updateProgress(1.0, 1.0);
			buttonNext.setDisable(false);		
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void sendAttachment()
	{
		AttachmentSender attachmentSender = null;		
		this.stage = stage;

		try
		{	
			updateMessage("Sending public key to " + toEmailID + "...");	
			System.out.println("Sending public key to " + toEmailID + "...");
			attachmentSender = new AttachmentSender( toEmailID, MyKeyGenerator.getPublicKeyPath());
			thread = new Thread(attachmentSender);
			thread.start();
			thread.join();
			updateMessage("Public Key sent!");
			System.out.println("Public Key sent!\n");
			updateProgress(1.0, 1.0);
			buttonNext.setDisable(false);		
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
	
	private String getOTP()
	{
		Random random = null;
		Integer min = 9999;
		Integer max = 99999999;
		
		random = new Random();
		return String.valueOf( random.nextInt( (max - min)+1 ) + min );
		
	}

	@Override
        protected void updateProgress(double workDone, double max)
        {
                super.updateProgress(workDone, max);
        }

	@Override
        protected void updateMessage(String message)
        {
                super.updateMessage(message);
        }
	
}

class PostEmailHandler implements EventHandler<ActionEvent>
{
	private Stage stage = null;
	private String otp = null;

	public PostEmailHandler(Stage stage, String otp)
	{
		this.stage = stage;
		this.otp = otp;
	}

	public PostEmailHandler(Stage stage)
	{
		this.stage = stage;
	}

	@Override
	public void handle(ActionEvent event)
	{
		OTPVerifierGUI otpVerifierGUI = null;
		PublicKeyCollectorGUI publicKeyCollectorGUI = null;

		if(BackGroundEmail.mode == BackGroundEmail.OTP)
		{
			otpVerifierGUI = new OTPVerifierGUI();
	                otpVerifierGUI.start(MyEmail.otp, stage);
		}

		else if(BackGroundEmail.mode == BackGroundEmail.ATTACHMENT)
		{
			publicKeyCollectorGUI = new PublicKeyCollectorGUI();
			publicKeyCollectorGUI.start(stage);	
		}
	}
}




























