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
		String original_message, binary_message;
		int rows, cols;
		int[] randomPosition;

		original_message = "sak";
		binary_message = getBinaryFromChars(original_message);

		System.out.println("Original Message : " + original_message);
		System.out.println( "Binary Message : " + binary_message );

		bufferedImage = getImage("./images/bear_grylls.jpg");
		rows = bufferedImage.getHeight();
		cols = bufferedImage.getWidth();
		System.out.println("Image -> (" + rows + ", " + cols + ")");

		//randomPosition = getRandomPosition(rows, cols);
		//System.out.println("(" + randomPosition[0] + ", " + randomPosition[1] + ", " + randomPosition[2] + ", " + randomPosition[3] + ")");

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