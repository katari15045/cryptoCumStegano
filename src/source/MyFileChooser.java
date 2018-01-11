import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

class MyFileChooser implements EventHandler<ActionEvent>
{
	private Stage stage = null;
	private TextField textField = null;
	private FileChooser fileChooser = null;
	static File file = null;
	private String title = null;
	static boolean isImage = false;
	
	private SecureTunnelCreator secureTunnelCreator = null;

	public MyFileChooser(Stage stage, String title) 
	{
		this.stage = stage;
		this.title = title;
	}
	
	@Override
	public void handle(ActionEvent event)
	{	
		fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		fileChooser.setInitialDirectory( new File( System.getProperty("user.home") ) );
		MyFileChooser.file = fileChooser.showOpenDialog(stage);
		
		if( MyFileChooser.file == null )
		{
			PublicKeyCollectorGUI pubKeyCollector = new PublicKeyCollectorGUI();

			System.out.println("Unable to open the file!");
			pubKeyCollector.start(stage);
		}

		if( isImage )
		{
			isImage = false;
			return;
		}

		PublicKeyCollectorGUI.dstPubKey = MyRSA.getPublicKey( file.getAbsolutePath() );
		secureTunnelCreator = new SecureTunnelCreator();
		secureTunnelCreator.start(stage);
	}
}
