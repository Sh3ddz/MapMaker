package input;

import worlds.World;

public class Change//what change was made to the world (used for undo-ing)
{
	private World world; //the world the change took place on
	private int xTilePos; //x Tile position of the change
	private int yTilePos; //y Tile position of the change
	private int layer; //the layer the change took place on
	private int oldTileId; //The tile that it was before the change
	private int newTileId; //The tile the old tile was changed to
	
	public Change(World world, int x, int y, int layer, int oldTileId, int newTileId)
	{
		this.world = world;
		this.xTilePos = x;
		this.yTilePos = y;
		this.layer = layer;
		this.oldTileId = oldTileId;
		this.newTileId = newTileId;
	}
	
	public void undo()
	{
		world.setTileNoChange(this.xTilePos, this.yTilePos, this.layer, this.oldTileId);
	}
	
	public void redo()
	{
		world.setTileNoChange(this.xTilePos, this.yTilePos, this.layer, this.newTileId);
	}
	
	public String toString()
	{
		return oldTileId+" changed to "+newTileId+" on "+xTilePos+","+yTilePos;
	}
}
