package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class KeyTransferChannelSelectorGUI 
{
	private Label labelHeading;
	private Label labelContent;
	private CheckBox checkboxOne;
	private CheckBox checkboxTwo;
	private Button button;
	private GridPane gridPane;
	private Scene scene;
	
	public void start(Stage stage)
	{
		labelHeading = new Label();
		labelHeading.setText("Step 2");
		labelHeading.setFont( new Font(20.0) );
		labelContent = new Label();
		labelContent.setText("Select a channel to transfer public key to the source");
		checkboxOne = new CheckBox("Transfer keys using this application");
		checkboxTwo = new CheckBox("Transfer keys on your own from your email");
		button = new Button();
		button.setText("Submit");
		button.setOnAction( new MyHandler(stage, checkboxOne, checkboxTwo) );
		
		gridPane = new GridPane();
		gridPane.add(labelHeading, 0, 0);
		gridPane.add(labelContent, 0, 1);
		gridPane.add(checkboxOne, 0, 2);
		gridPane.add(checkboxTwo, 0, 3);
		gridPane.add(button, 0, 4);
		GridPane.setMargin(labelContent, new Insets(30, 0, 0, 0));
		GridPane.setMargin(checkboxOne, new Insets(90, 0, 0, 0));
		GridPane.setMargin(checkboxTwo, new Insets(10, 0, 0, 0));
		GridPane.setMargin(button, new Insets(30, 0, 0, 0));
		gridPane.setAlignment(Pos.CENTER);
		
		scene = new Scene(gridPane, 900, 500);
		stage.setScene(scene);
		stage.show();
	}
}

class MyHandler implements EventHandler<ActionEvent>
{
	private Stage stage;
	private CheckBox checkboxOne;
	private CheckBox checkboxTwo;
	
	private KeyTransferOverEmailGUI keyTransferOverEmailGUI = null;
	private GetDataGUI getDataGUI;
	
	public MyHandler(Stage stage, CheckBox checkboxOne, CheckBox checkboxTwo) 
	{
		this.stage = stage;
		this.checkboxOne = checkboxOne;
		this.checkboxTwo = checkboxTwo;
	}
	
	@Override
	public void handle(ActionEvent event)
	{	
		if( checkboxOne.isSelected() )
		{
			// Transfer keys using this application
			keyTransferOverEmailGUI = new KeyTransferOverEmailGUI();
			keyTransferOverEmailGUI.start(stage);
		}
		
		else if( checkboxTwo.isSelected() )
		{
			// Wait for Data from Source
			getDataGUI = new GetDataGUI();
			getDataGUI.start(stage);
		}
	}
}
