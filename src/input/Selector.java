package input;

import java.awt.Color;
import java.awt.Graphics;

import gfx.Assets;
import gfx.Sound;
import main.Handler;
import states.State;
import states.TileSelectorState;
import tiles.Tile;

public class Selector 
{
	private Handler handler;
	private float x, y;
	private int tileX, tileY;
	private int newTileId;
	private int currentLayer;
	public static int width, height;
	public int oldZoom;
	
	public Selector(Handler handler, float x, float y)
	{
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.currentLayer = 0;
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
	
	public int getCurrentLayer()
	{
		return this.currentLayer;
	}
	
	public void setCurrentLayer(int l)
	{
		this.currentLayer = l;
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
	
	private Tile getTileOnLocation()
	{
		return handler.getWorld().getTile(tileX, tileY, currentLayer);
	}
	
	//changing the tile on a specific location to a new tile.
	private void placeTileOnLocation(int x, int y, int tileId)
	{
		handler.getWorld().setTile(x, y, currentLayer, tileId);
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
		
			//ZOOMING(in / out)
			if(handler.getKeyManager().mouseWheelUp && !handler.getKeyManager().mouseWheelDown)
			{
				handler.getMapMakerCamera().zoomIn();
				handler.getKeyManager().mouseWheelUp = false;
			}
			if(handler.getKeyManager().mouseWheelDown && !handler.getKeyManager().mouseWheelUp)
			{
				handler.getMapMakerCamera().zoomOut();
				handler.getKeyManager().mouseWheelDown = false;
			}
			
			//SHIFTING LAYERS
			//UP LAYER
			if(handler.getKeyManager().layerUp && !handler.getKeyManager().layerDown)
			{
				if(this.currentLayer<3)
				{
					this.currentLayer++;
				}
				else//rolls over
				{
					this.currentLayer = 0;
				}
				handler.getWorld().setHighlightLayer(currentLayer);
				handler.getKeyManager().layerUp = false;
			}
			//DOWN LAYER
			if(handler.getKeyManager().layerDown && !handler.getKeyManager().layerUp)
			{
				if(this.currentLayer>0)
				{
					this.currentLayer--;
				}
				else//rolls over
				{
					this.currentLayer = 3;
				}
				handler.getWorld().setHighlightLayer(currentLayer);
				handler.getKeyManager().layerDown = false;
			}
			//highlighting layer
			if(handler.getKeyManager().highlight)
			{
				if(!handler.getWorld().getHighlight())
				{
					handler.getWorld().setHighlight(true);
					handler.getKeyManager().highlight = false;
				}
				else //unhighlighting
				{
					handler.getWorld().setHighlight(false);
					handler.getKeyManager().highlight = false;
				}
			}
			//BASE LAYER
			if(handler.getKeyManager().layerBase && !handler.getKeyManager().layerUp && !handler.getKeyManager().layerDown)
			{
				this.currentLayer = 0;
				handler.getWorld().setHighlightLayer(currentLayer);
				handler.getKeyManager().layerBase = false;
			}
			
			//LEFT CLICKING ON TILES, so they can be changed.
			if(handler.getKeyManager().leftClick)
			{
				if(this.newTileId != getTileOnLocation().getId()) //so it doesnt place the same tile thats already on the loc
				{	
					placeTileOnLocation(tileX, tileY, newTileId);
					Sound.playSfx("placeTile.wav");
				}
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
		
		//CONTROLS IN THE TILE SELECTION STATE
		if((State.getState() == handler.getMapMaker().tileSelectorState))
		{
			if(handler.getKeyManager().mouseWheelDown)
			{
				TileSelectorState.setYOffset(TileSelectorState.getYOffset()-32);
				handler.getKeyManager().mouseWheelDown = false;
			}
			if(handler.getKeyManager().mouseWheelUp)
			{
				TileSelectorState.setYOffset(TileSelectorState.getYOffset()+32);
				handler.getKeyManager().mouseWheelUp = false;
			}
		}
	
		//swapping between tileselection state, mapmaker state, and menu state
		if(handler.getKeyManager().openTileSelection || handler.getKeyManager().openMenu)
		{
			stateSwap(State.getState());
			handler.getKeyManager().openTileSelection = false;
			handler.getKeyManager().openMenu = false;
		}
	}
	
	private void stateSwap(State currentState)
	{
		if(handler.getKeyManager().openTileSelection)//swapping between tileselection and mapmaker states
		{
			if(currentState == handler.getMapMaker().mapMakerState)
			{
				handler.getKeyManager().cX = -1;
				handler.getKeyManager().cY = -1;
				//resetting zoom and getting old zoom level
				oldZoom = handler.getMapMakerCamera().getZoomLevel();
				handler.getMapMakerCamera().zoomReset();
				
				State.setState(handler.getMapMaker().tileSelectorState);
			}
			if(currentState == handler.getMapMaker().tileSelectorState)
			{
				handler.getMapMakerCamera().setZoomLevel(oldZoom);//keeping the old zoom after exiting tile selection state
				State.setState(handler.getMapMaker().mapMakerState);
			}	
		}
		if(handler.getKeyManager().openMenu)//Swapping between mainmenu /mapmaker / tileselection states
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
	
	public void tick()
	{
		getInput();
		updatePosition();
	}
	
	public void render(Graphics g)
	{
		g.drawImage(Assets.selector, (int) x, (int) y, width, height, null);
		if(State.getState() == handler.getMapMaker().mapMakerState)
		{
			g.setColor(Color.GRAY);
			g.fillRect(0, 0, 48, 48);
			g.drawImage(Tile.tiles[newTileId].getTexture(), 8, 8, 32, 32, null);
		}
		else
			if(State.getState() == handler.getMapMaker().tileSelectorState)
			{
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 32, 32);
				g.drawImage(Tile.tiles[newTileId].getTexture(), 8, 8, 16, 16, null);
			}
	}
}
