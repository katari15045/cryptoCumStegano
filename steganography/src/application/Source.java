package application;
	
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
	Label label;
	TextField textField;
	Button buttonChooseImage;
	Button buttonEmbedMessage;
	
	ImageChooser imageChooser;
	MessageEmbedder messageEmbedder;
	
	@Override
	public void start(Stage stage) 
	{	
		label = new Label();
		textField = new TextField();
		buttonChooseImage = new Button();
		buttonEmbedMessage = new Button();
		imageChooser = new ImageChooser(stage);
		
		label.setText("Message : ");
		buttonChooseImage.setText("Choose image");
		buttonChooseImage.setOnAction(imageChooser);
		buttonEmbedMessage.setText("Embed Message");
		buttonEmbedMessage.setOnAction( new MessageEmbedder(imageChooser, textField) );
		
		GridPane pane = new GridPane();
		pane.add(label, 0, 0);
		pane.add(textField, 1, 0);
		pane.add(buttonChooseImage, 0, 1);
		pane.add(buttonEmbedMessage, 0, 2);
		pane.setAlignment(Pos.CENTER);
		pane.setPadding( new Insets(30, 30, 30, 30) );
		GridPane.setMargin(buttonChooseImage, new Insets(30, 0, 0, 0));
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



















