package states;

import java.awt.Graphics;
import java.io.IOException;

import input.Selector;
import main.Handler;
import worlds.World;

public class MapMakerState extends State
{
	private Selector selector;
	private World world;
	
	public MapMakerState(Handler handler)
	{
		super(handler);
		this.world = new World(handler, "src/resources/worlds/NewSaveWorld.txt");
		handler.setWorld(world);
		this.selector = handler.getSelector();
	}
	
	@Override
	public void tick() throws IOException
	{
		world.tick();
		selector.tick();
	}

	@Override
	public void render(Graphics g) 
	{
		world.render(g);
		selector.render(g);
	}

}
