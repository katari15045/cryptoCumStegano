import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Main
{
	public static void main(String[] args)
	{
		File file = new File("/home/saketh/Pictures/hostel_request.png");
		BufferedImage bufferedImage = null;
		int rows, cols;

		try
		{
			bufferedImage = ImageIO.read(file);
		}

		catch(IOException e)
		{
			e.printStackTrace();
		}

		rows = bufferedImage.getHeight();
		cols = bufferedImage.getWidth();

		System.out.println(rows + " x " + cols);
	}
}