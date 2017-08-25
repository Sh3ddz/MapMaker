package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;

import input.Selector;
import main.Handler;
import worlds.World;

public class MenuState extends State
{
	private Selector selector;
	private World world;
	public boolean inControlsMenu;
	private static Font headerFont = new Font("Arial", Font.BOLD, 30);
	private static Font menuFont = new Font("Arial",Font.PLAIN, 20);

	public MenuState(Handler handler)
	{
		super(handler);
		//getting the original world
		this.world = handler.getWorld();
		this.selector = new Selector(handler, world.getSpawnX(), world.getSpawnY());
	}

	@Override
	public void tick() throws IOException
	{
		selector.tick();
		checkClickedButtons();
	}

	@Override
	public void render(Graphics g)
	{
		mainMenu(g);
	    
	    if(inControlsMenu)
	    {
	    	controlsMenu(g);
	    }
	}
	
	//checks if the buttons are clicked on
	public void checkClickedButtons() throws IOException
	{
		//CHECKING MOUSE CLICK AREAS
		//if its not in controls menu.
		if(!inControlsMenu)
		{
			//CONTROLS BUTTON
			if(State.getState() == handler.getMapMaker().menuState)
			{
				if((handler.getKeyManager().cX >= 10 && handler.getKeyManager().cX <= 210) && (handler.getKeyManager().cY >= 295 && handler.getKeyManager().cY <= 345))
				{
					System.out.println("Controls button clicked, going into controls menu");
					handler.getKeyManager().cX = -1;
					handler.getKeyManager().cY = -1;
					inControlsMenu = true;
				}
			}
			
			//NEW WORLD BUTTON
			if(State.getState() == handler.getMapMaker().menuState)
			{
				if((handler.getKeyManager().cX >= 220 && handler.getKeyManager().cX <= 420) && (handler.getKeyManager().cY >= 70 && handler.getKeyManager().cY <= 120))
				{
					System.out.println("New World button clicker, creating a new world.");
					handler.getKeyManager().cX = -1;
					handler.getKeyManager().cY = -1;
				}
			}
		
			//LOAD WORLD BUTTON
			if(State.getState() == handler.getMapMaker().menuState)
			{
				if((handler.getKeyManager().cX >= 220 && handler.getKeyManager().cX <= 420) && (handler.getKeyManager().cY >= 145 && handler.getKeyManager().cY <= 195))
				{
					System.out.println("Load World clicked, loading a world.");
					handler.getKeyManager().cX = -1;
					handler.getKeyManager().cY = -1;
				}
			}
		
			//SAVE WORLD BUTTON
			if(State.getState() == handler.getMapMaker().menuState)
			{
				if((handler.getKeyManager().cX >= 220 && handler.getKeyManager().cX <= 420) && (handler.getKeyManager().cY >= 220 && handler.getKeyManager().cY <= 270))
				{
					handler.getWorld().saveWorld();
					System.out.println("Save World clicked, saving the world.");
					handler.getKeyManager().cX = -1;
					handler.getKeyManager().cY = -1;
				}
			}
		
			//EXIT BUTTON
			if(State.getState() == handler.getMapMaker().menuState)
			{
				if((handler.getKeyManager().cX >= 220 && handler.getKeyManager().cX <= 420) && (handler.getKeyManager().cY >= 295 && handler.getKeyManager().cY <= 345))
				{	
					System.out.println("EXITING.");
					handler.getKeyManager().cX = -1;
					handler.getKeyManager().cY = -1;
					System.exit(1);
				}
			}
			
			//BACK BUTTON
			if(State.getState() == handler.getMapMaker().menuState)
			{
			    if((handler.getKeyManager().cX >= 475 && handler.getKeyManager().cX <= 625) && (handler.getKeyManager().cY >= 20 && handler.getKeyManager().cY <= 70))
				{	
					System.out.println("Going back to Map Maker");
					handler.getKeyManager().cX = -1;
					handler.getKeyManager().cY = -1;
					State.setState(handler.getMapMaker().mapMakerState);
				}
			}
		}
		//if its in the controls menu
		else
		{
			//BACK BUTTON
			if(State.getState() == handler.getMapMaker().menuState)
			{
				//220,70,400,50
			    if((handler.getKeyManager().cX >= 475 && handler.getKeyManager().cX <= 625) && (handler.getKeyManager().cY >= 20 && handler.getKeyManager().cY <= 70))
				{	
					System.out.println("Going back to the Main Menu");
					handler.getKeyManager().cX = -1;
					handler.getKeyManager().cY = -1;
					this.inControlsMenu = false;
				}
			}
		}
		
	}
	
