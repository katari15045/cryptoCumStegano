package application;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

class MyFileChooser implements EventHandler<ActionEvent>
{
	Stage stage;
	TextField textField;
	FileChooser fileChooser;
	File file;
	String title;
	int fileType;
	
	public static final int IMG = 1;
	public static final int PUB_KEY = 2;
	
	public MyFileChooser(Stage stage, String title, int fileType) 
	{
		this.stage = stage;
		this.title = title;
		this.fileType = fileType;
	}
	
	@Override
	public void handle(ActionEvent event)
	{	
		fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		fileChooser.setInitialDirectory( new File( System.getProperty("user.home") ) );
		file = fileChooser.showOpenDialog(stage);
		
		if( file == null )
		{
			System.out.println("Unable to open the file!");
			return;
		}
		
		if( fileType == PUB_KEY )
		{
			KeyTransferOverEmailGUI.publicKeyFile = file;
			System.out.println("File chosen -> " + file.getAbsolutePath());
		}
		
		/*
		 * else if( fileType == IMG )
		{
			Destination.imageFile = file;
		}
		 */
	}
}
