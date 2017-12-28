package application;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MyEmail implements EventHandler<ActionEvent>
{
	public static final int OTP = 1;
	public static final int ATTACHMENT = 2;
	
	private TextField textFieldToEmail = null;
	private String otp = null;
	private String verifiedEmailID = null;
	private Integer emailType = null;
	private String filePath = null;
	private Stage stage = null;
	
	public MyEmail(TextField textFieldToEmail, String otp, String filePath, int emailType, Stage stage)
	{
		this.textFieldToEmail = textFieldToEmail;
		this.otp = otp;
		this.filePath = filePath;
		this.emailType = emailType;
		this.stage = stage;
	}
	
	public MyEmail(String verifiedEmailID, TextField textFieldToEmail, String filePath, int emailType, Stage stage)
	{
		this.textFieldToEmail =textFieldToEmail;
		this.verifiedEmailID = verifiedEmailID;
		this.filePath = filePath;
		this.emailType = emailType;
		this.stage = stage;
	}

	@Override
	public void handle(ActionEvent event)
	{	
		Thread thread = null;
		ProgressIndicator progressIndicator = null;
		Label label = null;
		Button button = null;
		
		GridPane gridPane = null;
		Scene scene = null;
		
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
		
		scene = new Scene(gridPane, 400, 200);
		stage.setScene(scene);
		stage.show();
		
		if( emailType == ATTACHMENT )
		{	
			AttachmentSender attachmentSender = null;
			
			attachmentSender = new AttachmentSender(verifiedEmailID, textFieldToEmail, filePath, button);
			progressIndicator.progressProperty().bind( attachmentSender.progressProperty() );
			label.textProperty().bind( attachmentSender.messageProperty() );
			thread = new Thread(attachmentSender);
			thread.start();
			
			button.setOnAction( new PostAttachmentHandler() );
		}
		
		else if( emailType == OTP )
		{
			OTPSender otpSender = null;
			
			button.setOnAction( new PostOTPHandler(otp, textFieldToEmail.getText(), filePath, stage) );
			otpSender = new OTPSender(otp, textFieldToEmail.getText(), button);
			progressIndicator.progressProperty().bind( otpSender.progressProperty() );
			label.textProperty().bind( otpSender.messageProperty() );
			thread = new Thread(otpSender);
			thread.start();
		}
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
	private String toEmail = null;
	private String filePath = null;
	
	private Stage stage = null;
	private OTPVerifierGUI otpVerifierGUI = null;
	
	public PostOTPHandler(String otp, String toEmail, String filePath, Stage stage)
	{
		this.otp = otp;
		this.toEmail = toEmail;
		this.filePath = filePath;
		this.stage = stage;
	}
	
	@Override
	public void handle(ActionEvent event) 
	{   
        otpVerifierGUI = new OTPVerifierGUI(otp, toEmail, filePath);
        otpVerifierGUI.start(stage);
	}
}
















