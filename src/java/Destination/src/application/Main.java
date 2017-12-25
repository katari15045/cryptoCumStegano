package application;
	
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application 
{	
	private KeyGeneratorGUI keyGeneratorGUI = null;
	
	@Override
	public void start(Stage stage) 
	{
		keyGeneratorGUI = new KeyGeneratorGUI();
		keyGeneratorGUI.start(stage);
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
