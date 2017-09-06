package MapMaker;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import display.Display;
import gfx.Assets;
import gfx.MapMakerCamera;
import gfx.Sound;
import input.KeyManager;
import input.Selector;
import main.Handler;
import states.MapMakerState;
import states.MenuState;
import states.State;
import states.TileSelectorState;

	//TO DO 
	//  : work on menu state [could use better graphics lol.]
	//  : Implement world saving to a text file [Pretty much finished.]-----------------------[Needs to be able to choose file name and location its saved to.]
	//	: add tile selection menu [finished with currently added tiles]-----------------------
	//  : fix zooming issues. [mainly fixed, other than offset]-------------------------------
	//  : add an undo option []
	//  : maybe add an option to make larger brush sizes [probably not.]
	//  : ADD A NEW WORLD OPTION TO CREATE NEW WORLDS
	//		-POTENTIAL PROCEDURAL GENERATION OPTION
	//		-BIOME GENERATION
	//		-MINECRAFT ESQUE IN A WAY.
	//		-DIFFERENT DEFAULT SETTINGS SUCH AS - default grass world, different sized worlds etc.
public class MapMaker implements Runnable
{

	private Display display;
	private int width, height;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	//States
	public State menuState;
	public State mapMakerState;
	public State tileSelectorState;
	
	//Input
	private KeyManager keyManager;
	private Selector selector;
	
	//Camera
	private MapMakerCamera mapMakerCamera;
	
	//Handler
	private Handler handler;
	
	public MapMaker(String title, int width, int height)
	{
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();
	}
	
	private void init()
	{
		this.display = new Display(title, width, height);
		//adding keyManager. / input listeners to the frame and canvas.
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(keyManager);
		display.getFrame().addMouseMotionListener(keyManager);
		display.getFrame().addMouseWheelListener(keyManager);
		display.getCanvas().addMouseListener(keyManager);
		display.getCanvas().addMouseMotionListener(keyManager);
		display.getCanvas().addMouseWheelListener(keyManager);

		Assets.init();
		Sound.init();
		
		Sound.playMusic(Sound.songs[0]);

		Sound.setMusicVolume(-20.0f);
		Sound.setSfxVolume(-30.0f);
		
		this.handler = new Handler(this);
		this.selector = new Selector(handler, 0, 0);
		this.handler.setSelector(selector);
		
		this.mapMakerCamera = new MapMakerCamera(this, handler.getWorld(), handler);

		this.mapMakerState = new MapMakerState(handler);
		this.menuState = new MenuState(handler);
		this.tileSelectorState = new TileSelectorState(handler);
		State.setState(mapMakerState);
	}
	
	private void tick() throws IOException
	{
		keyManager.tick();
		if(State.getState() != null)
			State.getState().tick();
	}
	
	private void render()
	{
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null)
		{
			display.getCanvas().createBufferStrategy(3); //original is 3
			return;
		}
		g = bs.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0, width, height);
		//Draw Here!
		
		if(State.getState() != null)
			State.getState().render(g);
		//End Drawing!
		bs.show();
		g.dispose();
	}
	
	public void run()
	{
		
		init();
		
		int tps = 60;
		int fps = Integer.MAX_VALUE; //unlimited FPS
		double timePerTick = 1000000000 / tps;
		double timePerRender = 1000000000 / fps;
		double delta = 0;
		double delta2 = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		int frames = 0;
		
		while(running)
		{
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			delta2 += (now - lastTime) / timePerRender;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1)//ticking
			{
				try
				{
					tick();
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
				ticks++;
				delta--;
			}
			
			if(delta2 >= 1)//rendering
			{
				render();
				frames++;
				delta2--;
			}
			
			if(timer >= 1000000000)
			{
				System.out.println("Ticks: " + ticks);
				System.out.println("Frames: " + frames);
				ticks = 0;
				frames = 0;
				timer = 0;
			}
		}
		
		stop();
		
	}
	
	public Display getDisplay()
	{
		return this.display;
	}
	
	public KeyManager getKeyManager()
	{
		return this.keyManager;
	}
	
	public MapMakerCamera getMapMakerCamera()
	{
		return mapMakerCamera;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public synchronized void start()
	{
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop()
	{
		if(!running)
			return;
		running = false;
		try 
		{
			thread.join();
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
}
