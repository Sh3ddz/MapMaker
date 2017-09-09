package tiles;

import gfx.Assets;

public class AirTile extends Tile
{
	public AirTile(int id)
	{
		super(Assets.air, id);
	}
	
	@Override
	public boolean isAir()
	{
		return true;
	}
}