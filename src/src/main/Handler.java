package main;

import MapMaker.MapMaker;
import gfx.MapMakerCamera;
import input.KeyManager;
import input.Selector;
import worlds.World;

public class Handler 
{
	private MapMaker mapMaker;
	private World world;
	private Selector selector;
	
	public Handler(MapMaker mapMaker)
	{
		this.mapMaker = mapMaker;
	}
	
	public MapMakerCamera getMapMakerCamera()
	{
		return mapMaker.getMapMakerCamera();
	}
	
	public KeyManager getKeyManager()
	{
		return mapMaker.getKeyManager();
	}
	
	public int getWidth()
	{
		return mapMaker.getWidth();
	}
	
	public int getHeight()
	{
		return mapMaker.getHeight();
	}
	
	public MapMaker getMapMaker() 
	{
		return mapMaker;
	}

	public void setGame(MapMaker mapMaker) 
	{
		this.mapMaker = mapMaker;
	}

	public World getWorld()
	{
		return world;
	}

	public void setWorld(World world) 
	{
		this.world = world;
	}
	
	public Selector getSelector()
	{
		return this.selector;
	}
	
	public void setSelector(Selector selector)
	{
		this.selector = selector;
	}
}
