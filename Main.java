import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Main
{
	private static BufferedImage bufferedImage;

	public static void main(String[] args)
	{
		bufferedImage = getImage("./images/bear_grylls.jpg");
		printImage(bufferedImage);
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