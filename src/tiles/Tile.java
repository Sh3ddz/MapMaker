package tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gfx.Assets;

public class Tile 
{
	//STATIC STUFF HERE
	
	//1x1 Tiles
	public static Tile[] tiles = new Tile[1000];
	public static Tile air = new AirTile(999);
	public static Tile grassTile = new GrassTile(0);
	public static Tile grass2Tile = new Tile(Assets.grass2, 1);
	public static Tile flower = new Tile(Assets.flower, 2);
	public static Tile grass3Tile = new Tile(Assets.grass3, 3);
	public static Tile grass4Tile = new Tile(Assets.grass4, 4);
	public static Tile entrancePadLTile = new Tile(Assets.entrancePadL, 5);
	public static Tile entrancePadRTile = new Tile(Assets.entrancePadR, 6);
	public static Tile pathTile = new Tile(Assets.path, 7);
	public static Tile path2Tile = new Tile(Assets.path2, 8);
	public static Tile path3Tile = new Tile(Assets.path3, 9);
	public static Tile path4Tile = new Tile(Assets.path4, 134);
	public static Tile dirtTile = new Tile(Assets.dirt, 10);
	public static Tile dirt2Tile = new Tile(Assets.dirt2, 11);
	public static Tile waterTile = new WaterTile(Assets.water, 12);
	public static Tile water2Tile = new WaterTile(Assets.water2, 13);
	public static Tile waterRockTile = new SolidTile(Assets.waterRock, 14);
	public static Tile snowTile = new Tile(Assets.snow, 123);
	public static Tile snow2Tile = new Tile(Assets.snow2, 124);
	public static Tile fencetopTile = new SolidTile(Assets.fencetop, 125);
	public static Tile fencebottomTile = new SolidTile(Assets.fencebottom, 126);
	public static Tile fenceconnecttopTile = new SolidTile(Assets.fenceconnecttop, 127);
	public static Tile fenceconnectshadowTile = new Tile(Assets.fenceconnectshadow, 128);
	public static Tile fenceconnectdownTile = new SolidTile(Assets.fenceconnectdown, 129);

	//2x2 Tiles
	public static Tile bush1Tile = new SolidTile(Assets.bush1, 15);
	public static Tile bush2Tile = new SolidTile(Assets.bush2, 16);
	public static Tile bush3Tile = new SolidTile(Assets.bush3, 17);
	public static Tile bush4Tile = new SolidTile(Assets.bush4, 18);
	public static Tile rock1Tile = new SolidTile(Assets.rock1, 19);
	public static Tile rock2Tile = new SolidTile(Assets.rock2, 20);
	public static Tile rock3Tile = new SolidTile(Assets.rock3, 21);
	public static Tile rock4Tile = new SolidTile(Assets.rock4, 22);
	public static Tile struc1Tile = new SolidTile(Assets.struc1, 23);
	public static Tile struc2Tile = new SolidTile(Assets.struc2, 24);
	public static Tile struc3Tile = new SolidTile(Assets.struc3, 25);
	public static Tile struc4Tile = new SolidTile(Assets.struc4, 26);
	public static Tile tallGrass1Tile = new Tile(Assets.tallgrass1, 27);
	public static Tile tallGrass2Tile = new Tile(Assets.tallgrass2, 28);
	public static Tile tallGrass3Tile = new Tile(Assets.tallgrass3, 29);
	public static Tile tallGrass4Tile = new Tile(Assets.tallgrass4, 30);
	public static Tile sign1Tile = new SolidTile(Assets.sign1, 31);
	public static Tile sign2Tile = new SolidTile(Assets.sign2, 32);
	public static Tile sign3Tile = new SolidTile(Assets.sign3, 33);
	public static Tile sign4Tile = new SolidTile(Assets.sign4, 34);
	public static Tile tallgrassend1Tile = new Tile(Assets.tallgrassend1, 130);
	public static Tile tallgrassend2Tile = new Tile(Assets.tallgrassend2, 131);
	public static Tile tallgrassend3Tile = new Tile(Assets.tallgrassend3, 132);
	public static Tile tallgrassend4Tile = new Tile(Assets.tallgrassend4, 133);
	public static Tile stonepath1Tile = new Tile(Assets.stonepath1, 135);
	public static Tile stonepath2Tile = new Tile(Assets.stonepath2, 136);
	public static Tile stonepath3Tile = new Tile(Assets.stonepath3, 137);
	public static Tile stonepath4Tile = new Tile(Assets.stonepath4, 138);

	
	//4x4 Tiles
	public static Tile stump1Tile = new SolidTile(Assets.stump1, 35);
	public static Tile stump2Tile = new SolidTile(Assets.stump2, 36);
	public static Tile stump3Tile = new SolidTile(Assets.stump3, 37);
	public static Tile stump4Tile = new SolidTile(Assets.stump4, 38);
	public static Tile stump5Tile = new SolidTile(Assets.stump5, 39);
	public static Tile stump6Tile = new SolidTile(Assets.stump6, 40);
	public static Tile stump7Tile = new SolidTile(Assets.stump7, 41);
	public static Tile stump8Tile = new SolidTile(Assets.stump8, 42);
	public static Tile stump9Tile = new SolidTile(Assets.stump9, 43);
	public static Tile stump10Tile = new SolidTile(Assets.stump10, 44);
	public static Tile stump11Tile = new SolidTile(Assets.stump11, 45);
	public static Tile stump12Tile = new SolidTile(Assets.stump12, 46);
	public static Tile stump13Tile = new SolidTile(Assets.stump13, 47);
	public static Tile stump14Tile = new SolidTile(Assets.stump14, 48);
	public static Tile stump15Tile = new SolidTile(Assets.stump15, 49);
	public static Tile stump16Tile = new SolidTile(Assets.stump16, 50);
	
