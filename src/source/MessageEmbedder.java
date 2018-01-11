import java.util.Base64;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.io.File;
import java.util.Random;
import java.lang.Thread;
import javafx.concurrent.Task;
import java.lang.Exception;
import javax.imageio.ImageIO;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class MessageEmbedder implements EventHandler<ActionEvent>
{
	private Stage stage = null;
	private TextField textField = null;

	private ProgressIndicator progressIndicator = null;
	private Label labelProgress = null;
	private Button buttonFinish = null;
	private GridPane gridPane = null;
	private Scene scene = null;

	private static String locationString = null;
	private BackGroundEmbedder backGroundEmbedder = null;
	private Thread backGroundThread = null;

	public MessageEmbedder(Stage stage, TextField textField) 
	{
		this.stage = stage;
		this.textField = textField;
	}
	
	@Override
	public void handle(ActionEvent event)
	{
		progressIndicator = new ProgressIndicator();
		progressIndicator.setProgress(-1.0);
		labelProgress = new Label();
		buttonFinish = new Button();
		buttonFinish.setText("Finish");
		buttonFinish.setOnAction( new Finisher() );
		buttonFinish.setDisable(true);	

		gridPane = new GridPane();	
		gridPane.add(progressIndicator, 0, 0);
		gridPane.add(labelProgress, 0, 1);
		gridPane.add(buttonFinish, 0, 2);
		gridPane.setAlignment(Pos.CENTER);
                GridPane.setMargin(labelProgress, new Insets(20, 0, 0, 0));
                GridPane.setMargin(buttonFinish, new Insets(60, 0, 0, 0));

		scene = new Scene(gridPane, Constants.WIND_COLS, Constants.WIND_ROWS);
                stage.setScene(scene);
                stage.show();

		backGroundEmbedder = new BackGroundEmbedder( textField.getText(), buttonFinish );
		progressIndicator.progressProperty().bind( backGroundEmbedder.progressProperty() );
                labelProgress.textProperty().bind( backGroundEmbedder.messageProperty() );
		backGroundThread = new Thread(backGroundEmbedder);
		backGroundThread.start();
	}

	static String getLocationString()
	{
		return MessageEmbedder.locationString;
	}

	static void setLocationString(String locationString)
	{
		MessageEmbedder.locationString = locationString;
	}
}

class BackGroundEmbedder extends Task<Void>
{
	private File file = null;
	private BufferedImage image = null;	
	private String binaryMessage, originalMessage;
	private int rows, cols;
	private Button buttonFinish = null;

	public BackGroundEmbedder(String originalMessage, Button buttonFinish)
	{
		this.originalMessage = originalMessage;
		this.buttonFinish = buttonFinish;
	}

	@Override 
	public Void call()
	{
		updateMessage("Embedding...");
		System.out.println("\nEmbedding...");
		file = MyFileChooser.file;
		
		try
		{
			image = ImageIO.read(file);
		}
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		binaryMessage = Binary.getBinaryFromChars(originalMessage);
		System.out.println("Message to embed : " + originalMessage);
		
		rows = image.getHeight();
		cols = image.getWidth();
		System.out.println("Image -> (" + rows + ", " + cols + ")");
		
		MessageEmbedder.setLocationString( embedMessageInAnImage(binaryMessage, image) );
		sendData();
	
		return null;
	}

	private void sendData()
	{
		byte[] fileBytes = null;		

		MySocket socket = null;
		Thread socketThread = null;

		updateMessage("Sending data...");
		System.out.println("Sending data...");

		try
		{
	                socket = new MySocket( DiffieHellman.getActiveSocket() );
        	        socket.setData( MessageEmbedder.getLocationString() );
			fileBytes = Files.readAllBytes( file.toPath() );
			socket.setImageString( Base64.getEncoder().encodeToString(fileBytes) );
        	        socket.setMode( MySocket.POST_SYM_KEY );
			socketThread = new Thread(socket);
			socketThread.start();
			socketThread.join();

			updateMessage("Data sent successfully!");
			System.out.println("Data sent successfully!");
			updateProgress(1.0, 1.0);
			buttonFinish.setDisable(false);
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private String embedMessageInAnImage(String message, BufferedImage image)
	{
		int currentIndex = 0;
		char currentBit;
		int[] randomPosition;
		StringBuilder locationString;

		locationString = new StringBuilder();

		while( currentIndex < message.length() )
		{
			currentBit = message.charAt(currentIndex);
			randomPosition = embedBitInAnImage(currentBit, image);

			if( randomPosition == null )
			{
				continue;
			}

			locationString.append( randomPosition[0] ).append("-").append( randomPosition[1] ).append("-").append( randomPosition[2] );
			locationString.append("-").append( randomPosition[3] ).append("|");

			currentIndex = currentIndex + 1;
		}

		return locationString.toString();
	}

	private int[] embedBitInAnImage(char inpBit, BufferedImage image)
	{
		int[] randomPosition;
		int[] pixel;
		int rows, cols;
		int color;
		String binaryColor;
		char randomBit;

		rows = image.getHeight();
		cols = image.getWidth();

		randomPosition = getRandomPosition(rows, cols);
		pixel = image.getRaster().getPixel(randomPosition[1], randomPosition[0], new int[3]);
		color = pixel[ randomPosition[2] ];
		binaryColor = Binary.getBinaryFromDecimal(color);
		randomBit = binaryColor.charAt( randomPosition[3] );

		if( inpBit != randomBit )
		{
			return null;
		}

		return randomPosition;
	}

	

	private int[] getRandomPosition(int rows, int cols)
	{
		Random random;
		int[] positionArray;

		random = new Random();
		positionArray = new int[4];

		positionArray[0] = random.nextInt(rows);	// Row Index
		positionArray[1] = random.nextInt(cols);	// Col index
		positionArray[2] = random.nextInt(3);		// Component index - [0, 2]
		positionArray[3] = random.nextInt(8);		// Bit Index - [0, 7]

		return positionArray;
	}	

	private void printImage(BufferedImage image)
	{
		int rows, cols;
		int currentRow = 0, currentCol;
		int[] currentPixel;

		rows = image.getHeight();
		cols = image.getWidth();

		while( currentRow < rows )
		{
			currentCol = 0;

			while( currentCol < cols )
			{
				currentPixel = image.getRaster().getPixel(currentCol, currentRow, new int[3]);
				System.out.print("(" + currentPixel[0] + ", " + currentPixel[1] + ", " + currentPixel[2] + ")	");

				currentCol = currentCol + 1;
			}

			System.out.println();
			currentRow = currentRow + 1;
		}
	}

	@Override
        protected void updateProgress(double workDone, double max)
        {
                super.updateProgress(workDone, max);
        }

        @Override
        protected void updateMessage(String message)
        {
                super.updateMessage(message);
        }
}

class Finisher implements EventHandler<ActionEvent>
{
	@Override
	public void handle(ActionEvent event)	
	{
		System.out.println("\nSee you!\n");
		Platform.exit();
		System.exit(0);
	}
}






