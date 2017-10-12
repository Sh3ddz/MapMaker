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
		layers.get(layer)[x][y] = id;
		
		//checking to generate a larger tile. (only need to check the ones which can generate larger tiles.)
		if(id == 15 || id == 19 || id == 23 || id == 27 || id == 31 || id == 35 || id == 51 || id == 125 || id == 149)
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
		if(layers.get(layer)[x][y] == Tile.bush1Tile.getId())
		{
			if((x+1<layers.get(layer)[x].length)&&(y+1<layers.get(layer).length))
			{
				layers.get(layer)[x][y] = Tile.bush1Tile.getId();
				layers.get(layer)[x+1][y] = Tile.bush2Tile.getId();
				layers.get(layer)[x][y+1] = Tile.bush3Tile.getId();
				layers.get(layer)[x+1][y+1] = Tile.bush4Tile.getId();
			}
		}
		
		//making rock
		if(layers.get(layer)[x][y] == Tile.rock1Tile.getId())
		{
			if((x+1<layers.get(layer)[x].length)&&(y+1<layers.get(layer).length))
			{
				layers.get(layer)[x][y] = Tile.rock1Tile.getId();
				layers.get(layer)[x+1][y] = Tile.rock2Tile.getId();
				layers.get(layer)[x][y+1] = Tile.rock3Tile.getId();
				layers.get(layer)[x+1][y+1] = Tile.rock4Tile.getId();
			}
		}
		
		//making struc
		if(layers.get(layer)[x][y] == Tile.struc1Tile.getId())
		{
			if((x+1<layers.get(layer)[x].length)&&(y+1<layers.get(layer).length))
			{
				layers.get(layer)[x][y] = Tile.struc1Tile.getId();
				layers.get(layer)[x+1][y] = Tile.struc2Tile.getId();
				layers.get(layer)[x][y+1] = Tile.struc3Tile.getId();
				layers.get(layer)[x+1][y+1] = Tile.struc4Tile.getId();
			}
		}
		
		//making tallGrass
		if(layers.get(layer)[x][y] == Tile.tallGrass1Tile.getId())
		{
			if((x+1<layers.get(layer)[x].length)&&(y+1<layers.get(layer).length))
			{
				layers.get(layer)[x][y] = Tile.tallGrass1Tile.getId();
				layers.get(layer)[x+1][y] = Tile.tallGrass2Tile.getId();
				layers.get(layer)[x][y+1] = Tile.tallGrass3Tile.getId();
				layers.get(layer)[x+1][y+1] = Tile.tallGrass4Tile.getId();
			}
		}
		
		//making sign
		if(layers.get(layer)[x][y] == Tile.sign1Tile.getId())
		{
			if((x+1<layers.get(layer)[x].length)&&(y+1<layers.get(layer).length))
			{
				layers.get(layer)[x][y] = Tile.sign1Tile.getId();
				layers.get(layer)[x+1][y] = Tile.sign2Tile.getId();
				layers.get(layer)[x][y+1] = Tile.sign3Tile.getId();
				layers.get(layer)[x+1][y+1] = Tile.sign4Tile.getId();
			}
		}
		
		//making stump
		if(layers.get(layer)[x][y] == Tile.stump1Tile.getId())
		{
			if((x+3<layers.get(layer)[x].length)&&(y+3<layers.get(layer).length))
			{
				layers.get(layer)[x][y] = Tile.stump1Tile.getId();
				layers.get(layer)[x+1][y] = Tile.stump2Tile.getId();
				layers.get(layer)[x+2][y] = Tile.stump3Tile.getId();
				layers.get(layer)[x+3][y] = Tile.stump4Tile.getId();
				layers.get(layer)[x][y+1] = Tile.stump5Tile.getId();
				layers.get(layer)[x+1][y+1] = Tile.stump6Tile.getId();
				layers.get(layer)[x+2][y+1] = Tile.stump7Tile.getId();
				layers.get(layer)[x+3][y+1] = Tile.stump8Tile.getId();
				layers.get(layer)[x][y+2] = Tile.stump9Tile.getId();
				layers.get(layer)[x+1][y+2] = Tile.stump10Tile.getId();
				layers.get(layer)[x+2][y+2] = Tile.stump11Tile.getId();
				layers.get(layer)[x+3][y+2] = Tile.stump12Tile.getId();
				layers.get(layer)[x][y+3] = Tile.stump13Tile.getId();
				layers.get(layer)[x+1][y+3] = Tile.stump14Tile.getId();
				layers.get(layer)[x+2][y+3] = Tile.stump15Tile.getId();
				layers.get(layer)[x+3][y+3] = Tile.stump16Tile.getId();
			}
		}
		//making stumpTree
		if(layers.get(layer)[x][y] == Tile.stumpTree1Tile.getId())
		{
			if((x+3<layers.get(layer)[x].length)&&(y+3<layers.get(layer).length))
			{
				layers.get(layer)[x][y] = Tile.stumpTree1Tile.getId();
				layers.get(layer)[x+1][y] = Tile.stumpTree2Tile.getId();
				layers.get(layer)[x+2][y] = Tile.stumpTree3Tile.getId();
				layers.get(layer)[x+3][y] = Tile.stumpTree4Tile.getId();
				layers.get(layer)[x][y+1] = Tile.stumpTree5Tile.getId();
				layers.get(layer)[x+1][y+1] = Tile.stumpTree6Tile.getId();
				layers.get(layer)[x+2][y+1] = Tile.stumpTree7Tile.getId();
				layers.get(layer)[x+3][y+1] = Tile.stumpTree8Tile.getId();
				layers.get(layer)[x][y+2] = Tile.stumpTree9Tile.getId();
				layers.get(layer)[x+1][y+2] = Tile.stumpTree10Tile.getId();
				layers.get(layer)[x+2][y+2] = Tile.stumpTree11Tile.getId();
				layers.get(layer)[x+3][y+2] = Tile.stumpTree12Tile.getId();
				layers.get(layer)[x][y+3] = Tile.stumpTree13Tile.getId();
				layers.get(layer)[x+1][y+3] = Tile.stumpTree14Tile.getId();
				layers.get(layer)[x+2][y+3] = Tile.stumpTree15Tile.getId();
				layers.get(layer)[x+3][y+3] = Tile.stumpTree16Tile.getId();
			}
		}
		
		//making tree
		if(layers.get(layer)[x][y] == Tile.tree1Tile.getId())
		{
			//so it doesnt crash when going off board
			if((x>=1)&&(x+6<layers.get(layer)[x].length)&&(y+9<layers.get(layer).length))
			{
				layers.get(layer)[x][y] = Tile.tree1Tile.getId();
				layers.get(layer)[x+1][y] = Tile.tree2Tile.getId();
				layers.get(layer)[x+2][y] = Tile.tree3Tile.getId();
				layers.get(layer)[x+3][y] = Tile.tree4Tile.getId();
				layers.get(layer)[x+4][y] = Tile.tree5Tile.getId();
				layers.get(layer)[x+5][y] = Tile.tree6Tile.getId();
				layers.get(layer)[x-1][y+1] = Tile.tree7Tile.getId();
				layers.get(layer)[x][y+1] = Tile.tree8Tile.getId();
				layers.get(layer)[x+1][y+1] = Tile.tree9Tile.getId();
				layers.get(layer)[x+2][y+1] = Tile.tree10Tile.getId();
				layers.get(layer)[x+3][y+1] = Tile.tree11Tile.getId();
				layers.get(layer)[x+4][y+1] = Tile.tree12Tile.getId();
				layers.get(layer)[x+5][y+1] = Tile.tree13Tile.getId();
				layers.get(layer)[x+6][y+1] = Tile.tree14Tile.getId();
				layers.get(layer)[x-1][y+2] = Tile.tree15Tile.getId();
				layers.get(layer)[x][y+2] = Tile.tree16Tile.getId();
				layers.get(layer)[x+1][y+2] = Tile.tree17Tile.getId();
				layers.get(layer)[x+2][y+2] = Tile.tree18Tile.getId();
				layers.get(layer)[x+3][y+2] = Tile.tree19Tile.getId();
				layers.get(layer)[x+4][y+2] = Tile.tree20Tile.getId();
				layers.get(layer)[x+5][y+2] = Tile.tree21Tile.getId();
				layers.get(layer)[x+6][y+2] = Tile.tree22Tile.getId();
				layers.get(layer)[x-1][y+3] = Tile.tree23Tile.getId();
				layers.get(layer)[x][y+3] = Tile.tree24Tile.getId();
				layers.get(layer)[x+1][y+3] = Tile.tree25Tile.getId();
				layers.get(layer)[x+2][y+3] = Tile.tree26Tile.getId();
				layers.get(layer)[x+3][y+3] = Tile.tree27Tile.getId();
				layers.get(layer)[x+4][y+3] = Tile.tree28Tile.getId();
				layers.get(layer)[x+5][y+3] = Tile.tree29Tile.getId();
				layers.get(layer)[x+6][y+3] = Tile.tree30Tile.getId();
				layers.get(layer)[x-1][y+4] = Tile.tree31Tile.getId();
				layers.get(layer)[x][y+4] = Tile.tree32Tile.getId();
				layers.get(layer)[x+1][y+4] = Tile.tree33Tile.getId();
				layers.get(layer)[x+2][y+4] = Tile.tree34Tile.getId();
				layers.get(layer)[x+3][y+4] = Tile.tree35Tile.getId();
				layers.get(layer)[x+4][y+4] = Tile.tree36Tile.getId();
				layers.get(layer)[x+5][y+4] = Tile.tree37Tile.getId();
				layers.get(layer)[x+6][y+4] = Tile.tree38Tile.getId();
				layers.get(layer)[x-1][y+5] = Tile.tree39Tile.getId();
				layers.get(layer)[x][y+5] = Tile.tree40Tile.getId();
				layers.get(layer)[x+1][y+5] = Tile.tree41Tile.getId();
				layers.get(layer)[x+2][y+5] = Tile.tree42Tile.getId();
				layers.get(layer)[x+3][y+5] = Tile.tree43Tile.getId();
				layers.get(layer)[x+4][y+5] = Tile.tree44Tile.getId();
				layers.get(layer)[x+5][y+5] = Tile.tree45Tile.getId();
				layers.get(layer)[x+6][y+5] = Tile.tree46Tile.getId();
				layers.get(layer)[x-1][y+6] = Tile.tree47Tile.getId();
				layers.get(layer)[x][y+6] = Tile.tree48Tile.getId();
				layers.get(layer)[x+1][y+6] = Tile.tree49Tile.getId();
				layers.get(layer)[x+2][y+6] = Tile.tree50Tile.getId();
				layers.get(layer)[x+3][y+6] = Tile.tree51Tile.getId();
				layers.get(layer)[x+4][y+6] = Tile.tree52Tile.getId();
				layers.get(layer)[x+5][y+6] = Tile.tree53Tile.getId();
				layers.get(layer)[x+6][y+6] = Tile.tree54Tile.getId();
				layers.get(layer)[x-1][y+7] = Tile.tree55Tile.getId();
				layers.get(layer)[x][y+7] = Tile.tree56Tile.getId();
				layers.get(layer)[x+1][y+7] = Tile.tree57Tile.getId();
				layers.get(layer)[x+2][y+7] = Tile.tree58Tile.getId();
				layers.get(layer)[x+3][y+7] = Tile.tree59Tile.getId();
				layers.get(layer)[x+4][y+7] = Tile.tree60Tile.getId();
				layers.get(layer)[x+5][y+7] = Tile.tree61Tile.getId();
				layers.get(layer)[x+6][y+7] = Tile.tree62Tile.getId();
				layers.get(layer)[x][y+8] = Tile.tree63Tile.getId();
				layers.get(layer)[x+1][y+8] = Tile.tree64Tile.getId();
				layers.get(layer)[x+2][y+8] = Tile.tree65Tile.getId();
				layers.get(layer)[x+3][y+8] = Tile.tree66Tile.getId();
				layers.get(layer)[x+4][y+8] = Tile.tree67Tile.getId();
				layers.get(layer)[x+5][y+8] = Tile.tree68Tile.getId();
				layers.get(layer)[x+1][y+9] = Tile.tree69Tile.getId();
				layers.get(layer)[x+2][y+9] = Tile.tree70Tile.getId();
				layers.get(layer)[x+3][y+9] = Tile.tree71Tile.getId();
				layers.get(layer)[x+4][y+9] = Tile.tree72Tile.getId();
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
