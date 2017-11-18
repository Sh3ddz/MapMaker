package gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet
{
	private BufferedImage sheet;	
	
	public SpriteSheet(BufferedImage sheet)
	{
		this.sheet = sheet;
	}
	
	public BufferedImage crop(int x, int y, int width, int height)
	{
		return sheet.getSubimage(x, y, width, height);
	}
	
	public int getWidth()//returns width of the sprite sheet
	{
		return sheet.getWidth();
	}
	
	public int getHeight()//returns height of the sprite sheet
	{
		return sheet.getHeight();
	}
}