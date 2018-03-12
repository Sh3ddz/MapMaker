package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class ImageLoader 
{
	//used for reading in files from the relative directory
	public static BufferedImage loadImage(String path)
	{
		try 
		{
			return ImageIO.read(ImageLoader.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	//used for reading in image files from anywhere on the computer
	public static BufferedImage loadImageAbsolutePath(String path)
	{
		try
		{
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}