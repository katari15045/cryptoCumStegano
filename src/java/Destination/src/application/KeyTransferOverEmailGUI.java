package application;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class KeyTransferOverEmailGUI 
{
	private Label labelHeading = null;
	private Label labelContent = null;
	private Label labelToEmailID = null;
	private TextField textFieldToEmailID = null;
	private Button buttonChooseKeyFile = null;
	private Button buttonSendEmail = null;
	private String verifiedEmailID = null;
	
	private GridPane gridPane = null;
	private Scene scene = null;
	public static File publicKeyFile = null;
	
	public KeyTransferOverEmailGUI(String verifiedEmailID)
	{
		this.verifiedEmailID = verifiedEmailID;
	}
	
	public void start(Stage stage)
	{
		labelHeading = new Label();
		labelHeading.setText("Step 4");
		labelHeading.setFont( new Font(20.0) );
		labelContent = new Label();
		labelContent.setText("Transfer public key over Email");
		labelToEmailID = new Label();
		labelToEmailID.setText("Receiver's EmailID : ");
		textFieldToEmailID = new TextField();
		buttonChooseKeyFile = new Button();
		buttonChooseKeyFile.setText("Choose Public Key");
		buttonChooseKeyFile.setOnAction( new MyFileChooser(stage, "Choose Public Key", MyFileChooser.PUB_KEY) );
		buttonSendEmail = new Button();
		buttonSendEmail.setText("Send Email");
		buttonSendEmail.setOnAction( new MyEmail(verifiedEmailID, textFieldToEmailID, MyEmail.ATTACHMENT, stage) );
		
		gridPane = new GridPane();
		gridPane.add(labelHeading, 0, 0);
		gridPane.add(labelContent, 0, 1);
		gridPane.add(labelToEmailID, 0, 2);
		gridPane.add(textFieldToEmailID, 1, 2);
		gridPane.add(buttonChooseKeyFile, 0, 3);
		gridPane.add(buttonSendEmail, 0, 4);
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setMargin(labelContent, new Insets(20, 0, 0, 0));
		GridPane.setMargin(labelToEmailID, new Insets(60, 0, 0, 0));
		GridPane.setMargin(textFieldToEmailID, new Insets(60, 0, 0, 0));
		GridPane.setMargin(buttonChooseKeyFile, new Insets(20, 0, 0, 0));
		GridPane.setMargin(buttonSendEmail, new Insets(60, 0, 0, 0));
		scene = new Scene(gridPane, 900, 480);
		stage.setScene(scene);
		stage.show();
	}
}
