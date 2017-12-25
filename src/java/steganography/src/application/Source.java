package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Source extends Application 
{
	Label labelMessage;
	TextField textFieldMessage;
	Button buttonChooseImage;
	Button buttonChooseKey;
	Button buttonEmbedMessage;
	
	MyFileChooser fileChooserImage;
	MyFileChooser fileChooserKey;
	MessageEmbedder messageEmbedder;
	
	public static File imageFile;
	public static File publicKeyFile;
	
	@Override
	public void start(Stage stage) 
	{	
		labelMessage = new Label();
		textFieldMessage = new TextField();
		buttonChooseImage = new Button();
		buttonChooseKey = new Button();
		buttonEmbedMessage = new Button();
		fileChooserImage = new MyFileChooser(stage, "choose an image", MyFileChooser.SRC_IMG);
		fileChooserKey = new MyFileChooser(stage, "Upload public key of destination", MyFileChooser.SRC_PUB_KEY);
		
		labelMessage.setText("Message : ");
		buttonChooseImage.setText("Choose image");
		buttonChooseImage.setOnAction(fileChooserImage);
		buttonChooseKey.setText("upload public key of destination");
		buttonChooseKey.setOnAction(fileChooserKey);
		buttonEmbedMessage.setText("Embed Message");
		buttonEmbedMessage.setOnAction( new MessageEmbedder(fileChooserImage, textFieldMessage) );
		
		GridPane pane = new GridPane();
		pane.add(buttonChooseKey, 0, 0);
		pane.add(labelMessage, 0, 1);
		pane.add(textFieldMessage, 1, 1);
		pane.add(buttonChooseImage, 0, 2);
		pane.add(buttonEmbedMessage, 0, 3);
		pane.setAlignment(Pos.CENTER);
		pane.setPadding( new Insets(30, 30, 30, 30) );
		GridPane.setMargin(buttonChooseImage, new Insets(30, 0, 0, 0));
		GridPane.setMargin(buttonChooseKey, new Insets(30, 0, 30, 0));
		GridPane.setMargin(buttonEmbedMessage, new Insets(30, 0, 0, 0));
		
		Scene scene = new Scene(pane, 900, 400);
		stage.setScene(scene);
		stage.setTitle("Embed Message");
		stage.show();
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}



















