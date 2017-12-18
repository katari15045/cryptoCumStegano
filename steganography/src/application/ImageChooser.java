package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

class ImageChooser implements EventHandler<ActionEvent>
{
	Stage stage;
	TextField textField;
	FileChooser fileChooser;
	File file;
	
	BufferedImage bufferedImage;
	
	public ImageChooser(Stage stage, TextField textField) 
	{
		this.stage = stage;
		this.textField = textField;
	}
	
	@Override
	public void handle(ActionEvent event)
	{	
		fileChooser = new FileChooser();
		fileChooser.setTitle("Choose an image");
		fileChooser.setInitialDirectory( new File( System.getProperty("user.home") ) );
		file = fileChooser.showOpenDialog(stage);
		
		if( file == null )
		{
			System.out.println("Unable to open the image!");
			return;
		}
		
		try
		{
			bufferedImage = ImageIO.read(file);
		}

		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage()
	{
		return bufferedImage;
	}
}
