package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class OTPVerifierGUI 
{
	private Label labelHeading = null;
	private Label labelContent = null;
	private Label labelOTP = null;
	private TextField textFieldOTP = null;
	private Button buttonOTP = null;
	private String actualOTP = null;
	private String verifyingEmailID = null;
	private String publicKeyPath = null;
	
	private GridPane gridPane = null;
	private Scene scene = null;
	
	public OTPVerifierGUI(String actualOTP, String verifyingEmailID, String publicKeyPath)
	{
		this.actualOTP = actualOTP;
		this.verifyingEmailID = verifyingEmailID;
		this.publicKeyPath = publicKeyPath;
	}
	
	public void start(Stage stage)
	{
		labelHeading = new Label();
		labelHeading.setText("Step 2");
		labelHeading.setFont( new Font(20.0) );
		labelContent = new Label();
		labelContent.setText("Verify your Email ID");
		labelOTP = new Label();
		labelOTP.setText("OTP : ");
		textFieldOTP = new TextField();
		buttonOTP = new Button();
		buttonOTP.setText("Verify");
		buttonOTP.setOnAction( new OTPVerifier(textFieldOTP, actualOTP, verifyingEmailID, publicKeyPath, stage) );
		
		gridPane = new GridPane();
		gridPane.add(labelHeading, 0, 0);
		gridPane.add(labelContent, 0, 1);
		gridPane.add(labelOTP, 0, 2);
		gridPane.add(textFieldOTP, 1, 2);
		gridPane.add(buttonOTP, 0, 3);
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setMargin(labelContent, new Insets(20, 0, 0, 0));
		GridPane.setMargin(labelOTP, new Insets(60, 0, 0, 0));
		GridPane.setMargin(textFieldOTP, new Insets(60, 0, 0, 0));
		GridPane.setMargin(buttonOTP, new Insets(60, 0, 0, 0));
		
		scene = new Scene(gridPane, 960, 540);
		stage.setScene(scene);
		stage.show();
	}
}
