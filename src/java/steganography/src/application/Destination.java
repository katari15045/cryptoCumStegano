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

public class Destination extends Application 
{
	Label labelLocationStr;
	TextField textFieldLocationStr;
	Button buttonChooseImage;
	Button buttonExtractor;
	Label labelMessage;
	
	MyFileChooser imageChooser;
	MessageExtractor messageExtractor;
	
	public static File imageFile;
	public static File publicKeyFile;
	
	@Override
	public void start(Stage stage) throws Exception 
	{
		labelLocationStr = new Label();
		textFieldLocationStr = new TextField();
		buttonChooseImage = new Button();
		buttonExtractor = new Button();
		labelMessage = new Label();
		imageChooser = new MyFileChooser(stage, "choose an image", MyFileChooser.DST_IMG);
		messageExtractor = new MessageExtractor(textFieldLocationStr, imageChooser, labelMessage);
		
		labelLocationStr.setText("Location String : ");
		buttonChooseImage.setText("Choose image");
		buttonChooseImage.setOnAction(imageChooser);
		buttonExtractor.setText("Extract Message");
		buttonExtractor.setOnAction(messageExtractor);
		
		GridPane pane = new GridPane();
		pane.add(labelLocationStr, 0, 0);
		pane.add(textFieldLocationStr, 1, 0);
		pane.add(buttonChooseImage, 0, 2);
		pane.add(buttonExtractor, 0, 3);
		pane.add(labelMessage, 0, 4);
		pane.setAlignment(Pos.CENTER);
		pane.setPadding( new Insets(30, 30, 30, 30) );
		GridPane.setMargin(buttonChooseImage, new Insets(30, 0, 0, 0));
		GridPane.setMargin(buttonExtractor, new Insets(30, 0, 0, 0));
		GridPane.setMargin(labelMessage, new Insets(50, 0, 0, 0));
		
		Scene scene = new Scene(pane, 900, 400);
		stage.setScene(scene);
		stage.setTitle("Extract Message");
		stage.show();
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
	
}
