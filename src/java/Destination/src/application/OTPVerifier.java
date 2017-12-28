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
	private String verifyingEmailID = null;
	
	public OTPVerifier(TextField textFieldOTP, String actualOTP, String verifyingEmailID, Stage stage)
	{
		this.textFieldOTP = textFieldOTP;
		this.actualOTP = actualOTP;
		this.stage = stage;
		this.verifyingEmailID = verifyingEmailID;
	}
	
	@Override
	public void handle(ActionEvent event)
	{
		otpEntered = textFieldOTP.getText();
		Alert alert = null;
		KeyTransferOverEmailGUI keyTransferOverEmailGUI = null;
		OTPSenderGUI otpSenderGUI = null;
		
		if( actualOTP.equals(otpEntered) )
		{
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Step 3 Done!");
			alert.setHeaderText("Success!");
			alert.setContentText(verifyingEmailID + " Verified!");
			alert.show();
			
			keyTransferOverEmailGUI = new KeyTransferOverEmailGUI(verifyingEmailID);
			keyTransferOverEmailGUI.start(stage);
		}
		
		else
		{
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Step 3 failed!");
			alert.setHeaderText("Invalid OTP!");
			alert.setContentText("OTP sent doesn't match with the otp you've entered!");
			alert.show();
			
			otpSenderGUI = new OTPSenderGUI();
			otpSenderGUI.start(stage);
		}
	}
}

















