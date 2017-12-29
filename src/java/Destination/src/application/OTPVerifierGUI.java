package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class OTPVerifierGUI 
{
	private Label labelHeading = null;
	private Label labelContent = null;
	private Label labelOTP = null;
	private TextField textFieldOTP = null;
	private Button buttonOTP = null;
	private GridPane gridPane = null;
	private Scene scene = null;
	
	public void start(String actualOTP, Stage stage)
	{
		
		labelHeading = new Label();
		labelHeading.setText("Step 2");
		labelHeading.setFont( new Font(Constants.HEAD_SIZE) );
		labelHeading.setTextFill( Color.web(Constants.HEAD_COLOR) );
		labelContent = new Label();
		labelContent.setText("Enter the OTP sent to " + EmailCollectorGUI.receiverEmailID);
		labelContent.setTextFill( Color.web(Constants.HEAD_COLOR) );
		labelOTP = new Label();
		labelOTP.setText("OTP : ");
		textFieldOTP = new TextField();
		buttonOTP = new Button();
		buttonOTP.setText("Verify");
		buttonOTP.setOnAction( new OTPVerifier(textFieldOTP, actualOTP, stage) );
		
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
		
		scene = new Scene(gridPane, Constants.WIND_COLS, Constants.WIND_ROWS);
		stage.setScene(scene);
		stage.show();
	}
}
