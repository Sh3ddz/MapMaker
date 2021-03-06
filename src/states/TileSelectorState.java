package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import gfx.Assets;
import input.Selector;
import main.Handler;
import tiles.Tile;

public class TileSelectorState extends State
{
	private Selector selector;
	private static int xOffset;
	private static int yOffset;
	
	public TileSelectorState(Handler handler)
	{
		super(handler);
		handler.getWorld();
		this.selector = handler.getSelector();
		xOffset = 0;
		yOffset = 0;
	}
	public static void setXOffset(int x)
	{
		xOffset = x;
	}
	public static int getXOffset()
	{
		return xOffset;
	}
	
	public static void setYOffset(int y)
	{
		yOffset = y;
	}
	public static int getYOffset()
	{
		return yOffset;
	}
	
	@Override
	public void tick()
	{
		selector.tick();
		selectTile();
	}

	@Override
	public void render(Graphics g)
	{
		int xRender, yRender;		
		xRender = 0;
		yRender = 32;
		
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, 1920, 1080);

		for(int i = 0; i < Tile.tiles.length; i++)
		{
			if((Tile.tiles[i] != null) && (i < 15 || i >  122) && (i < 1000))
			{				
				//resetting each row so it doesnt go off screen
				if(xRender > 600)
				{
					xRender = 0;
					yRender += 32;
				}
				if(i == 123)
				{
					xRender = 0;
					yRender +=32;
				}
				xRender += 32;
	
				if(!(yRender+yOffset < 0) && !(yRender+yOffset>360)) //only rendering tiles in view.
				{
					Tile.tiles[i].render(g, xRender+xOffset, yRender+yOffset);
				}
			}
			if(!(yRender < 0) && !(yRender>260)) //only rendering tiles in view.
			{
				renderLargerTiles(g, xRender, yRender, i);
			}
		}
		
