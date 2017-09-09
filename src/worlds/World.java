package worlds;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

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
	private ArrayList<int[][]> layers;
	private int[][] tilesLayer0;
	private int[][] tilesLayer1;
	private int[][] tilesLayer2;
	private int[][] tilesLayer3;
	
	private int highlightLayer = 0;
	private boolean highlight = false;
	private Color highlightColor = new Color(255,0,0,100);

	public World(Handler handler, String path)
	{
		this.handler = handler;
		loadWorld(path);
	}
	
	public Tile getTile(int x, int y, int layer)
	{
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tile.grassTile;
		
		Tile t = Tile.tiles[layers.get(layer)[x][y]];
		if(t == null)
			return Tile.grassTile;
		return t;
	}
	
	public void setTile(int x, int y, int layer, int id)
	{
		if(x < 0 || y < 0 || x >= width || y >= height)
			return;

		//gets specific layer and sets the tile.
		layers.get(layer)[x][y] = id;
		
		//checking to generate a larger tile. (only need to check the ones which can generate larger tiles.)
		if(id == 15 || id == 19 || id == 23 || id == 27 || id == 31 || id == 35 || id == 51 || id == 125)
		{
			generateLargerTiles(x, y, layer);
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
	
	public int[][] getTilesLayer0()
	{
		return this.tilesLayer0;
	}
	public int[][] getTilesLayer1()
	{
		return this.tilesLayer1;
	}
	public int[][] getTilesLayer2()
	{
		return this.tilesLayer2;
	}
	public int[][] getTilesLayer3()
	{
		return this.tilesLayer3;
	}
	
	public boolean getHighlight()
	{
		return this.highlight;
	}
	public void setHighlight(boolean h)
	{
		this.highlight = h;
	}
	public int getHighlightLayer()
	{
		return this.highlightLayer;
	}
	public void setHighlightLayer(int h)
	{
		this.highlightLayer = h;
	}
	
	private void loadWorld(String path)
	{
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		
		tilesLayer0 = new int[width][height];
		tilesLayer1 = new int[width][height];
		tilesLayer2 = new int[width][height];
		tilesLayer3 = new int[width][height]; 
		layers = new ArrayList<int[][]>();
		layers.add(tilesLayer0);
		layers.add(tilesLayer1);
		layers.add(tilesLayer2);
		layers.add(tilesLayer3);
		
		int token = 4; //which number its reading in
		for(int l = 0; l < layers.size(); l++)
		{
			for(int y = 0; y < height; y++)
			{
				for(int x = 0; x < width; x++)
				{
					if(layers.get(l)[x][y]  == 0)
						layers.get(l)[x][y] = Utils.parseInt(tokens[token]);
					if(layers.get(l)[x][y]  != 0)
						generateLargerTiles(x, y, l);
					token++;
				}
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

			for(int layer = 0; layer < layers.size(); layer++)
			{
				for(int y = 0; y < layers.get(layer)[0].length; y++)
				{
					for(int x = 0; x < layers.get(layer).length; x++)
					{
						if(layers.get(layer)[x][y] < 10)
						{
							writer.write("00"+layers.get(layer)[x][y]);
						}
						else
							if(layers.get(layer)[x][y] >= 10 && layers.get(layer)[x][y] < 100)
							{
								writer.write("0"+layers.get(layer)[x][y]);
							}
							else
							{
								writer.write(""+layers.get(layer)[x][y]);
							}
						if(x != layers.get(layer).length-1)
						{
							writer.write(" ");
						}
					}
					((BufferedWriter) writer).newLine();
				}
				if(layer!=layers.size())//indicate a new layer in the text file.(just an empty line between layers)
				{
					((BufferedWriter) writer).newLine();
				}
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
		tilesLayer0 = new int[width][height];
		tilesLayer1 = new int[width][height];
		tilesLayer2 = new int[width][height];
		tilesLayer3 = new int[width][height];
		layers = new ArrayList<int[][]>();
		layers.add(tilesLayer0);
		layers.add(tilesLayer1);
		layers.add(tilesLayer2);
		layers.add(tilesLayer3);
		
		System.out.println(tilesLayer0.length+","+tilesLayer0[0].length+","+generationOption);
		generateWorld(generationOption);
		State.setState(handler.getMapMaker().mapMakerState);
	}
	
	public void generateWorld(int generationOption)
	{
		switch(generationOption)
		{
		case 1: //Completely random
		{
			for(int l = 0; l < layers.size(); l++)
			{
				for(int y = 0; y < height; y++)
				{
					for(int x = 0; x < width; x++)
					{
						layers.get(l)[x][y] = (int)(Math.random() * 128) + 1;
						if(l!=0)//setting all the other layers to air by default.
						{
							layers.get(l)[x][y] = 999;
						}
					}
				}
			}
			break;
		}
		case 2: //All Grass
		{
			for(int l = 0; l < layers.size(); l++)
			{
				for(int y = 0; y < height; y++)
				{
					for(int x = 0; x < width; x++)
					{
						layers.get(l)[x][y] = 0;
						if(l!=0)//setting all the other layers to air by default.
						{
							layers.get(l)[x][y] = 999;
						}
					}
				}
			}
			break;
		}
		case 3: //Ocean
		{
			for(int l = 0; l < layers.size(); l++)
			{
				for(int y = 0; y < height; y++)
				{
					for(int x = 0; x < width; x++)
					{
						int rand = (int)(Math.random() * 400) + 1;
						if(rand<=388)
							layers.get(l)[x][y] = 12;
						if(rand>388 && rand<=398)
							layers.get(l)[x][y] = 13;
						if(rand>398)
							layers.get(l)[x][y] = 14;
						if(l!=0)//setting all the other layers to air by default.
						{
							layers.get(l)[x][y] = 999;
						}
					}
				}
			}
			break;
		}
		case 4: //Forest
		{
			for(int l = 0; l < layers.size(); l++)
			{
				for(int y = 0; y < height; y++)
				{
					for(int x = 0; x < width; x++)
					{
						int rand = (int)(Math.random() * 1000) + 1;
						if(rand<=900)
							layers.get(l)[x][y] = 0;
						if(rand>900 && rand <=910)
							layers.get(l)[x][y] = 1;
						if(rand>910&& rand <=920)
							layers.get(l)[x][y] = 2;
						if(rand>920&& rand <=930)
							layers.get(l)[x][y] = 3;
						if(x>1)
							if(layers.get(l)[x-1][y] == 3)
								layers.get(l)[x][y] = 4;
						if(l!=0)//setting all the other layers to air by default.
						{
							layers.get(l)[x][y] = 999;
						}
					}
				}
			}
			for(int l = 1; l < layers.size(); l++)
			{
				for(int y = 0; y < height; y++)
				{
					for(int x = 0; x < width; x++)
					{
						int rand = (int)(Math.random() * 1000) + 1;
						if((x>1 && y>1) && (x<width-7 && y<height-10))
							//checking each corner of the tree to place to see if it overlaps any other tree.
							if((rand>0 && rand <=5) && 
							   (layers.get(l)[x-1][y]<51 || layers.get(l)[x-1][y]>122)  && 
							   (layers.get(l)[x+7][y]<51 || layers.get(l)[x+7][y]>122) && 
							   (layers.get(l)[x][y+10]<51 || layers.get(l)[x][y+10]>122) &&
							   (layers.get(l)[x+7][y+10]<51 || layers.get(l)[x+7][y+10]>122))
								layers.get(l)[x][y] = 51;
						generateLargerTiles(x,y,l);
						if(l>1)//setting all the other layers to air by default.
						{
							layers.get(l)[x][y] = 999;
						}
					}
				}
			}
			break;
		}
		}
	}
	
	private void generateLargerTiles(int x, int y, int layer)
	{
		//making bush
		if(layers.get(layer)[x][y] == 15)
		{
			if((x+1<layers.get(layer)[x].length)&&(y+1<layers.get(layer).length))
			{
				layers.get(layer)[x][y] = 15;
				layers.get(layer)[x+1][y] = 16;
				layers.get(layer)[x][y+1] = 17;
				layers.get(layer)[x+1][y+1] = 18;
			}
		}
		
		//making rock
		if(layers.get(layer)[x][y] == 19)
		{
			if((x+1<layers.get(layer)[x].length)&&(y+1<layers.get(layer).length))
			{
				layers.get(layer)[x][y] = 19;
				layers.get(layer)[x+1][y] = 20;
				layers.get(layer)[x][y+1] = 21;
				layers.get(layer)[x+1][y+1] = 22;
			}
		}
		
		//making struc
		if(layers.get(layer)[x][y] == 23)
		{
			if((x+1<layers.get(layer)[x].length)&&(y+1<layers.get(layer).length))
			{
				layers.get(layer)[x][y] = 23;
				layers.get(layer)[x+1][y] = 24;
				layers.get(layer)[x][y+1] = 25;
				layers.get(layer)[x+1][y+1] = 26;
			}
		}
		
		//making tallGrass
		if(layers.get(layer)[x][y] == 27)
		{
			if((x+1<layers.get(layer)[x].length)&&(y+1<layers.get(layer).length))
			{
				layers.get(layer)[x][y] = 27;
				layers.get(layer)[x+1][y] = 28;
				layers.get(layer)[x][y+1] = 29;
				layers.get(layer)[x+1][y+1] = 30;
			}
		}
		
		//making sign
		if(layers.get(layer)[x][y] == 31)
		{
			if((x+1<layers.get(layer)[x].length)&&(y+1<layers.get(layer).length))
			{
				layers.get(layer)[x][y] = 31;
				layers.get(layer)[x+1][y] = 32;
				layers.get(layer)[x][y+1] = 33;
				layers.get(layer)[x+1][y+1] = 34;
			}
		}
		
		//making stump
		if(layers.get(layer)[x][y] == 35)
		{
			if((x+3<layers.get(layer)[x].length)&&(y+3<layers.get(layer).length))
			{
				layers.get(layer)[x][y] = 35;
				layers.get(layer)[x+1][y] = 36;
				layers.get(layer)[x+2][y] = 37;
				layers.get(layer)[x+3][y] = 38;
				layers.get(layer)[x][y+1] = 39;
				layers.get(layer)[x+1][y+1] = 40;
				layers.get(layer)[x+2][y+1] = 41;
				layers.get(layer)[x+3][y+1] = 42;
				layers.get(layer)[x][y+2] = 43;
				layers.get(layer)[x+1][y+2] = 44;
				layers.get(layer)[x+2][y+2] = 45;
				layers.get(layer)[x+3][y+2] = 46;
				layers.get(layer)[x][y+3] = 47;
				layers.get(layer)[x+1][y+3] = 48;
				layers.get(layer)[x+2][y+3] = 49;
				layers.get(layer)[x+3][y+3] = 50;
			}
		}
		
		//making tree
		if(layers.get(layer)[x][y] == 51)
		{
			//so it doesnt crash when going off board
			if((x>=1)&&(x+6<layers.get(layer)[x].length)&&(y+9<layers.get(layer).length))
			{
				layers.get(layer)[x][y] = 51;
				layers.get(layer)[x+1][y] = 52;
				layers.get(layer)[x+2][y] = 53;
				layers.get(layer)[x+3][y] = 54;
				layers.get(layer)[x+4][y] = 55;
				layers.get(layer)[x+5][y] = 56;
				layers.get(layer)[x-1][y+1] = 57;
				layers.get(layer)[x][y+1] = 58;
				layers.get(layer)[x+1][y+1] = 59;
				layers.get(layer)[x+2][y+1] = 60;
				layers.get(layer)[x+3][y+1] = 61;
				layers.get(layer)[x+4][y+1] = 62;
				layers.get(layer)[x+5][y+1] = 63;
				layers.get(layer)[x+6][y+1] = 64;
				layers.get(layer)[x-1][y+2] = 65;
				layers.get(layer)[x][y+2] = 66;
				layers.get(layer)[x+1][y+2] = 67;
				layers.get(layer)[x+2][y+2] = 68;
				layers.get(layer)[x+3][y+2] = 69;
				layers.get(layer)[x+4][y+2] = 70;
				layers.get(layer)[x+5][y+2] = 71;
				layers.get(layer)[x+6][y+2] = 72;
				layers.get(layer)[x-1][y+3] = 73;
				layers.get(layer)[x][y+3] = 74;
				layers.get(layer)[x+1][y+3] = 75;
				layers.get(layer)[x+2][y+3] = 76;
				layers.get(layer)[x+3][y+3] = 77;
				layers.get(layer)[x+4][y+3] = 78;
				layers.get(layer)[x+5][y+3] = 79;
				layers.get(layer)[x+6][y+3] = 80;
				layers.get(layer)[x-1][y+4] = 81;
				layers.get(layer)[x][y+4] = 82;
				layers.get(layer)[x+1][y+4] = 83;
				layers.get(layer)[x+2][y+4] = 84;
				layers.get(layer)[x+3][y+4] = 85;
				layers.get(layer)[x+4][y+4] = 86;
				layers.get(layer)[x+5][y+4] = 87;
				layers.get(layer)[x+6][y+4] = 88;
				layers.get(layer)[x-1][y+5] = 89;
				layers.get(layer)[x][y+5] = 90;
				layers.get(layer)[x+1][y+5] = 91;
				layers.get(layer)[x+2][y+5] = 92;
				layers.get(layer)[x+3][y+5] = 93;
				layers.get(layer)[x+4][y+5] = 94;
				layers.get(layer)[x+5][y+5] = 95;
				layers.get(layer)[x+6][y+5] = 96;
				layers.get(layer)[x-1][y+6] = 97;
				layers.get(layer)[x][y+6] = 98;
				layers.get(layer)[x+1][y+6] = 99;
				layers.get(layer)[x+2][y+6] = 100;
				layers.get(layer)[x+3][y+6] = 101;
				layers.get(layer)[x+4][y+6] = 102;
				layers.get(layer)[x+5][y+6] = 103;
				layers.get(layer)[x+6][y+6] = 104;
				layers.get(layer)[x-1][y+7] = 105;
				layers.get(layer)[x][y+7] = 106;
				layers.get(layer)[x+1][y+7] = 107;
				layers.get(layer)[x+2][y+7] = 108;
				layers.get(layer)[x+3][y+7] = 109;
				layers.get(layer)[x+4][y+7] = 110;
				layers.get(layer)[x+5][y+7] = 111;
				layers.get(layer)[x+6][y+7] = 112;
				layers.get(layer)[x][y+8] = 113;
				layers.get(layer)[x+1][y+8] = 114;
				layers.get(layer)[x+2][y+8] = 115;
				layers.get(layer)[x+3][y+8] = 116;
				layers.get(layer)[x+4][y+8] = 117;
				layers.get(layer)[x+5][y+8] = 118;
				layers.get(layer)[x+1][y+9] = 119;
				layers.get(layer)[x+2][y+9] = 120;
				layers.get(layer)[x+3][y+9] = 121;
				layers.get(layer)[x+4][y+9] = 122;
			}
		}
		
		//Connecting Fences (all directions)
		if(layers.get(layer)[x][y] == 125)
		{
			//just normal fence + fence bottom
			if(y+1<layers.get(layer).length)
			{
				if(layers.get(layer)[x][y+1] != 125)
					layers.get(layer)[x][y+1] = 126;
			}
			//connection on the left (if there's a tile on the left)
			if((x>1) && (y+1<layers.get(layer).length))
			{
				if(layers.get(layer)[x-2][y] == 125)
				{
					layers.get(layer)[x-2][y] = 125;
					layers.get(layer)[x-2][y+1] = 126;
					layers.get(layer)[x-1][y] = 127;
					layers.get(layer)[x-1][y+1] = 128;
					layers.get(layer)[x][y+1] = 126;
					//checking if there's also a connection below
					if(y+2<layers.get(layer).length)
					{
						if(layers.get(layer)[x-2][y+2] == 125)
						{
							layers.get(layer)[x-2][y+1] = 129;
						}
					}
				}
			}
			//connection on the right (if there's a tile on the right)
			if((x+2<layers.get(layer)[x].length) && (y+1<layers.get(layer).length))
			{
				if(layers.get(layer)[x+2][y] == 125)
				{
					layers.get(layer)[x+2][y] = 125;
					layers.get(layer)[x+2][y+1] = 126;
					layers.get(layer)[x+1][y] = 127;
					layers.get(layer)[x+1][y+1] = 128;
					layers.get(layer)[x][y+1] = 126;
					//checking if there's also a connection below
					if(y+2<layers.get(layer).length)
					{
						if(layers.get(layer)[x+2][y+2] == 125)
						{
							layers.get(layer)[x+2][y+1] = 129;
						}
					}
				}
			}
			//connection down (if there's a tile below)
			if(y+2<layers.get(layer).length)
			{
				if(layers.get(layer)[x][y+2] == 125)
				{
					layers.get(layer)[x][y+2] = 125;
					layers.get(layer)[x][y+1] = 129;
				}
			}
			//connection up (if there's a tile above)
			if(y>1)
			{
				if(layers.get(layer)[x][y-2] == 125)
				{
					layers.get(layer)[x][y-2] = 125;
					layers.get(layer)[x][y-1] = 129;
				}
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
		
		for(int layer = 0; layer < layers.size(); layer++)//rendering in all the layers.
		{
			for(int y = yStart; y < yEnd; y++)
			{
				for(int x = xStart; x < xEnd; x++)
				{
					if(!getTile(x,y,layer).isAir())//dont render in air tiles, saves a ton of fps.
						getTile(x, y, layer).render(g, (int)(x * Tile.TILEWIDTH - handler.getMapMakerCamera().getxOffset()), (int)(y * Tile.TILEHEIGHT - handler.getMapMakerCamera().getyOffset()));
					if(layer==highlightLayer)
					{
						if(highlight && !getTile(x,y,layer).isAir()) //if its highlighting and the tile isnt air.
						{
							g.setColor(highlightColor);
							g.fillRect((int)(x * Tile.TILEWIDTH - handler.getMapMakerCamera().getxOffset()), (int)(y * Tile.TILEHEIGHT - handler.getMapMakerCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT);
						}
					}
				}
			}
		}
	}
}
