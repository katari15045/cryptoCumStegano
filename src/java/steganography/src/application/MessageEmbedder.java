package application;

import java.awt.image.BufferedImage;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

public class MessageEmbedder implements EventHandler<ActionEvent>
{
	TextField textField;
	ImageChooser imageChooser;
	BufferedImage image;
	
	String binaryMessage, originalMessage, locationString;
	int rows, cols;
	
	public MessageEmbedder(ImageChooser imageChooser, TextField textField) 
	{
		this.imageChooser = imageChooser;
		this.textField = textField;
	}
	
	@Override
	public void handle(ActionEvent event)
	{
		this.image = imageChooser.getImage();
		originalMessage = textField.getText();
		
		binaryMessage = Binary.getBinaryFromChars(originalMessage);
		System.out.println("Original Message : " + originalMessage);
		System.out.println( "Binary Message : " + binaryMessage );
		
		rows = image.getHeight();
		cols = image.getWidth();
		System.out.println("Image -> (" + rows + ", " + cols + ")");
		
		locationString = embedMessageInAnImage(binaryMessage, image);
		System.out.println("Location String -> " + locationString);
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
/*
	private static void printImage(BufferedImage image)
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
*/
}