package application;

import java.util.Random;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class OTPSenderGUI 
{
	private Label labelHeading = null;
	private Label labelContent = null;
	private Label labelEmailID = null;
	private TextField textFieldEmailID = null;
	private Button buttonSendOTP = null;
	
	private String otp = null;
	
	private GridPane gridPane = null;
	private Scene scene = null;
	
	public void start(Stage stage)
	{
		labelHeading = new Label();
		labelHeading.setText("Step 2");
		labelHeading.setFont( new Font(20.0) );
		labelContent = new Label();
		labelContent.setText("Receive OTP");
		labelEmailID = new Label();
		labelEmailID.setText("Your Email ID : ");
		textFieldEmailID = new TextField();
		buttonSendOTP = new Button();
		buttonSendOTP.setText("Send OTP");
		otp = getOTP();
		buttonSendOTP.setOnAction(new MyEmail(textFieldEmailID, otp, MyEmail.OTP, stage) );
		
		gridPane = new GridPane();
		gridPane.add(labelHeading, 0, 0);
		gridPane.add(labelContent, 0, 1);
		gridPane.add(labelEmailID, 0, 2);
		gridPane.add(textFieldEmailID, 1, 2);
		gridPane.add(buttonSendOTP, 0, 3);
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setMargin(labelContent, new Insets(20, 0, 0, 0));
		GridPane.setMargin(labelEmailID, new Insets(60, 0, 0, 0));
		GridPane.setMargin(textFieldEmailID, new Insets(60, 0, 0, 0));
		GridPane.setMargin(buttonSendOTP, new Insets(60, 0, 0, 0));
		
		scene = new Scene(gridPane, 900, 480);
		stage.setScene(scene);
		stage.show();
	}
	
	private String getOTP()
	{
		Random random = null;
		Integer min = 9999;
		Integer max = 99999999;
		
		random = new Random();
		return String.valueOf( random.nextInt( (max - min)+1 ) + min );
		
	}
}
