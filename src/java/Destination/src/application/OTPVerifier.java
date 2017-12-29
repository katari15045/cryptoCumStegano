package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class OTPVerifier implements EventHandler<ActionEvent>
{
	private TextField textFieldOTP = null;
	private String actualOTP = null;
	private String otpEntered = null;
	private Stage stage = null;
	
	public OTPVerifier(TextField textFieldOTP, String actualOTP, Stage stage)
	{
		this.textFieldOTP = textFieldOTP;
		this.actualOTP = actualOTP;
		this.stage = stage;
	}
	
	@Override
	public void handle(ActionEvent event)
	{
		Alert alert = null;
		EmailCollectorGUI otpSenderGUI = null;
		MyEmail myEmail = null;
		
		otpEntered = textFieldOTP.getText();
		
		if( actualOTP.equals(otpEntered) )
		{
			System.out.println("OTP Verified!\n");
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Step 2 Done!");
			alert.setHeaderText("Success!");
			alert.setContentText(EmailCollectorGUI.receiverEmailID + " Verified!");
			alert.show();
			
			myEmail = new MyEmail();
			System.out.println("Sending Public Key to " + EmailCollectorGUI.senderEmailID + "...");
			myEmail.sendAttachment( EmailCollectorGUI.senderEmailID, stage );
		}
		
		else
		{
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Step 3 failed!");
			alert.setHeaderText("Invalid OTP!");
			alert.setContentText("OTP Mismatched!");
			alert.show();
			System.out.println("Invalid OTP!");
			
			otpSenderGUI = new EmailCollectorGUI();
			otpSenderGUI.start(stage);
		}
	}
}

