	//EVEN LARGER TILES...
	public static Tile tree1Tile = new SolidTile(Assets.tree1, 51);
	public static Tile tree2Tile = new SolidTile(Assets.tree2, 52);
	public static Tile tree3Tile = new SolidTile(Assets.tree3, 53);
	public static Tile tree4Tile = new SolidTile(Assets.tree4, 54);
	public static Tile tree5Tile = new SolidTile(Assets.tree5, 55);
	public static Tile tree6Tile = new SolidTile(Assets.tree6, 56);
	public static Tile tree7Tile = new SolidTile(Assets.tree7, 57);
	public static Tile tree8Tile = new SolidTile(Assets.tree8, 58);
	public static Tile tree9Tile = new SolidTile(Assets.tree9, 59);
	public static Tile tree10Tile = new SolidTile(Assets.tree10, 60);
	public static Tile tree11Tile = new SolidTile(Assets.tree11, 61);
	public static Tile tree12Tile = new SolidTile(Assets.tree12, 62);
	public static Tile tree13Tile = new SolidTile(Assets.tree13, 63);
	public static Tile tree14Tile = new SolidTile(Assets.tree14, 64);
	public static Tile tree15Tile = new SolidTile(Assets.tree15, 65);
	public static Tile tree16Tile = new SolidTile(Assets.tree16, 66);
	public static Tile tree17Tile = new SolidTile(Assets.tree17, 67);
	public static Tile tree18Tile = new SolidTile(Assets.tree18, 68);
	public static Tile tree19Tile = new SolidTile(Assets.tree19, 69);
	public static Tile tree20Tile = new SolidTile(Assets.tree20, 70);
	public static Tile tree21Tile = new SolidTile(Assets.tree21, 71);
	public static Tile tree22Tile = new SolidTile(Assets.tree22, 72);
	public static Tile tree23Tile = new SolidTile(Assets.tree23, 73);
	public static Tile tree24Tile = new SolidTile(Assets.tree24, 74);
	public static Tile tree25Tile = new SolidTile(Assets.tree25, 75);
	public static Tile tree26Tile = new SolidTile(Assets.tree26, 76);
	public static Tile tree27Tile = new SolidTile(Assets.tree27, 77);
	public static Tile tree28Tile = new SolidTile(Assets.tree28, 78);
	public static Tile tree29Tile = new SolidTile(Assets.tree29, 79);
	public static Tile tree30Tile = new SolidTile(Assets.tree30, 80);
	public static Tile tree31Tile = new SolidTile(Assets.tree31, 81);
	public static Tile tree32Tile = new SolidTile(Assets.tree32, 82);
	public static Tile tree33Tile = new SolidTile(Assets.tree33, 83);
	public static Tile tree34Tile = new SolidTile(Assets.tree34, 84);
	public static Tile tree35Tile = new SolidTile(Assets.tree35, 85);
	public static Tile tree36Tile = new SolidTile(Assets.tree36, 86);
	public static Tile tree37Tile = new SolidTile(Assets.tree37, 87);
	public static Tile tree38Tile = new SolidTile(Assets.tree38, 88);
	public static Tile tree39Tile = new SolidTile(Assets.tree39, 89);
	public static Tile tree40Tile = new SolidTile(Assets.tree40, 90);
	public static Tile tree41Tile = new SolidTile(Assets.tree41, 91);
	public static Tile tree42Tile = new SolidTile(Assets.tree42, 92);
	public static Tile tree43Tile = new SolidTile(Assets.tree43, 93);
	public static Tile tree44Tile = new SolidTile(Assets.tree44, 94);
	public static Tile tree45Tile = new SolidTile(Assets.tree45, 95);
	public static Tile tree46Tile = new SolidTile(Assets.tree46, 96);
	public static Tile tree47Tile = new SolidTile(Assets.tree47, 97);
	public static Tile tree48Tile = new SolidTile(Assets.tree48, 98);
	public static Tile tree49Tile = new SolidTile(Assets.tree49, 99);
	public static Tile tree50Tile = new SolidTile(Assets.tree50, 100);
	public static Tile tree51Tile = new SolidTile(Assets.tree51, 101);
	public static Tile tree52Tile = new SolidTile(Assets.tree52, 102);
	public static Tile tree53Tile = new SolidTile(Assets.tree53, 103);
	public static Tile tree54Tile = new SolidTile(Assets.tree54, 104);
	public static Tile tree55Tile = new SolidTile(Assets.tree55, 105);
	public static Tile tree56Tile = new SolidTile(Assets.tree56, 106);
	public static Tile tree57Tile = new SolidTile(Assets.tree57, 107);
	public static Tile tree58Tile = new SolidTile(Assets.tree58, 108);
	public static Tile tree59Tile = new SolidTile(Assets.tree59, 109);
	public static Tile tree60Tile = new SolidTile(Assets.tree60, 110);
	public static Tile tree61Tile = new SolidTile(Assets.tree61, 111);
	public static Tile tree62Tile = new SolidTile(Assets.tree62, 112);
	public static Tile tree63Tile = new SolidTile(Assets.tree63, 113);
	public static Tile tree64Tile = new SolidTile(Assets.tree64, 114);
	public static Tile tree65Tile = new SolidTile(Assets.tree65, 115);
	public static Tile tree66Tile = new SolidTile(Assets.tree66, 116);
	public static Tile tree67Tile = new SolidTile(Assets.tree67, 117);
	public static Tile tree68Tile = new SolidTile(Assets.tree68, 118);
	public static Tile tree69Tile = new SolidTile(Assets.tree69, 119);
	public static Tile tree70Tile = new SolidTile(Assets.tree70, 120);
	public static Tile tree71Tile = new SolidTile(Assets.tree71, 121);
	public static Tile tree72Tile = new SolidTile(Assets.tree72, 122);

	//CLASS
	public static int TILEWIDTH, TILEHEIGHT;
	
	protected BufferedImage texture;
	protected int id;
	
	public Tile(BufferedImage texture, int id)
	{
		this.texture = texture;
		this.id = id;
		Tile.TILEWIDTH = 16;
		Tile.TILEHEIGHT = 16;
		
		tiles[id] = this;
	}
	
	public void tick()
	{
		
	}
	
	public void render(Graphics g, int x, int y)
	{
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	
	public boolean isSolid()
	{
		return false;
	}
	
	public boolean isWater()
	{
		return false;
	}
	
	public boolean isAir()
	{
		return false;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public BufferedImage getTexture()
	{
		return this.texture;
	}
}
