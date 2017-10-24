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

import gfx.Assets;
import input.Change;
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
	private Color highlightColor = new Color(255,255,0,100);
	
	private boolean gridView = true;

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
		Change c = new Change(handler.getWorld(), x, y, layer, getTile(x,y,layer).getId(), id);
		handler.getSelector().addChange(c);

		layers.get(layer)[x][y] = id;
		//checking to generate a larger tile. (only need to check the ones which can generate larger tiles.)
		if(id == 15 || id == 19 || id == 23 || id == 27 || id == 31 || id == 35 || id == 51 || id == 125 || id == 149)
		{
			generateLargerTiles(x, y, layer);
		}
	}
	
	//used for undo-ing and redo-ing since we dont want to update the undo's / redo's as changes, it will clog it up.
	public void setTileNoChange(int x, int y, int layer, int id)
	{
		if(x < 0 || y < 0 || x >= width || y >= height)
			return;

		//gets specific layer and sets the tile.
		layers.get(layer)[x][y] = id;
		//dont generate larger tiles, since it's already in the change log generating it again here will double the changes and cause errors.
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
	
	public boolean getGridView()
	{
		return this.gridView;
	}
	public void setGridView(boolean g)
	{
		this.gridView = g;
	}
	
	private void loadWorld(String path)
	{
		handler.getMapMaker().displayLoadingScreen(); //displaying the loading screen
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		
		handler.getMapMakerCamera().setPosition(spawnX, spawnY);

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
					if(token > tokens.length)
					{
						layers.get(l)[x][y] = 999; //setting it to air if the file isn't formatted right.
						continue;
					}
					if(layers.get(l)[x][y]  == 0 && token < tokens.length)//checking if token is less than the length of tokens so it doesnt throw an OOB exception and crash. (mainly for bad world files).
						layers.get(l)[x][y] = Utils.parseInt(tokens[token]);
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
			writer.write(""+this.spawnX+" "+this.spawnY);
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
			System.out.println("Loaded "+path+" as a world.");
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
		handler.getMapMaker().displayLoadingScreen(); //displaying the loading screen
		this.width = width;
		this.height = height;
		this.spawnX = 0;
		this.spawnY = 0;
		handler.getMapMakerCamera().setPosition(spawnX, spawnY);
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
		if(layers.get(layer)[x][y] == Tile.bush1Tile.getId())
		{
			if((x+1<layers.get(layer)[x].length)&&(y+1<layers.get(layer).length))
			{
				setTile(x+1, y, layer, Tile.bush2Tile.getId());
				setTile(x, y+1, layer, Tile.bush3Tile.getId());
				setTile(x+1, y+1, layer, Tile.bush4Tile.getId());
			}
		}
		
		//making rock
		if(layers.get(layer)[x][y] == Tile.rock1Tile.getId())
		{
			if((x+1<layers.get(layer)[x].length)&&(y+1<layers.get(layer).length))
			{
				setTile(x+1, y, layer, Tile.rock2Tile.getId());
				setTile(x, y+1, layer, Tile.rock3Tile.getId());
				setTile(x+1, y+1, layer, Tile.rock4Tile.getId());
			}
		}
		
		//making struc
		if(layers.get(layer)[x][y] == Tile.struc1Tile.getId())
		{
			if((x+1<layers.get(layer)[x].length)&&(y+1<layers.get(layer).length))
			{
				setTile(x+1, y, layer, Tile.struc2Tile.getId());
				setTile(x, y+1, layer, Tile.struc3Tile.getId());
				setTile(x+1, y+1, layer, Tile.struc4Tile.getId());
			}
		}
		
		//making tallGrass
		if(layers.get(layer)[x][y] == Tile.tallGrass1Tile.getId())
		{
			if((x+1<layers.get(layer)[x].length)&&(y+1<layers.get(layer).length))
			{
				setTile(x+1, y, layer, Tile.tallGrass2Tile.getId());
				setTile(x, y+1, layer, Tile.tallGrass3Tile.getId());
				setTile(x+1, y+1, layer, Tile.tallGrass4Tile.getId());
			}
		}
		
		//making sign
		if(layers.get(layer)[x][y] == Tile.sign1Tile.getId())
		{
			if((x+1<layers.get(layer)[x].length)&&(y+1<layers.get(layer).length))
			{
				setTile(x+1, y, layer, Tile.sign2Tile.getId());
				setTile(x, y+1, layer, Tile.sign3Tile.getId());
				setTile(x+1, y+1, layer, Tile.sign4Tile.getId());
			}
		}
		
		//making stump
		if(layers.get(layer)[x][y] == Tile.stump1Tile.getId())
		{
			if((x+3<layers.get(layer)[x].length)&&(y+3<layers.get(layer).length))
			{
				setTile(x+1, y, layer, Tile.stump2Tile.getId());
				setTile(x+2, y, layer, Tile.stump3Tile.getId());
				setTile(x+3, y, layer, Tile.stump4Tile.getId());
				setTile(x, y+1, layer, Tile.stump5Tile.getId());
				setTile(x+1, y+1, layer, Tile.stump6Tile.getId());
				setTile(x+2, y+1, layer, Tile.stump7Tile.getId());
				setTile(x+3, y+1, layer, Tile.stump8Tile.getId());
				setTile(x, y+2, layer, Tile.stump9Tile.getId());
				setTile(x+1, y+2, layer, Tile.stump10Tile.getId());
				setTile(x+2, y+2, layer, Tile.stump11Tile.getId());
				setTile(x+3, y+2, layer, Tile.stump12Tile.getId());
				setTile(x, y+3, layer, Tile.stump13Tile.getId());
				setTile(x+1, y+3, layer, Tile.stump14Tile.getId());
				setTile(x+2, y+3, layer, Tile.stump15Tile.getId());
				setTile(x+3, y+3, layer, Tile.stump16Tile.getId());
			}
		}
		//making stumpTree
		if(layers.get(layer)[x][y] == Tile.stumpTree1Tile.getId())
		{
			if((x+3<layers.get(layer)[x].length)&&(y+3<layers.get(layer).length))
			{
				setTile(x+1, y, layer, Tile.stumpTree2Tile.getId());
				setTile(x+2, y, layer, Tile.stumpTree3Tile.getId());
				setTile(x+3, y, layer, Tile.stumpTree4Tile.getId());
				setTile(x, y+1, layer, Tile.stumpTree5Tile.getId());
				setTile(x+1, y+1, layer, Tile.stumpTree6Tile.getId());
				setTile(x+2, y+1, layer, Tile.stumpTree7Tile.getId());
				setTile(x+3, y+1, layer, Tile.stumpTree8Tile.getId());
				setTile(x, y+2, layer, Tile.stumpTree9Tile.getId());
				setTile(x+1, y+2, layer, Tile.stumpTree10Tile.getId());
				setTile(x+2, y+2, layer, Tile.stumpTree11Tile.getId());
				setTile(x+3, y+2, layer, Tile.stumpTree12Tile.getId());
				setTile(x, y+3, layer, Tile.stumpTree13Tile.getId());
				setTile(x+1, y+3, layer, Tile.stumpTree14Tile.getId());
				setTile(x+2, y+3, layer, Tile.stumpTree15Tile.getId());
				setTile(x+3, y+3, layer, Tile.stumpTree16Tile.getId());
			}
		}
		
		//making tree
		if(layers.get(layer)[x][y] == Tile.tree1Tile.getId())
		{
			//so it doesnt crash when going off board
			if((x>=1)&&(x+6<layers.get(layer)[x].length)&&(y+9<layers.get(layer).length))
			{
				layers.get(layer)[x][y] = Tile.tree1Tile.getId();
				
				setTile(x+1, y, layer, Tile.tree2Tile.getId());
				setTile(x+2, y, layer, Tile.tree3Tile.getId());
				setTile(x+3, y, layer, Tile.tree4Tile.getId());
				setTile(x+4, y, layer, Tile.tree5Tile.getId());
				setTile(x+5, y, layer, Tile.tree6Tile.getId());
				setTile(x-1, y+1, layer, Tile.tree7Tile.getId());
				setTile(x, y+1, layer, Tile.tree8Tile.getId());
				setTile(x+1, y+1, layer, Tile.tree9Tile.getId());
				setTile(x+2, y+1, layer, Tile.tree10Tile.getId());
				setTile(x+3, y+1, layer, Tile.tree11Tile.getId());
				setTile(x+4, y+1, layer, Tile.tree12Tile.getId());
				setTile(x+5, y+1, layer, Tile.tree13Tile.getId());
				setTile(x+6, y+1, layer, Tile.tree14Tile.getId());
				setTile(x-1, y+2, layer, Tile.tree15Tile.getId());
				setTile(x, y+2, layer, Tile.tree16Tile.getId());
				setTile(x+1, y+2, layer, Tile.tree17Tile.getId());
				setTile(x+2, y+2, layer, Tile.tree18Tile.getId());
				setTile(x+3, y+2, layer, Tile.tree19Tile.getId());
				setTile(x+4, y+2, layer, Tile.tree20Tile.getId());
				setTile(x+5, y+2, layer, Tile.tree21Tile.getId());
				setTile(x+6, y+2, layer, Tile.tree22Tile.getId());
				setTile(x-1, y+3, layer, Tile.tree23Tile.getId());
				setTile(x, y+3, layer, Tile.tree24Tile.getId());
				setTile(x+1, y+3, layer, Tile.tree25Tile.getId());
				setTile(x+2, y+3, layer, Tile.tree26Tile.getId());
				setTile(x+3, y+3, layer, Tile.tree27Tile.getId());
				setTile(x+4, y+3, layer, Tile.tree28Tile.getId());
				setTile(x+5, y+3, layer, Tile.tree29Tile.getId());
				setTile(x+6, y+3, layer, Tile.tree30Tile.getId());
				setTile(x-1, y+4, layer, Tile.tree31Tile.getId());
				setTile(x, y+4, layer, Tile.tree32Tile.getId());
				setTile(x+1, y+4, layer, Tile.tree33Tile.getId());
				setTile(x+2, y+4, layer, Tile.tree34Tile.getId());
				setTile(x+3, y+4, layer, Tile.tree35Tile.getId());
				setTile(x+4, y+4, layer, Tile.tree36Tile.getId());
				setTile(x+5, y+4, layer, Tile.tree37Tile.getId());
				setTile(x+6, y+4, layer, Tile.tree38Tile.getId());
				setTile(x-1, y+5, layer, Tile.tree39Tile.getId());
				setTile(x, y+5, layer, Tile.tree40Tile.getId());
				setTile(x+1, y+5, layer, Tile.tree41Tile.getId());
				setTile(x+2, y+5, layer, Tile.tree42Tile.getId());
				setTile(x+3, y+5, layer, Tile.tree43Tile.getId());
				setTile(x+4, y+5, layer, Tile.tree44Tile.getId());
				setTile(x+5, y+5, layer, Tile.tree45Tile.getId());
				setTile(x+6, y+5, layer, Tile.tree46Tile.getId());
				setTile(x-1, y+6, layer, Tile.tree47Tile.getId());
				setTile(x, y+6, layer, Tile.tree48Tile.getId());
				setTile(x+1, y+6, layer, Tile.tree49Tile.getId());
				setTile(x+2, y+6, layer, Tile.tree50Tile.getId());
				setTile(x+3, y+6, layer, Tile.tree51Tile.getId());
				setTile(x+4, y+6, layer, Tile.tree52Tile.getId());
				setTile(x+5, y+6, layer, Tile.tree53Tile.getId());
				setTile(x+6, y+6, layer, Tile.tree54Tile.getId());
				setTile(x-1, y+7, layer, Tile.tree55Tile.getId());
				setTile(x, y+7, layer, Tile.tree56Tile.getId());
				setTile(x+1, y+7, layer, Tile.tree57Tile.getId());
				setTile(x+2, y+7, layer, Tile.tree58Tile.getId());
				setTile(x+3, y+7, layer, Tile.tree59Tile.getId());
				setTile(x+4, y+7, layer, Tile.tree60Tile.getId());
				setTile(x+5, y+7, layer, Tile.tree61Tile.getId());
				setTile(x+6, y+7, layer, Tile.tree62Tile.getId());
				setTile(x, y+8, layer, Tile.tree63Tile.getId());
				setTile(x+1, y+8, layer, Tile.tree64Tile.getId());
				setTile(x+2, y+8, layer, Tile.tree65Tile.getId());
				setTile(x+3, y+8, layer, Tile.tree66Tile.getId());
				setTile(x+4, y+8, layer, Tile.tree67Tile.getId());
				setTile(x+5, y+8, layer, Tile.tree68Tile.getId());
				setTile(x+1, y+9, layer, Tile.tree69Tile.getId());
				setTile(x+2, y+9, layer, Tile.tree70Tile.getId());
				setTile(x+3, y+9, layer, Tile.tree71Tile.getId());
				setTile(x+4, y+9, layer, Tile.tree72Tile.getId());
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
		int changeStart = 0;
		int changeEnd = 0;
		switch(handler.getMapMakerCamera().getZoomLevel())
		{
		case 1: //zoomed out
			changeStart = 0;
			changeEnd = -4;
			break;
		case 2:
			changeStart = 0;
			changeEnd = -2;
			break;
		case 3: //middle zoom
			changeStart = 0;
			changeEnd = 0;
			break;
		case 4:
			changeStart = 0;
			changeEnd = 1;
			break;
		case 5: //zoomed in
			changeStart = 0;
			changeEnd = 1;
			break;
		}
			
		int xStart = (int) Math.max(0, handler.getMapMakerCamera().getxOffset() / Tile.TILEWIDTH +changeStart);
		int xEnd = (int) Math.min(width, (handler.getMapMakerCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH +changeEnd);
		int yStart = (int) Math.max(0, handler.getMapMakerCamera().getyOffset() / Tile.TILEHEIGHT +changeStart);
		int yEnd = (int) Math.min(height, (handler.getMapMakerCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT +changeEnd);
		
		for(int layer = 0; layer < layers.size(); layer++)//rendering in all the layers.
		{
			for(int y = yStart; y < yEnd; y++)
			{
				for(int x = xStart; x < xEnd; x++)
				{				
					//rendering tiles.
					if(!getTile(x,y,layer).isAir())//dont render in air tiles, saves a ton of fps.
						getTile(x, y, layer).render(g, (int)(x * Tile.TILEWIDTH - handler.getMapMakerCamera().getxOffset()), (int)(y * Tile.TILEHEIGHT - handler.getMapMakerCamera().getyOffset()));
					
					//rendering highlight
					if(layer==highlightLayer && highlight)
					{
						if(!getTile(x,y,layer).isAir()) //if its highlighting and the tile isnt air.
						{
							g.setColor(highlightColor);
							g.fillRect((int)(x * Tile.TILEWIDTH - handler.getMapMakerCamera().getxOffset())+1, (int)(y * Tile.TILEHEIGHT - handler.getMapMakerCamera().getyOffset())+1, Tile.TILEWIDTH-1, Tile.TILEHEIGHT-1);
						}
					}
				}
			}
		}
		//rendering in the grid.
		if(gridView)
		{
			for(int y = yStart; y < yEnd; y++)
			{
				for(int x = xStart; x < xEnd; x++)
				{
					g.drawImage(Assets.grid, (int)(x * Tile.TILEWIDTH - handler.getMapMakerCamera().getxOffset()), (int)(y * Tile.TILEHEIGHT - handler.getMapMakerCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
				}
			}
		}
	}
}
