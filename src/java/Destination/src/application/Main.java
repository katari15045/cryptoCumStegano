package application;
	
import javafx.application.Application;
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

public class Main extends Application 
{	
	private ProgressIndicator progressIndicator = null;
	private Label label = null;
	private Button button = null;
	private GridPane gridPane = null;
	private Scene scene = null;
	
	private Thread thread = null;
	private KeyGenerator keyGenerator = null;
	
	@Override
	public void start(Stage stage) 
	{
		progressIndicator = new ProgressIndicator();
		progressIndicator.setProgress(-1.0);
		label = new Label();
		button = new Button();
		button.setText("Start!");
		button.setAlignment(Pos.CENTER_RIGHT);
		button.setOnAction( new Starter(stage) );
		
		gridPane = new GridPane();
		gridPane.add(progressIndicator, 0, 0);
		gridPane.add(label, 0, 1);
		gridPane.add(button, 0, 2);
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setMargin(progressIndicator, new Insets(60, 0, 0, 0));
		GridPane.setMargin(label, new Insets(20, 0, 0, 0));
		GridPane.setMargin(button, new Insets(60, 0, 60, 0));
		scene = new Scene(gridPane, Constants.WIND_COLS, Constants.WIND_ROWS);
		stage.setScene(scene);
		stage.show();
		
		keyGenerator = new KeyGenerator(button);
		progressIndicator.progressProperty().bind( keyGenerator.progressProperty() );
		label.textProperty().bind( keyGenerator.messageProperty() );
		thread = new Thread(keyGenerator);
		thread.start();
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}


class Starter implements EventHandler<ActionEvent>
{
	private Stage stage = null;
	private OTPSenderGUI otpSenderGUI = null;
	private String publicKeyPath = null;
	
	public Starter(Stage stage)
	{
		this.stage = stage;
	}
	
	public void handle(ActionEvent event)
	{
		publicKeyPath = KeyGenerator.getPublicKeyPath();
		otpSenderGUI = new OTPSenderGUI(publicKeyPath);
		otpSenderGUI.start(stage);
	}
}