		g.setColor(Color.GREEN);
		Font f = new Font(Font.SERIF,Font.PLAIN, 20);
		g.setFont(f);
		g.drawString("Imported Tiles", 270, 600+yOffset);
		g.drawLine(handler.getMapMaker().getWidth()/2-64, 600+yOffset, handler.getMapMaker().getWidth()/2+64, 600+yOffset);
		xRender = 0;
		yRender = 640;
		for(int i = 0; i < Tile.importedTiles.size(); i++)
		{
			if(Tile.importedTiles.get(i) != null)
			{				
				//resetting each row so it doesnt go off screen
				if(xRender > 600)
				{
					xRender = 0;
					yRender += 32;
				}
				xRender += 32;
	
				if(!(yRender+yOffset < 0) && !(yRender+yOffset>360)) //only rendering tiles in view.
				{
					Tile.importedTiles.get(i).render(g, xRender+xOffset, yRender+yOffset);
				}
			}
		}
		selector.render(g);
	}
	
	public void renderLargerTiles(Graphics g, int xRender, int yRender, int i)
	{
		//15,19,23,27,31,35
		if(i == 15)
		{
			xRender = 160;
			int tempYRender = yRender;
			yRender = 416;
			g.drawImage(Assets.bushFull, xRender+xOffset, yRender+yOffset, Tile.TILEWIDTH*2, Tile.TILEHEIGHT*2, null);
			yRender = tempYRender;
		}
		if(i == 19)
		{
			xRender = 208;
			int tempYRender = yRender;
			yRender = 416;
			g.drawImage(Assets.rockFull, xRender+xOffset, yRender+yOffset, Tile.TILEWIDTH*2, Tile.TILEHEIGHT*2, null);
			yRender = tempYRender;
		}
		if(i == 23)
		{
			xRender = 256;
			int tempYRender = yRender;
			yRender = 416;
			g.drawImage(Assets.strucFull, xRender+xOffset, yRender+yOffset, Tile.TILEWIDTH*2, Tile.TILEHEIGHT*2, null);
			yRender = tempYRender;
		}
		if(i == 27)
		{
			xRender = 304;
			int tempYRender = yRender;
			yRender = 416;
			g.drawImage(Assets.tallgrassFull, xRender+xOffset, yRender+yOffset, Tile.TILEWIDTH*2, Tile.TILEHEIGHT*2, null);
			yRender = tempYRender;
		}
		if(i == 31)
		{
			xRender = 352;
			int tempYRender = yRender;
			yRender = 416;
			g.drawImage(Assets.signFull, xRender+xOffset, yRender+yOffset, Tile.TILEWIDTH*2, Tile.TILEHEIGHT*2, null);
			yRender = tempYRender;
		}
		if(i == 35)
		{
			xRender = 400;
			int tempYRender = yRender;
			yRender = 416;
			g.drawImage(Assets.stumpFull, xRender+xOffset, yRender+yOffset, Tile.TILEWIDTH*4, Tile.TILEHEIGHT*4, null);
			yRender = tempYRender;
		}
		if(i == 51)
		{
			xRender = 32;
			int tempYRender = yRender;
			yRender = 416;
			g.drawImage(Assets.treeFull, xRender+xOffset, yRender+yOffset, Tile.TILEWIDTH*7, Tile.TILEHEIGHT*9, null);
			yRender = tempYRender;
		}
		if(i == 125)
		{
			xRender = 160;
			int tempYRender = yRender;
			yRender = 464;
			g.drawImage(Assets.fenceFull, xRender+xOffset, yRender+yOffset, Tile.TILEWIDTH*2, Tile.TILEHEIGHT*2, null);
			yRender = tempYRender;
		}
		if(i == 149)
		{
			xRender = 400;
			int tempYRender = yRender;
			yRender = 496;
			g.drawImage(Assets.stumpTreeFull, xRender+xOffset, yRender+yOffset, Tile.TILEWIDTH*4, Tile.TILEHEIGHT*4, null);
			yRender = tempYRender;
		}
	}
	
	public void selectTile()
	{
		int xSelect = 0;
		int ySelect = 32;
		
		for(int i = 0; i < Tile.tiles.length; i++)
		{
			if((Tile.tiles[i] != null) && (i < 15 || i >  122) && (i < 1000))
			{
				if(xSelect > 600)
				{
					xSelect = 0;
					ySelect += 32;
				}
				if(i == 123)
				{
					xSelect = 0;
					ySelect +=32;
				}
				xSelect += 32;
				
				if(((handler.getKeyManager().cX > xSelect+xOffset) && (handler.getKeyManager().cX < xSelect + 32 + xOffset)) && ((handler.getKeyManager().cY > ySelect+yOffset) && (handler.getKeyManager().cY < ySelect + 32 + yOffset)))
				{
					handler.getSelector().setNewTileId(i);
					handler.getKeyManager().cX = -1;
					handler.getKeyManager().cY = -1;
					handler.getMapMakerCamera().setZoomLevel(handler.getSelector().oldZoom);//keeping the old zoom after selecting a tile.
					State.setState(handler.getMapMaker().mapMakerState);
				}
			}
			//selecting larger tiles
			if((i == 15) || (i == 19) || (i == 23) || (i == 27) || (i == 31) || (i == 35)  || (i == 51) || (i == 125) || (i == 149))
				selectLargerTiles(xSelect, ySelect, i);
		}
		
		xSelect = 0;
		ySelect = 640;

		for(int i = 0; i < Tile.importedTiles.size(); i++)
		{
			if(Tile.importedTiles.get(i) != null)
			{
				if(xSelect > 600)
				{
					xSelect = 0;
					ySelect += 32;
				}
				xSelect += 32;
				
				if(((handler.getKeyManager().cX > xSelect+xOffset) && (handler.getKeyManager().cX < xSelect + 32 + xOffset)) && ((handler.getKeyManager().cY > ySelect+yOffset) && (handler.getKeyManager().cY < ySelect + 32 + yOffset)))
				{
					handler.getSelector().setNewTileId(i+1000);
					handler.getKeyManager().cX = -1;
					handler.getKeyManager().cY = -1;
					handler.getMapMakerCamera().setZoomLevel(handler.getSelector().oldZoom);//keeping the old zoom after selecting a tile.
					State.setState(handler.getMapMaker().mapMakerState);
				}
			}
		}
	}
	
	public void selectLargerTiles(int xSelect, int ySelect, int i)
	{
		//15,19,23,27,31,35,51,125
		int addNum = 0;
		if(i == 15)
		{
			xSelect = 160;
			ySelect = 416;
			addNum = 32;
		}
		if(i == 19)
		{
			xSelect = 208;
			ySelect = 416;
			addNum = 32;
		}
		if(i == 23)
		{
			xSelect = 256;
			ySelect = 416;
			addNum = 32;
		}
		if(i == 27)
		{
			xSelect = 304;
			ySelect = 416;
			addNum = 32;
		}
		if(i == 31)
		{
			xSelect = 352;
			ySelect = 416;
			addNum = 32;
		}
		if(i == 35)
		{
			xSelect = 400;
			ySelect = 416;
			addNum = 64;
		}
		if(i == 51)
		{
			xSelect = 32;
			ySelect = 416;
			addNum = 112;
		}
		if(i == 125)
		{
			xSelect = 160;
			ySelect = 464;
			addNum= 32;
		}
		if(i == 149)
		{
			xSelect = 400;
			ySelect = 496;
			addNum = 64;
		}
		
		if(((handler.getKeyManager().cX > xSelect+xOffset) && (handler.getKeyManager().cX < xSelect + addNum + xOffset)) && ((handler.getKeyManager().cY > ySelect+yOffset) && (handler.getKeyManager().cY < ySelect + addNum + yOffset)))
		{
			handler.getSelector().setNewTileId(i);
			handler.getKeyManager().cX = 0;
			handler.getKeyManager().cY = 0;
			State.setState(handler.getMapMaker().mapMakerState);
		}
	}
	
}