package tiles;

import java.awt.image.BufferedImage;

public class WaterTile extends Tile
{

	public WaterTile(BufferedImage texture, int id) 
	{
		super(texture, id);
	}
	
	public boolean isWater()
	{
		return true;
	}
}
