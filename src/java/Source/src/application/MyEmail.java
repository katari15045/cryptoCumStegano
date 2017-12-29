package application;

import java.util.Random;

/*
 *	Tutorial 	-> 	https://www.javatpoint.com/example-of-sending-attachment-with-email-using-java-mail-api
 *	Download mail.jar from http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-eeplat-419426.html#javamail-1.4.7-oth-JPR
 *  Place it here - /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext
 *
 */

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

public class MyEmail
{	
	private Thread thread = null;
	private ProgressIndicator progressIndicator = null;
	private Label label = null;
	private Button button = null;
	private GridPane gridPane = null;
	private Scene scene = null;
	private Stage stage = null;

	public void sendOTP(String toEmailID, Stage stage)
	{
		String otp = null;
		OTPSender otpSender = null;
		
		this.stage = stage;
		initialize();
		otp = getOTP();
		
		button.setText("Verify");
		button.setOnAction( new PostOTPHandler(otp, stage) );
		otpSender = new OTPSender(otp, toEmailID, button);
		progressIndicator.progressProperty().bind( otpSender.progressProperty() );
		label.textProperty().bind( otpSender.messageProperty() );
		thread = new Thread(otpSender);
		thread.start();
	}
	
	public void sendAttachment(String toEmailID, Stage stage)
	{
		AttachmentSender attachmentSender = null;
		
		this.stage = stage;
		initialize();
		
		attachmentSender = new AttachmentSender( toEmailID, KeyGenerator.getPublicKeyPath(), button);
		progressIndicator.progressProperty().bind( attachmentSender.progressProperty() );
		label.textProperty().bind( attachmentSender.messageProperty() );
		thread = new Thread(attachmentSender);
		thread.start();
		
		button.setOnAction( new PostAttachmentHandler() );
	}
	
	private String getOTP()
	{
		Random random = null;
		Integer min = 9999;
		Integer max = 99999999;
		
		random = new Random();
		return String.valueOf( random.nextInt( (max - min)+1 ) + min );
		
	}
	
	private void initialize()
	{
		progressIndicator = new ProgressIndicator();
		progressIndicator.setProgress(-1.0);
		label = new Label();
		button = new Button();
		button.setText("Next Step");
		button.setDisable(true);

		gridPane = new GridPane();
		gridPane.add(progressIndicator, 0,  0);
		gridPane.add(label, 0, 1);
		gridPane.add(button, 0, 2);
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setMargin(label, new Insets(20, 0, 0, 0));
		GridPane.setMargin(button, new Insets(60, 0, 0, 0));
		
		scene = new Scene(gridPane, Constants.WIND_COLS, Constants.WIND_ROWS);
		stage.setScene(scene);
		stage.show();
	}
}

class PostAttachmentHandler implements EventHandler<ActionEvent>
{	
	public void handle(ActionEvent event)
	{
		System.out.println("Need to implement it.");
	}
}

class PostOTPHandler implements EventHandler<ActionEvent>
{
	private String otp = null;
	
	private Stage stage = null;
	private OTPVerifierGUI otpVerifierGUI = null;
	
	public PostOTPHandler(String otp, Stage stage)
	{
		this.otp = otp;
		this.stage = stage;
	}
	
	@Override
	public void handle(ActionEvent event) 
	{   
        otpVerifierGUI = new OTPVerifierGUI();
        otpVerifierGUI.start(otp, stage);
	}
}
















