package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GetDataGUI
{	
	private Label label = null;
	private GridPane gridPane;
	private Scene scene;
	
	public void start(Stage stage)
	{
		label = new Label();
		label.setText("Press \"Get data\" button just before pressing \"Send data\" at source");
		
		gridPane = new GridPane();
		gridPane.add(label, 0, 0);
		gridPane.setAlignment(Pos.CENTER);
		
		scene = new Scene(gridPane, 900, 500);
		stage.setScene(scene);
		stage.show();
	}
}
