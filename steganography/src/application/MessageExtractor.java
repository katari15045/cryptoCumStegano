package application;

import java.awt.image.BufferedImage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MessageExtractor implements EventHandler<ActionEvent>
{
	String locationString;
	BufferedImage bufferedImage;
	
	public MessageExtractor(String locationString, BufferedImage bufferedImage) 
	{
			this.locationString = locationString;
			this.bufferedImage = bufferedImage;
	}
	
	@Override
	public void handle(ActionEvent event)
	{
		String extractedBinaryMessage, extratedMessage;

		extractedBinaryMessage = extractBinaryMessage(locationString, bufferedImage);
		System.out.println("Extracted Binary Message -> " + extractedBinaryMessage);

		extratedMessage = extractMessage(extractedBinaryMessage);
		System.out.println("Extracted Message -> " + extratedMessage);
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
