package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class KeyTransferOverEmailGUI 
{
	private Label labelHeading = null;
	private Label labelContent = null;
	private Label labelEmailID = null;
	private TextField textFieldEmailID = null;
	private Label labelPassword = null;
	private PasswordField passwordField = null;
	private Button buttonSendEmail;
	
	private GridPane gridPane = null;
	private Scene scene = null;
	
	public void start(Stage stage)
	{
		labelHeading = new Label();
		labelHeading.setText("Step 2.1");
		labelHeading.setFont( new Font(20.0) );
		labelContent = new Label();
		labelContent.setText("Transfer public key to the source over Email");
		labelEmailID = new Label();
		labelEmailID.setText("EmailID : ");
		textFieldEmailID = new TextField();
		labelPassword = new Label();
		labelPassword.setText("Password : ");
		passwordField = new PasswordField();
		buttonSendEmail = new Button();
		buttonSendEmail.setText("Send Email");
		
		gridPane = new GridPane();
		gridPane.add(labelHeading, 0, 0);
		gridPane.add(labelContent, 0, 1);
		gridPane.add(labelEmailID, 0, 2);
		gridPane.add(textFieldEmailID, 1, 2);
		gridPane.add(labelPassword, 0, 3);
		gridPane.add(passwordField, 1, 3);
		gridPane.add(buttonSendEmail, 0, 4);
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setMargin(labelContent, new Insets(20, 0, 0, 0));
		GridPane.setMargin(labelEmailID, new Insets(60, 0, 0, 0));
		GridPane.setMargin(textFieldEmailID, new Insets(60, 0, 0, 0));
		GridPane.setMargin(labelPassword, new Insets(20, 0, 0, 0));
		GridPane.setMargin(passwordField, new Insets(20, 0, 0, 0));
		GridPane.setMargin(buttonSendEmail, new Insets(30, 0, 0, 0));
		scene = new Scene(gridPane, 900, 480);
		stage.setScene(scene);
		stage.show();
	}
}
