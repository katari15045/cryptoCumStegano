import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.Exception;
import java.io.FileOutputStream;
import javafx.concurrent.Task;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MessageExtractor extends Task<Void>
{
	
	private String locationString = null, imgStr = null;
	private BufferedImage bufferedImage = null;
	
	private String extractedBinaryMessage = null;
	static String extractedMessage = null;
	
	public MessageExtractor(String locationString, String imgStr)
	{
		this.locationString = locationString;
		this.imgStr = imgStr;
	}
	
	@Override
	public Void call()
	{
		InputStream inputStream = null;
		
		try 
		{
			System.out.println("image str length : " + imgStr.getBytes().length + " bytes");
			inputStream = new ByteArrayInputStream(imgStr.getBytes());
			bufferedImage = ImageIO.read(inputStream);
			System.out.println("\nBufferedImage : " + bufferedImage + "\n");
		}
		
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		System.out.println("Extracting binary message...");
		extractedBinaryMessage = extractBinaryMessage(locationString, bufferedImage);

		System.out.println("Extracting actual message...");
		extractedMessage = extractMessage(extractedBinaryMessage);
		System.out.println("Extracted Message : " + extractedMessage);

		return null;
	}
	
	private String extractMessage(String binaryMessage)
	{
		int currentStartIndex = 0, currentEndIndex = 7, currentAscii;
		String currentBinary;
		char currentChar;
		StringBuilder message;

		message = new StringBuilder();

		while( currentEndIndex < binaryMessage.length() )
		{
			currentBinary = binaryMessage.substring(currentStartIndex, currentEndIndex+1);
			currentAscii = Binary.getDecimalFromBinary(currentBinary);
			currentChar = (char)currentAscii;
			message.append(currentChar);

			currentStartIndex = currentStartIndex + 8;
			currentEndIndex = currentEndIndex + 8;
		}

		return message.toString();
	}

	private String extractBinaryMessage(String locationString, BufferedImage image)
	{
		String[] bitLocations;
		int currentBitLocationIndex = 0, currentColIndex, currentRowIndex, currentColorIndex, currentBitIndex;
		int currentColorDecimal;
		String currentColorBinary;
		int[] currentPixel;
		char currentBitChar;
		StringBuilder extractedMessage;

		extractedMessage = new StringBuilder();
		bitLocations = locationString.split("\\|");	// https://stackoverflow.com/questions/26192481/java-string-splits-by-every-character

		while( currentBitLocationIndex < bitLocations.length )
		{
			String[] sublocations = bitLocations[currentBitLocationIndex].split("-");
			currentRowIndex = Integer.valueOf( sublocations[0] );
			currentColIndex = Integer.valueOf( sublocations[1] );
			currentColorIndex = Integer.valueOf( sublocations[2] );
			currentBitIndex = Integer.valueOf( sublocations[3] );
			currentPixel = image.getRaster().getPixel( currentColIndex, currentRowIndex, new int[3]);
			currentColorDecimal = currentPixel[currentColorIndex];
			currentColorBinary = Binary.getBinaryFromDecimal(currentColorDecimal);
			currentBitChar = currentColorBinary.charAt(currentBitIndex);
			extractedMessage.append(currentBitChar);

			currentBitLocationIndex = currentBitLocationIndex + 1;
		}

		return extractedMessage.toString();

	}
}
