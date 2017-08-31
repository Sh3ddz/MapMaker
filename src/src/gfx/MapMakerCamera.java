package gfx;

import MapMaker.MapMaker;
import input.Selector;
import main.Handler;
import tiles.Tile;
import worlds.World;

public class MapMakerCamera 
{
	private float xOffset, yOffset;
	private int zoomLevel;
	
	public MapMakerCamera(MapMaker mapMaker, World world, Handler handler)
	{
		this.xOffset = 0;
		this.yOffset = 0;
		this.zoomLevel = 3;
	}
	
	public void moveByTile(float xAmt, float yAmt)
	{
		move(xAmt*16, yAmt*16);
	}
	
	public void move(float xAmt, float yAmt)
	{
		xOffset += xAmt;
		yOffset += yAmt;
	}

	public float getxOffset() 
	{
		return xOffset;
	}

	public void setxOffset(float xOffset) 
	{
		this.xOffset = xOffset;
	}

	public float getyOffset()
	{
		return yOffset;
	}

	public void setyOffset(float yOffset) 
	{
		this.yOffset = yOffset;
	}
	
	//zooms towards the center of the screen
	public void zoomIn()
	{
		//640, 360
		if(zoomLevel < 5)
		{
			this.xOffset = (float) (xOffset *2) + (640/2);
			this.yOffset = (float) (yOffset *2) + (360/2);
			Tile.TILEWIDTH *= 2;
			Tile.TILEHEIGHT *= 2;
			Selector.width *= 2;
			Selector.height *= 2;
			this.zoomLevel++;
		}
	}
	
	//zooms away from the center of the screen
	public void zoomOut()
	{
		if(zoomLevel > 1)
		{
			this.xOffset = (float) (xOffset /2) - (640/4);
			this.yOffset = (float) (yOffset /2) - (360/4);
			Tile.TILEWIDTH /= 2;
			Tile.TILEHEIGHT /= 2;
			Selector.width /= 2;
			Selector.height /= 2;
			this.zoomLevel--;
		}
	}
	
	public void zoomReset()
	{
		this.xOffset = 0;
		this.yOffset = 0;
		Tile.TILEWIDTH =  16;
		Tile.TILEHEIGHT =  16;
		Selector.width =  16;
		Selector.height =  16;
		this.zoomLevel = 3;
	}
}
