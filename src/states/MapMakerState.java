package states;

import java.awt.Graphics;

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
		this.world = new World(handler, "src/resources/worlds/Graal.txt");
		handler.setWorld(world);
		this.selector = handler.getSelector();
	}
	
	@Override
	public void tick()
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
