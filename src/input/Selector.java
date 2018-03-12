package input;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Stack;

import gfx.Assets;
import gfx.Sound;
import main.Handler;
import states.State;
import states.TileSelectorState;
import tiles.Tile;

public class Selector 
{
	private Handler handler;
	private boolean filling;
	private float x, y;
	private int tileX, tileY;
	private int newTileId;
	private int currentLayer;
	public static int width, height;
	public int oldZoom;
	private ArrayList<Change> currentChangeList; //changes made to the map
	private Stack<ArrayList<Change>> allChangesStack; //stack of all the changes made
	private Stack<ArrayList<Change>> allChangesUndone; //stack of all the UNDO's
	
	public Selector(Handler handler, float x, float y)
	{
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.currentLayer = 0;
		Selector.width = Tile.TILEWIDTH;
		Selector.height = Tile.TILEHEIGHT;
		this.newTileId = 0; // what the tile being clicked on will change to
		this.currentChangeList = new ArrayList<Change>();
		this.allChangesStack = new Stack<ArrayList<Change>>();
		this.allChangesUndone = new Stack<ArrayList<Change>>();
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
	
	public void addChange(Change c)
	{
		if(currentChangeList == null)
			currentChangeList = new ArrayList<Change>();
		this.currentChangeList.add(c);
	}
	
	private Tile getTileOnLocation()
	{
		return handler.getWorld().getTile(tileX, tileY, currentLayer);
	}
	
	//changing the tile on a specific location to a new tile.
	private void placeTileOnLocation(int x, int y, int tileId)
	{
		//setting the tile change
		handler.getWorld().setTile(x, y, currentLayer, tileId);
	}
	
	//Fills tiles (recursive) this will cause stack overflows if you try to fill areas that are too large
	@SuppressWarnings("unused")
	private void fill(int x, int y, int oldTileId)
	{
	    // Base cases
	    if ((x < 0 || x >= handler.getWorld().getWidth()) || (y < 0 || y >= handler.getWorld().getHeight()))
	        return;
	    if (handler.getWorld().getTile(x,y,currentLayer).getId() != oldTileId)
	        return;
	 
	    // Replace the tile at (x, y)
	    placeTileOnLocation(x, y, newTileId);
	    
	    // Recur for north, east, south and west
	    fill(x+1, y, oldTileId);
	    fill(x-1, y, oldTileId);
	    fill(x, y+1, oldTileId);
	    fill(x, y-1, oldTileId);
	}
	
	//Fills tiles, non-recursive (TileX, TileY, value of old tile ID)
	private void fill2(int x, int y, int oldTileId)
	{
		//created a temp class to store the tile location value
		class Location
		{
			public int x, y;
			public Location(int x, int y)
			{
				this.x = x;
				this.y = y;
			}
		}
		//creating a stack of locations of where next to place a tile at
		Stack<Location> fillList = new Stack<Location>();
		Location l = new Location(x,y);
		fillList.add(l);
		//fill loop, runs until there's nowhere left to fill
		while(!fillList.isEmpty())
		{
			//pops the top location off the stack
			l = fillList.pop();
			int x1 = l.x;
			int y1 = l.y;
			//fills the tile at determined point
			//System.out.println("Placing tile on: "+x1+","+y1);
		    placeTileOnLocation(x1, y1, newTileId);
		    //checking up down left and right for valid filling.
		    if(checkFillValidity(x1+1, y1, oldTileId))
		    {
		    	l = new Location(x1+1, y1);
		    	fillList.push(l);
		    }
			if(checkFillValidity(x1-1, y1, oldTileId))
		    {
		    	l = new Location(x1-1, y1);
		    	fillList.push(l);
		    }
			if(checkFillValidity(x1, y1+1, oldTileId))
		    {
		    	l = new Location(x1, y1+1);
		    	fillList.push(l);
		    }
			if(checkFillValidity(x1, y1-1, oldTileId))
		    {
		    	l = new Location(x1, y1-1);
		    	fillList.push(l);
		    }
		}
	}
	//checks if the next point is a valid fill location
	private boolean checkFillValidity(int x, int y, int oldTileId)
	{
	    if ((x < 0 || x >= handler.getWorld().getWidth()) || (y < 0 || y >= handler.getWorld().getHeight()))
	        return false;
	    
	    if (handler.getWorld().getTile(x,y,currentLayer).getId() != oldTileId)
	    	return false;
	    	else
	    		return true;
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
	
	private void getInput()
	{
		if(State.getState() == handler.getMapMaker().mapMakerState)
		{
			//Moving the camera
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
			
			//CHECKING ALL USER INPUT OPTIONS 
			//DIFFERENT TOOLS
			checkTools();
			//ZOOMING(in / out)
			checkZoom();
			//SHIFTING LAYERS
			checkLayerShift();
			//highlighting layer
			checkHighlight();
			//grid view
			checkGridview();			
			//LEFT CLICKING ON TILES, so they can be changed.
			checkClicks();
			//UNDOING / REDOING
			handleChanges();
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
	
	private void checkTools()
	{
		//FILL OPTION
		if(handler.getKeyManager().fillTool)
		{
			if(!this.filling)
			{
				this.filling = true;
				handler.getKeyManager().fillTool = false;
			}
			else
			{
				this.filling = false;
				handler.getKeyManager().fillTool = false;
			}			
		}
	}
	
	private void checkZoom()
	{
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
	}
	
	private void checkLayerShift()
	{
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
		//BASE LAYER
		if(handler.getKeyManager().layerBase && !handler.getKeyManager().layerUp && !handler.getKeyManager().layerDown)
		{
			this.currentLayer = 0;
			handler.getWorld().setHighlightLayer(currentLayer);
			handler.getKeyManager().layerBase = false;
		}
	}
	
	private void checkHighlight()
	{
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
	}
	
	private void checkGridview()
	{
		if(handler.getKeyManager().gridView)
		{
			if(!handler.getWorld().getGridView())
			{
				handler.getWorld().setGridView(true);
				handler.getKeyManager().gridView = false;
			}
			else
			{
				handler.getWorld().setGridView(false);
				handler.getKeyManager().gridView = false;
			}
		}
	}
	
	private void checkClicks()
	{
		if(handler.getKeyManager().leftClick)
		{
			if(this.newTileId != getTileOnLocation().getId()) //so it doesnt place the same tile thats already on the loc
			{	
				if(filling)
				{
					int oldTileId = handler.getWorld().getTile(tileX, tileY, currentLayer).getId();
					fill2(tileX, tileY, oldTileId);
				}
				else
				{
					//placing the tile
					placeTileOnLocation(tileX, tileY, newTileId);
					Sound.playSfx("placeTile.wav");
				}
			}
		}
		else
		{
			if(currentChangeList != null && currentChangeList.size() != 0) //if the list isnt empty, add it to the stack and then reset it.
			{
				allChangesStack.push(currentChangeList);
				currentChangeList = null;
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
		
		//RIGHT CLICKING to move camera
		if(handler.getKeyManager().rightClick)
		{
			int xMove = 0;
			int yMove = 0;
			if(handler.getKeyManager().dragging)
			{
				xMove = handler.getKeyManager().cX - handler.getKeyManager().dX;
				yMove = handler.getKeyManager().cY - handler.getKeyManager().dY;
				handler.getKeyManager().cX = handler.getKeyManager().dX;
				handler.getKeyManager().cY = handler.getKeyManager().dY;
			}
			handler.getMapMakerCamera().move(xMove, yMove);
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
	
	//CHANGES (UNDOING AND REDOING)
	private void handleChanges()
	{
		//UNDOING the changes
		if(handler.getKeyManager().ctrl && handler.getKeyManager().z)
		{
			if(allChangesStack.size() != 0) //if the stack isnt empty
			{
				ArrayList<Change> poppedList = allChangesStack.pop(); //pop the top change list from the stack
				
				for(int i = 0; i < poppedList.size(); i++)
				{
					poppedList.get(i).undo(); //undo the change
				}
				allChangesUndone.push(poppedList);//pushing the changes undone onto the undone stack.
			}
			handler.getKeyManager().z = false;
		}
		//REDOING the changes
		if(handler.getKeyManager().ctrl && handler.getKeyManager().y)
		{
			if(allChangesUndone.size() != 0) //if the stack isnt empty
			{
				ArrayList<Change> redoList = allChangesUndone.pop(); //pop the top redo list from the stack
				
				for(int i = 0; i < redoList.size(); i++)
				{
					redoList.get(i).redo(); //redo the change
				}
				allChangesStack.push(redoList);//pushing the changes re-done back onto the all changes stack
			}
			handler.getKeyManager().y = false;
		}
	}
	
	public void tick()
	{
		getInput();
		updatePosition();
	}
	
	public void render(Graphics g)
	{
		if(!filling)
			g.drawImage(Assets.selector, (int) x, (int) y, width, height, null);
		else
			g.drawImage(Assets.selectorFill, (int) x, (int) y, width, height, null);
		
		//Drawing the information
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
		if(State.getState() == handler.getMapMaker().mapMakerState)
		{
			g.setColor(Color.CYAN);
			g.drawString("LAYER:"+this.getCurrentLayer(), 0, 10);
			
			if(filling)
			{
				g.setColor(Color.CYAN);
				g.drawString("TOOL: FILL", 0, 45);
			}
			else
			{
				g.setColor(Color.CYAN);
				g.drawString("TOOL: PLACE", 0, 45);
			}
		}
	}
}
