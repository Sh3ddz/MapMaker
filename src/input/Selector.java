package input;

import java.awt.Graphics;
import java.io.IOException;

import gfx.Assets;
import main.Handler;
import states.State;
import tiles.Tile;

public class Selector 
{
	private Handler handler;
	private float x, y;
	private int tileX, tileY;
	public static int width, height;
	private int newTileId;
	
	public Selector(Handler handler, float x, float y)
	{
		this.handler = handler;
		this.x = x;
		this.y = y;
		Selector.width = Tile.TILEWIDTH;
		Selector.height = Tile.TILEHEIGHT;
		this.newTileId = 0; // what the tile being clicked on will change to
	}
	
	public float getX()
	{
		return x;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public int getNewTileId()
	{
		return newTileId;
	}
	
	public void setNewTileId(int id)
	{
		this.newTileId = id;
	}
	
	public void updatePosition()
	{
		if(State.getState() == handler.getMapMaker().mapMakerState)
		{
			//what tile its on
			this.tileX = (int) ((handler.getKeyManager().mX + handler.getMapMakerCamera().getxOffset())/(Tile.TILEWIDTH));
			this.tileY = (int) ((handler.getKeyManager().mY + handler.getMapMakerCamera().getyOffset())/(Tile.TILEHEIGHT));
			//drawing it on the right place on the screen
			this.x = ((tileX - (handler.getMapMakerCamera().getxOffset()/(Tile.TILEWIDTH))) * (Tile.TILEWIDTH));
			this.y = ((tileY - (handler.getMapMakerCamera().getyOffset()/(Tile.TILEHEIGHT))) * (Tile.TILEHEIGHT));
		}
		
		if(State.getState() == handler.getMapMaker().tileSelectorState)
		{
			this.tileX = (int) ((handler.getKeyManager().mX + 0)/(Tile.TILEWIDTH*2));
			this.tileY = (int) ((handler.getKeyManager().mY + 0)/(Tile.TILEHEIGHT*2));
			this.x = ((tileX - (0/(Tile.TILEWIDTH*2))) * (Tile.TILEWIDTH*2));
			this.y = ((tileY - (0/(Tile.TILEHEIGHT*2))) * (Tile.TILEHEIGHT*2));
		}
		
	}
	
	public Tile getTileOnLocation()
	{
		Tile tile = null;
		tile = handler.getWorld().getTile(tileX, tileY); 
		System.out.println(tile.getId());
		return tile;
	}
	
	//changing the tile on a specific location to a new tile.
	public void updateTileOnLocation(int x, int y, int tileId)
	{
		handler.getWorld().setTile(x, y, tileId);
	}
	
	private void getInput()
	{
		if(State.getState() == handler.getMapMaker().mapMakerState)
		{
			//WASD KEYS
			if(handler.getKeyManager().up && !handler.getKeyManager().down)
				handler.getMapMakerCamera().moveByTile(0,-1);
			if(handler.getKeyManager().down && !handler.getKeyManager().up)
				handler.getMapMakerCamera().moveByTile(0,1);
			if(handler.getKeyManager().left && !handler.getKeyManager().right)
				handler.getMapMakerCamera().moveByTile(-1,0);
			if(handler.getKeyManager().right && !handler.getKeyManager().left)
				handler.getMapMakerCamera().moveByTile(1,0);
		
			//ARROW KEYS
			if(handler.getKeyManager().upArrow && !handler.getKeyManager().downArrow)
				handler.getMapMakerCamera().moveByTile(0,-1);
			if(handler.getKeyManager().downArrow && !handler.getKeyManager().upArrow)
				handler.getMapMakerCamera().moveByTile(0,1);
			if(handler.getKeyManager().leftArrow && !handler.getKeyManager().rightArrow)
				handler.getMapMakerCamera().moveByTile(-1,0);
			if(handler.getKeyManager().rightArrow && !handler.getKeyManager().leftArrow)		
				handler.getMapMakerCamera().moveByTile(1,0);
		
			//ZOOMING(in / out / resetting)
			if((handler.getKeyManager().zoomIn && !handler.getKeyManager().zoomOut) || (handler.getKeyManager().mouseWheelUp && !handler.getKeyManager().mouseWheelDown))
			{
				handler.getMapMakerCamera().zoomIn();
				handler.getKeyManager().zoomIn = false;
				handler.getKeyManager().mouseWheelUp = false;
			}
			if((handler.getKeyManager().zoomOut && !handler.getKeyManager().zoomIn) || (handler.getKeyManager().mouseWheelDown && !handler.getKeyManager().mouseWheelUp))
			{
				handler.getMapMakerCamera().zoomOut();
				handler.getKeyManager().zoomOut = false;
				handler.getKeyManager().mouseWheelDown = false;
			}
			if(handler.getKeyManager().zoomReset)
			{	
				handler.getMapMakerCamera().zoomReset();
				handler.getKeyManager().zoomReset = false;
			}
			
			//LEFT CLICKING ON TILES, so they can be changed.
			if(handler.getKeyManager().leftClick)
			{
				 updateTileOnLocation(tileX, tileY, newTileId);
			}
			//RIGHT CLICKING ON TILES, gets current tile
			if(handler.getKeyManager().rightClick)
			{
				 getTileOnLocation();
			}
			//MIDDLE CLICKING ON TILES, copies the tile on position to the current tile.
			if(handler.getKeyManager().middleClick)
			{
				 this.setNewTileId(getTileOnLocation().getId());
			}
		}
	
		//swapping between tileselection state and mapmaker state
		if(handler.getKeyManager().stateSwap || handler.getKeyManager().escape)
		{
			stateSwap(State.getState());
			handler.getKeyManager().stateSwap = false;
			handler.getKeyManager().escape = false;
		}
	}
	
	public void toggleInput(boolean pressing)
	{
		
	}
	
	public void stateSwap(State currentState)
	{
		if(handler.getKeyManager().stateSwap)
		{
			if(currentState == handler.getMapMaker().mapMakerState)
			{
				handler.getKeyManager().cX = -1;
				handler.getKeyManager().cY = -1;
				State.setState(handler.getMapMaker().tileSelectorState);
			}
			if(currentState == handler.getMapMaker().tileSelectorState)
			{
				State.setState(handler.getMapMaker().mapMakerState);
			}		
		}
		if(handler.getKeyManager().escape)
		{
			if(currentState == handler.getMapMaker().mapMakerState)
			{
				handler.getKeyManager().cX = -1;
				handler.getKeyManager().cY = -1;
				State.setState(handler.getMapMaker().menuState);
			}
			if(currentState == handler.getMapMaker().tileSelectorState)
			{
				State.setState(handler.getMapMaker().menuState);
			}	
			if(currentState == handler.getMapMaker().menuState)
			{
				State.setState(handler.getMapMaker().mapMakerState);
			}		
		}
	}
	
	public void tick() throws IOException
	{
		getInput();
		updatePosition();
	}
	
	public void render(Graphics g)
	{
		g.drawImage(Assets.selector, (int) x, (int) y, width, height, null);
	}
}
