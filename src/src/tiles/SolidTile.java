package tiles;

import java.awt.image.BufferedImage;

public class SolidTile extends Tile
{
	public SolidTile(BufferedImage texture, int id)
	{
		super(texture, id);
	}
	
	@Override
	public boolean isSolid()
	{
		return true;
	}

}
