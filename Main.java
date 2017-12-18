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
		int rows, cols;
		int[] randomPosition;

		bufferedImage = getImage("./images/bear_grylls.jpg");
		//printImage(bufferedImage);

		rows = bufferedImage.getHeight();
		cols = bufferedImage.getWidth();
		System.out.println("Image -> (" + rows + ", " + cols + ")");

		randomPosition = getRandomPosition(rows, cols);
		System.out.println("(" + randomPosition[0] + ", " + randomPosition[1] + ", " + randomPosition[2] + ", " + randomPosition[3] + ")");

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