	public void mainMenu(Graphics g)
	{
		//Drawing background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1920, 1080);
		//MENU text
		g.setColor(Color.RED);
	    g.setFont(headerFont);
	    g.drawString("MENU", 280, 40);
	    
	    g.setFont(menuFont);
	    
		//CONTROLS button
	    if((handler.getKeyManager().mX >= 10 && handler.getKeyManager().mX <= 210) && (handler.getKeyManager().mY >= 295 && handler.getKeyManager().mY <= 345))
	    	g.setColor(Color.GRAY);
	    else
	    	g.setColor(Color.WHITE);
	    g.fillRect(10,295,200,50);
	    g.setColor(Color.BLACK);  
	    g.drawString("Controls", 75, 325);
	    
		//NEW WORLD button
	    if((handler.getKeyManager().mX >= 220 && handler.getKeyManager().mX <= 420) && (handler.getKeyManager().mY >= 70 && handler.getKeyManager().mY <= 120))
	    	g.setColor(Color.GRAY);
	    else
	    	g.setColor(Color.WHITE);
	    g.fillRect(220,70,200,50);
	    g.setColor(Color.BLACK);  
	    g.drawString("New World", 275, 100);
	    
	    //LOAD WORLD button
	    if((handler.getKeyManager().mX >= 220 && handler.getKeyManager().mX <= 420) && (handler.getKeyManager().mY >= 145 && handler.getKeyManager().mY <= 195))
	    	g.setColor(Color.GRAY);
	    else
	    	g.setColor(Color.WHITE);
	    g.fillRect(220,145,200,50);
	    g.setColor(Color.BLACK);  
	    g.drawString("Load World", 270, 175);
	    
	    //SAVE WORLD button
	    if((handler.getKeyManager().mX >= 220 && handler.getKeyManager().mX <= 420) && (handler.getKeyManager().mY >= 220 && handler.getKeyManager().mY <= 270))
	    	g.setColor(Color.GRAY);
	    else
	    	g.setColor(Color.WHITE);
	    g.fillRect(220,220,200,50);
	    g.setColor(Color.BLACK);  
	    g.drawString("Save World", 270, 250);
	    
	    //EXIT button
	    if((handler.getKeyManager().mX >= 220 && handler.getKeyManager().mX <= 420) && (handler.getKeyManager().mY >= 295 && handler.getKeyManager().mY <= 345))
	    	g.setColor(Color.GRAY);
	    else
	    	g.setColor(Color.WHITE);
	    g.fillRect(220,295,200,50);
	    g.setColor(Color.BLACK);  
	    g.drawString("EXIT MAP MAKER", 235, 325);
	    
	    //BACK button
	    if((handler.getKeyManager().mX >= 475 && handler.getKeyManager().mX <= 625) && (handler.getKeyManager().mY >= 20 && handler.getKeyManager().mY <= 70))
	    	g.setColor(Color.GRAY);
	    else
	    	g.setColor(Color.WHITE);
	    g.fillRect(475,20,150,50);
	    g.setColor(Color.BLACK);  
	    g.drawString("<-Back", 495, 50);
	}
	
	public void controlsMenu(Graphics g)
	{
		//Drawing background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1920, 1080);
		
		//CONTROLS text
		g.setColor(Color.RED);
	    g.setFont(headerFont);
	    g.drawString("CONTROLS", 235, 40);
	    
	    //Showing the controls
	    g.setFont(menuFont);
		g.setColor(Color.BLUE);
	    g.drawString("WASD/Arrow Keys", 145, 100);
	    g.drawString("Escape", 245, 130);
	    g.drawString("E", 300, 160);
	    g.drawString("- OR scrollDown", 167, 190);
	    g.drawString("= OR scrollUp", 190, 220);
	    g.drawString("0", 300, 250);
	    g.drawString("Left Click", 230, 280);
	    g.drawString("Middle Click", 205, 310);
		g.setColor(Color.YELLOW);
	    g.drawString("| Move the camera", 320, 100);
	    g.drawString("| Open / Close Menu", 320, 130);
	    g.drawString("| Open / Close Tile Selection", 320, 160);
	    g.drawString("| Zoom Out", 320, 190);
	    g.drawString("| Zoom In", 320, 220);
	    g.drawString("| Zoom Reset", 320, 250);
	    g.drawString("| Place a tile", 320, 280);
	    g.drawString("| Copy current selected tile", 320, 310);

	    //BACK button
	    if((handler.getKeyManager().mX >= 475 && handler.getKeyManager().mX <= 625) && (handler.getKeyManager().mY >= 20 && handler.getKeyManager().mY <= 70))
	    	g.setColor(Color.GRAY);
	    else
	    	g.setColor(Color.WHITE);
	    g.fillRect(475,20,150,50);
	    g.setColor(Color.BLACK);  
	    g.drawString("<-Back", 495, 50);
	}
	
}