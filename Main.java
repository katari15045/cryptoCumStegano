import java.lang.StringBuilder;
import java.lang.Math;
import java.util.Random;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Main
{
	public static void main(String[] args)
	{
		BufferedImage bufferedImage;
		String originalMessage, binaryMessage;
		String locationString;
		int rows, cols;

		originalMessage = "sak";
		binaryMessage = getBinaryFromChars(originalMessage);
		System.out.println("Original Message : " + originalMessage);
		System.out.println( "Binary Message : " + binaryMessage );

		bufferedImage = getImage("./images/bear_grylls.jpg");
		rows = bufferedImage.getHeight();
		cols = bufferedImage.getWidth();
		System.out.println("Image -> (" + rows + ", " + cols + ")");

		locationString = embedMessageInAnImage(binaryMessage, bufferedImage);
		System.out.println("Location String -> " + locationString);

		String extractedMessage;

		extractedMessage = extractMessage(locationString, bufferedImage);
		System.out.println("Extracted Message -> " + extractedMessage);
	}

	private static String extractMessage(String locationString, BufferedImage image)
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

	private static String embedMessageInAnImage(String message, BufferedImage image)
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

	private static int[] embedBitInAnImage(char inpBit, BufferedImage image)
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
		binaryColor = getBinaryFromDecimal(color);
		randomBit = binaryColor.charAt( randomPosition[3] );

		if( inpBit != randomBit )
		{
			return null;
		}

		return randomPosition;
	}

	private static String getBinaryFromChars(String inp)
	{
		StringBuilder stringBuilder;
		int currentIndex = 0;
		int currentAscii;
		String currentBinary;

		stringBuilder = new StringBuilder();

		while( currentIndex < inp.length() )
		{
			currentAscii = (int) inp.charAt(currentIndex);
			currentBinary = getBinaryFromDecimal(currentAscii);
			stringBuilder.append(currentBinary);

			currentIndex = currentIndex + 1;
		}

		return stringBuilder.toString();
	}

	private static int getDecimalFromBinary(String binary)
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

	private static String getBinaryFromDecimal(int decimal)
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

	private static int getNearestPowerOfTwo(int num)
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

	private static int[] getRandomPosition(int rows, int cols)
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

	private static BufferedImage getImage(String filePath)
	{
		File file = new File(filePath);
		BufferedImage bufferedImage = null;

		try
		{
			bufferedImage = ImageIO.read(file);
		}

		catch(IOException e)
		{
			e.printStackTrace();
		}

		return bufferedImage;
	}
}