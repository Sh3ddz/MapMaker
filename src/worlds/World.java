package worlds;

import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import main.Handler;
import states.State;
import tiles.Tile;
import util.Utils;

public class World 
{
	private Handler handler;
	private int width, height;
	private int spawnX, spawnY;
	private int[][] tiles;
	
	public World(Handler handler, String path)
	{
		this.handler = handler;
		loadWorld(path);
	}
	
	public Tile getTile(int x, int y)
	{
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tile.grassTile;
		
		Tile t = Tile.tiles[tiles[x][y]];
		if(t == null)
			return Tile.grassTile;
		return t;
	}
	
	public void setTile(int x, int y, int id)
	{
		if(x < 0 || y < 0 || x >= width || y >= height)
			return;
		
		tiles[x][y] = id;
		
		//checking to generate a larger tile.
		if(id == 15 || id == 19 || id == 23 || id == 27 || id == 31 || id == 35 || id == 51)
		{
			generateLargerTiles(x,y);
		}
	}
	
	public int getSpawnX()
	{
		return spawnX;
	}
	
	public int getSpawnY()
	{
		return spawnY;
	}
	
	public int[][] getTiles()
	{
		return this.tiles;
	}
	
	private void loadWorld(String path)
	{
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		
		tiles = new int[width][height];
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				if(tiles[x][y] == 0)
					tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
				if(tiles[x][y] != 0)
					generateLargerTiles(x, y);
			}
		}
	}
	
	//Saving the world to a text file
	public void saveWorld() throws IOException
	{
		String fileName = "";
		
		//creating the filechooser and setting current directory.
		JFileChooser fileChooser = new JFileChooser();
		File workingDirectory = new File(System.getProperty("user.dir")+"/src/resources/worlds");
		fileChooser.setCurrentDirectory(workingDirectory);
		
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) 
		{
			File file = fileChooser.getSelectedFile();
			fileName = file.getName();
		}
		
		//file name error checking
		if(fileName == "" || fileName == null)//if they dont type anything it doesnt save.
		{
			return;
		}
		if(!fileName.substring(fileName.length()-4).equals(".txt"))
		{
			fileName = fileName+".txt";
		}
		//writing to the file
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/resources/worlds/"+fileName), "utf-8"))) 
		{
			writer.write(""+this.width+" "+this.height);
			((BufferedWriter) writer).newLine();
			writer.write("0 0");
			((BufferedWriter) writer).newLine();

			for(int y = 0; y < tiles[0].length; y++)
			{
				for(int x = 0; x < tiles.length; x++)
				{
					if(tiles[x][y] < 10)
					{
						writer.write("00"+tiles[x][y]);
					}
					else
						if(tiles[x][y] >= 10 && tiles[x][y] < 100)
						{
							writer.write("0"+tiles[x][y]);
						}
						else
						{
							writer.write(""+tiles[x][y]);
						}
					if(x != tiles.length-1)
					{
						writer.write(" ");
					}
				}
				((BufferedWriter) writer).newLine();
			}
			writer.flush();
			writer.close();
		}
		System.out.println("World Saved! path: src/resources/worlds/"+fileName);
	}
	
	public void loadNewWorld()
	{
		//creating the filechooser and setting current directory.
		JFileChooser fileChooser = new JFileChooser();
		File workingDirectory = new File(System.getProperty("user.dir")+"/src/resources/worlds");
		fileChooser.setCurrentDirectory(workingDirectory);
		
		//getting the path of the new world file and then loading it.
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
		{
		 	File file = fileChooser.getSelectedFile();
		 	String path = "src/resources/worlds/"+file.getName();
			loadWorld(path);
			System.out.println("Loaded "+path);
		}
	}
	
	public void newWorld()
	{
		//JOptionPane.showMessageDialog(handler.getMapMaker().getDisplay().getFrame(), "World size?");
		String tempOPString = JOptionPane.showInputDialog("New World Width");
		int newWorldWidth = 0;
		if(tempOPString != null)
			newWorldWidth = Integer.parseInt(tempOPString);
		
		tempOPString = JOptionPane.showInputDialog("New World Height");
		int newWorldHeight = 0;
		if(tempOPString != null)
			newWorldHeight = Integer.parseInt(tempOPString);
		
		tempOPString = JOptionPane.showInputDialog("1.)Random 2.)All Grass 3.)Ocean 4.) Forest");
		int generationOption = 0;
		if(tempOPString != null)
			generationOption = Integer.parseInt(tempOPString);
		
		if(newWorldWidth != 0 && newWorldHeight != 0 && generationOption != 0)
			createNewWorld(newWorldWidth, newWorldHeight, generationOption);
	}
	
	public void createNewWorld(int width, int height, int generationOption)
	{
		this.width = width;
		this.height = height;
		tiles = new int[width][height];
		System.out.println(tiles.length+","+tiles[0].length);
		generateWorld(generationOption);
		State.setState(handler.getMapMaker().mapMakerState);
	}
	
	public void generateWorld(int generationOption)
	{
		switch(generationOption)
		{
		case 1: //Completely random
		{
			for(int y = 0; y < height; y++)
			{
				for(int x = 0; x < width; x++)
				{
					tiles[x][y] = (int)(Math.random() * 128) + 1;
				}
			}
			break;
		}
		case 2: //All Grass
		{
			for(int y = 0; y < height; y++)
			{
				for(int x = 0; x < width; x++)
				{
					tiles[x][y] = 0;
				}
			}
			break;
		}
		case 3: //Ocean
		{
			for(int y = 0; y < height; y++)
			{
				for(int x = 0; x < width; x++)
				{
					int rand = (int)(Math.random() * 400) + 1;
					if(rand<=388)
						tiles[x][y] = 12;
					if(rand>388 && rand<=398)
						tiles[x][y] = 13;
					if(rand>398)
						tiles[x][y] = 14;
				}
			}
			break;
		}
		case 4: //Forest
		{
			for(int y = 0; y < height; y++)
			{
				for(int x = 0; x < width; x++)
				{
					int rand = (int)(Math.random() * 1000) + 1;
					if(rand<=900)
						tiles[x][y] = 0;
					if(rand>900 && rand <=910)
						tiles[x][y] = 1;
					if(rand>910&& rand <=920)
						tiles[x][y] = 2;
					if(rand>920&& rand <=930)
						tiles[x][y] = 3;
					if(x>1)
						if(tiles[x-1][y] == 3)
							tiles[x][y] = 4;
				}
			}
			for(int y = 0; y < height; y++)
			{
				for(int x = 0; x < width; x++)
				{
					int rand = (int)(Math.random() * 1000) + 1;
					if((x>1 && y>1) && (x<width-7 && y<height-10))
						//checking each corner of the tree to place to see if it overlaps any other tree.
						if((rand>0 && rand <=5) && 
						   (tiles[x-1][y]<51 || tiles[x-1][y]>122)  && 
						   (tiles[x+7][y]<51 || tiles[x+7][y]>122) && 
						   (tiles[x][y+10]<51 || tiles[x][y+10]>122) &&
						   (tiles[x+7][y+10]<51 || tiles[x+7][y+10]>122))
							tiles[x][y] = 51;
					generateLargerTiles(x,y);
				}
			}
			break;
		}
		}
	}
	
	private void generateLargerTiles(int x, int y)
	{
		//making bush
		if(tiles[x][y] == 15)
		{
			if((x+1<tiles[x].length)&&(y+1<tiles.length))
			{
				tiles[x][y] = 15;
				tiles[x+1][y] = 16;
				tiles[x][y+1] = 17;
				tiles[x+1][y+1] = 18;
			}
		}
		
		//making rock
		if(tiles[x][y] == 19)
		{
			if((x+1<tiles[x].length)&&(y+1<tiles.length))
			{
				tiles[x][y] = 19;
				tiles[x+1][y] = 20;
				tiles[x][y+1] = 21;
				tiles[x+1][y+1] = 22;
			}
		}
		
		//making struc
		if(tiles[x][y] == 23)
		{
			if((x+1<tiles[x].length)&&(y+1<tiles.length))
			{
				tiles[x][y] = 23;
				tiles[x+1][y] = 24;
				tiles[x][y+1] = 25;
				tiles[x+1][y+1] = 26;
			}
		}
		
		//making tallGrass
		if(tiles[x][y] == 27)
		{
			if((x+1<tiles[x].length)&&(y+1<tiles.length))
			{
				tiles[x][y] = 27;
				tiles[x+1][y] = 28;
				tiles[x][y+1] = 29;
				tiles[x+1][y+1] = 30;
			}
		}
		
		//making sign
		if(tiles[x][y] == 31)
		{
			if((x+1<tiles[x].length)&&(y+1<tiles.length))
			{
				tiles[x][y] = 31;
				tiles[x+1][y] = 32;
				tiles[x][y+1] = 33;
				tiles[x+1][y+1] = 34;
			}
		}
		
		//making stump
		if(tiles[x][y] == 35)
		{
			if((x+3<tiles[x].length)&&(y+3<tiles.length))
			{
				tiles[x][y] = 35;
				tiles[x+1][y] = 36;
				tiles[x+2][y] = 37;
				tiles[x+3][y] = 38;
				tiles[x][y+1] = 39;
				tiles[x+1][y+1] = 40;
				tiles[x+2][y+1] = 41;
				tiles[x+3][y+1] = 42;
				tiles[x][y+2] = 43;
				tiles[x+1][y+2] = 44;
				tiles[x+2][y+2] = 45;
				tiles[x+3][y+2] = 46;
				tiles[x][y+3] = 47;
				tiles[x+1][y+3] = 48;
				tiles[x+2][y+3] = 49;
				tiles[x+3][y+3] = 50;
			}
		}
		
		//making tree
		if(tiles[x][y] == 51)
		{
			//so it doesnt crash when going off board
			if((x>=1)&&(x+6<tiles[x].length)&&(y+9<tiles.length))
			{
				tiles[x][y] = 51;
				tiles[x+1][y] = 52;
				tiles[x+2][y] = 53;
				tiles[x+3][y] = 54;
				tiles[x+4][y] = 55;
				tiles[x+5][y] = 56;
				tiles[x-1][y+1] = 57;
				tiles[x][y+1] = 58;
				tiles[x+1][y+1] = 59;
				tiles[x+2][y+1] = 60;
				tiles[x+3][y+1] = 61;
				tiles[x+4][y+1] = 62;
				tiles[x+5][y+1] = 63;
				tiles[x+6][y+1] = 64;
				tiles[x-1][y+2] = 65;
				tiles[x][y+2] = 66;
				tiles[x+1][y+2] = 67;
				tiles[x+2][y+2] = 68;
				tiles[x+3][y+2] = 69;
				tiles[x+4][y+2] = 70;
				tiles[x+5][y+2] = 71;
				tiles[x+6][y+2] = 72;
				tiles[x-1][y+3] = 73;
				tiles[x][y+3] = 74;
				tiles[x+1][y+3] = 75;
				tiles[x+2][y+3] = 76;
				tiles[x+3][y+3] = 77;
				tiles[x+4][y+3] = 78;
				tiles[x+5][y+3] = 79;
				tiles[x+6][y+3] = 80;
				tiles[x-1][y+4] = 81;
				tiles[x][y+4] = 82;
				tiles[x+1][y+4] = 83;
				tiles[x+2][y+4] = 84;
				tiles[x+3][y+4] = 85;
				tiles[x+4][y+4] = 86;
				tiles[x+5][y+4] = 87;
				tiles[x+6][y+4] = 88;
				tiles[x-1][y+5] = 89;
				tiles[x][y+5] = 90;
				tiles[x+1][y+5] = 91;
				tiles[x+2][y+5] = 92;
				tiles[x+3][y+5] = 93;
				tiles[x+4][y+5] = 94;
				tiles[x+5][y+5] = 95;
				tiles[x+6][y+5] = 96;
				tiles[x-1][y+6] = 97;
				tiles[x][y+6] = 98;
				tiles[x+1][y+6] = 99;
				tiles[x+2][y+6] = 100;
				tiles[x+3][y+6] = 101;
				tiles[x+4][y+6] = 102;
				tiles[x+5][y+6] = 103;
				tiles[x+6][y+6] = 104;
				tiles[x-1][y+7] = 105;
				tiles[x][y+7] = 106;
				tiles[x+1][y+7] = 107;
				tiles[x+2][y+7] = 108;
				tiles[x+3][y+7] = 109;
				tiles[x+4][y+7] = 110;
				tiles[x+5][y+7] = 111;
				tiles[x+6][y+7] = 112;
				tiles[x][y+8] = 113;
				tiles[x+1][y+8] = 114;
				tiles[x+2][y+8] = 115;
				tiles[x+3][y+8] = 116;
				tiles[x+4][y+8] = 117;
				tiles[x+5][y+8] = 118;
				tiles[x+1][y+9] = 119;
				tiles[x+2][y+9] = 120;
				tiles[x+3][y+9] = 121;
				tiles[x+4][y+9] = 122;
			}
		}
	}
	
	public void tick()
	{

	}
	
	public void render(Graphics g)
	{
		int xStart = (int) Math.max(0, handler.getMapMakerCamera().getxOffset() / Tile.TILEWIDTH);
		int xEnd = (int) Math.min(width, (handler.getMapMakerCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH +1);
		int yStart = (int) Math.max(0, handler.getMapMakerCamera().getyOffset() / Tile.TILEHEIGHT);
		int yEnd = (int) Math.min(height, (handler.getMapMakerCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT +1);
		
		for(int y = yStart; y < yEnd; y++)
		{
			for(int x = xStart; x < xEnd; x++)
			{
				getTile(x, y).render(g, (int)(x * Tile.TILEWIDTH - handler.getMapMakerCamera().getxOffset()), (int)(y * Tile.TILEHEIGHT - handler.getMapMakerCamera().getyOffset()));
			}
		}
	}
}
