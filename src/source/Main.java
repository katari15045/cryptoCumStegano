/*
 * Compile with javac -cp .:<path to all external jars> Main.java
 * Run with java -cp .:<path to all external jars> Main
*/


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.crypto.KeyGenerator;

public class Main extends Application 
{	
	private Label labelContent = null;
	private ProgressIndicator progressIndicator = null;
	private Label labelProgress = null;
	private Button button = null;
	private GridPane gridPane = null;
	private Scene scene = null;
	
	private Thread thread = null;
	private MyKeyGenerator keyGenerator = null;
	
	@Override
	public void start(Stage stage) 
	{
		labelContent = new Label();
		labelContent.setText(Constants.definition);
		labelContent.setTextFill( Color.web(Constants.HEAD_COLOR) );
		progressIndicator = new ProgressIndicator();
		progressIndicator.setProgress(-1.0);
		labelProgress = new Label();
		button = new Button();
		button.setText("Start!");
		button.setOnAction( new Starter(stage) );
		button.setDisable(true);
				
		gridPane = new GridPane();
		gridPane.add(progressIndicator, 0, 0);
		gridPane.add(labelProgress, 0, 1);
		gridPane.add(labelContent, 0, 2);
		gridPane.add(button, 0, 3);
		gridPane.setAlignment(Pos.CENTER);
		GridPane.setMargin(progressIndicator, new Insets(30, 0, 0, 0));
		GridPane.setMargin(labelProgress, new Insets(20, 0, 0, 0));
		GridPane.setMargin(labelContent, new Insets(30, 0, 0, 0));
		GridPane.setMargin(button, new Insets(20, 0, 60, 0));
		scene = new Scene(gridPane, Constants.WIND_COLS, Constants.WIND_ROWS);
		stage.setScene(scene);
		stage.setTitle(Constants.APP_NAME + " - Source - Main");
		stage.show();
		
		keyGenerator = new MyKeyGenerator(button);
		progressIndicator.progressProperty().bind( keyGenerator.progressProperty() );
		labelProgress.textProperty().bind( keyGenerator.messageProperty() );
		thread = new Thread(keyGenerator);
		thread.start();
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}


class Starter implements EventHandler<ActionEvent>
{
	private Stage stage = null;
	private EmailCumIPCollectorGUI emailCumIPCollectorGUI = null;
	
	Starter(Stage stage)
	{
		this.stage = stage;
	}
	
	public void handle(ActionEvent event)
	{
		emailCumIPCollectorGUI = new EmailCumIPCollectorGUI();
		emailCumIPCollectorGUI.start(stage);
	}
}









