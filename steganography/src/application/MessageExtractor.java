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
			currentAscii = getDecimalFromBinary(currentBinary);
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
			currentColorBinary = getBinaryFromDecimal(currentColorDecimal);
			currentBitChar = currentColorBinary.charAt(currentBitIndex);
			extractedMessage.append(currentBitChar);

			currentBitLocationIndex = currentBitLocationIndex + 1;
		}

		return extractedMessage.toString();

	}
	
	private int getDecimalFromBinary(String binary)
	{
		int currentIndex, currentBit;
		int exponent = 0, value;
		int decimal = 0;

		currentIndex = binary.length()-1;

		while( currentIndex >= 0 )
		{
			value = (int)Math.pow(2, exponent);
			currentBit = Integer.valueOf( String.valueOf( binary.charAt(currentIndex) ) );
			decimal = decimal + (value*currentBit);

			exponent = exponent + 1;
			currentIndex = currentIndex - 1;
		}

		return decimal;
	}

	private String getBinaryFromDecimal(int decimal)
	{
		// decimal must be in [0, 255]

		if( decimal == 0 )
		{
			return "00000000";
		}

		if( decimal == 1 )
		{
			return "00000001";
		}

		char[] binary = {'0', '0', '0', '0', '0', '0', '0', '0'};
		int exponent, value;

		do
		{
			exponent = getNearestPowerOfTwo(decimal);
			binary[7-exponent] = '1';
			value = (int)Math.pow(2, exponent);
			decimal = decimal - value;
		}
		while( decimal > 0 );

		return new String(binary);
	}
	
	private int getNearestPowerOfTwo(int num)
	{
		// num should be atleast 1

		int value = -2;
		int exponent = 0;

		do
		{
			exponent = exponent + 1;
			value = (int)Math.pow(2, exponent);
		}
		while(value <= num);

		return exponent - 1;
	}
}
