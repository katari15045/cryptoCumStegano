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
	
	public static final int SRC_IMG = 1;
	public static final int DST_IMG = 2;
	public static final int SRC_PUB_KEY = 3;
	public static final int DST_PUB_KEY = 4;
	
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
		
		if( fileType == SRC_IMG )
		{
			Source.imageFile = file;
		}
		
		else if( fileType == DST_IMG )
		{
			Destination.imageFile = file;
		}
		
		else if( fileType == SRC_PUB_KEY )
		{
			Source.publicKeyFile = file;
		}
		
		else if( fileType == DST_PUB_KEY )
		{
			Destination.publicKeyFile = file;
		}
		
		
	}
}
