import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
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
		
		otpEntered = textFieldOTP.getText();
		
		if( actualOTP.equals(otpEntered) )
		{
			System.out.println("OTP Verified!\n");
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Step 2 Done!");
			alert.setHeaderText("Success!");
			alert.setContentText(EmailCumIPCollectorGUI.senderEmailID + " Verified!");
			alert.show();
			
			MyEmail.sendAttachment( EmailCumIPCollectorGUI.receiverEmailID, stage );
		}
		
		else
		{
			handleInvalidOTP();
		}
	}
	
	private void handleInvalidOTP()
	{
		Label label = null;
		Button buttonResend = null;
		Button buttonModifyEmail = null;
		GridPane gridPane = null;
		Scene scene = null;
		
		label = new Label();
		label.setText("Invalid OTP!");
		buttonResend = new Button();
		buttonResend.setText("Resend OTP");
		buttonResend.setOnAction( new OTPResender(stage) );
		buttonModifyEmail = new Button();
		buttonModifyEmail.setText("Change Email");
		buttonModifyEmail.setOnAction( new EmailChanger(stage) );
		
		gridPane = new GridPane();
		gridPane.add(label, 0, 0);
		gridPane.add(buttonResend, 0, 1);
		gridPane.add(buttonModifyEmail, 1, 1);
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setMargin(label, new Insets(60, 0, 0, 0));
		GridPane.setMargin(buttonResend, new Insets(30, 0, 0, 0));
		GridPane.setMargin(buttonModifyEmail, new Insets(30, 0, 0, 30));
		scene = new Scene(gridPane, Constants.WIND_COLS, Constants.WIND_ROWS);
		stage.setScene(scene);
		stage.show();
	}
}

class OTPResender implements EventHandler<ActionEvent>
{
	private Stage stage = null;
	private MyEmail myEmail = null;
	
	public OTPResender(Stage stage) 
	{
		this.stage = stage;
	}
	
	@Override
	public void handle(ActionEvent event) 
	{
		myEmail = new MyEmail();
		System.out.println("Sending OTP to " + EmailCumIPCollectorGUI.senderEmailID + "...");
		myEmail.sendOTP(EmailCumIPCollectorGUI.senderEmailID, stage);
	}
}

class EmailChanger implements EventHandler<ActionEvent>
{
	private Stage stage = null;
	private EmailCumIPCollectorGUI emailCollectorGUI = null;
	
	public EmailChanger(Stage stage) 
	{
		this.stage = stage;
	}
	
	@Override
	public void handle(ActionEvent event) 
	{
		emailCollectorGUI = new EmailCumIPCollectorGUI();
		emailCollectorGUI.start(stage);
	}
}
















