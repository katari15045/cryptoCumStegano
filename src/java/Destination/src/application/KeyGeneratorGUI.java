package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class KeyGeneratorGUI 
{
	private Label labelHeading;
	private Label labelContent;
	private Button button;
	
	private GridPane gridPane;
	private Scene scene;
	
	public void start(Stage stage)
	{
		labelHeading = new Label();
		labelHeading.setText("Step 1");
		labelHeading.setFont( new Font(20.0) );
		
		labelContent = new Label();
		labelContent.setText("Generate Asymmetric keys");
		
		button = new Button();
		button.setText("Generate keys");
		button.setOnAction( new KeyGenerator(stage) );
		
		gridPane = new GridPane();
		gridPane.add(labelHeading, 0, 0);
		gridPane.add(labelContent, 0, 1);
		gridPane.add(button, 0, 2);
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setMargin(labelContent, new Insets(20, 0, 0, 0));
		GridPane.setMargin(button, new Insets(90, 0, 0, 0));
		
		scene = new Scene(gridPane, 900, 480);
		stage.setScene(scene);
		stage.setTitle("Step 1");
		stage.show();
	}
}
