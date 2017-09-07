package gfx;

import java.awt.image.BufferedImage;

public class Assets
{
	
	private static final int WIDTH = 16, HEIGHT = 16;
	
	//IMAGES
	public static BufferedImage playerHead, playerBody, grass, grass2, flower, grass3, grass4, entrancePadL, entrancePadR, path, path2, path3, path4, snow, snow2,
								dirt, dirt2, dirt3,																		         stonepath1, stonepath2,
								water, water2, waterRock,																		 stonepath3, stonepath4,
								bush1, bush2, rock1, rock2, struc1, struc2, tallgrass1, tallgrass2, sign1, sign2, tallgrassend1, tallgrassend2, fencetop, fenceconnecttop,
								bush3, bush4, rock3, rock4, struc3, struc4, tallgrass3, tallgrass4, sign3, sign4, tallgrassend3, tallgrassend4, fencebottom, fenceconnectshadow, fenceconnectdown, 
								       tree1, tree2, tree3, tree4, tree5, tree6,					stump1, stump2, stump3, stump4,
								tree7, tree8, tree9, tree10, tree11, tree12, tree13, tree14,		stump5, stump6, stump7, stump8,
								tree15, tree16, tree17, tree18, tree19, tree20, tree21, tree22,    	stump9, stump10, stump11, stump12,
								tree23, tree24, tree25, tree26, tree27, tree28, tree29, tree30,		stump13, stump14, stump15, stump16,
								tree31, tree32, tree33, tree34, tree35, tree36, tree37, tree38,
								tree39, tree40, tree41, tree42, tree43, tree44, tree45, tree46,
								tree47, tree48, tree49, tree50, tree51, tree52, tree53, tree54,
								tree55, tree56, tree57, tree58, tree59, tree60, tree61, tree62,
									   tree63, tree64, tree65, tree66, tree67, tree68,
									   		   tree69, tree70, tree71, tree72,
									   		   
								selector, muteSound, nextSong,
								
								treeFull, stumpFull, bushFull, rockFull, strucFull, tallgrassFull, signFull, fenceFull;
	//SPRITESHEETS
	public static SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/resources/textures/spritesheet.png"));

	public static void init()
	{		
		playerHead = sheet.crop(WIDTH*2, HEIGHT*28, WIDTH*2, HEIGHT*2);
		playerBody = sheet.crop(WIDTH*2, HEIGHT*30, WIDTH*2, HEIGHT*2);
		selector = sheet.crop(0, HEIGHT*27, WIDTH, HEIGHT);
		muteSound = sheet.crop(WIDTH, HEIGHT*27, WIDTH, HEIGHT);
		nextSong = sheet.crop(WIDTH*2, HEIGHT*27, WIDTH, HEIGHT);
		
		grass = sheet.crop(0, 0, WIDTH, HEIGHT);
		grass2 = sheet.crop(WIDTH, 0, WIDTH, HEIGHT);
	    flower = sheet.crop(WIDTH*2, 0, WIDTH, HEIGHT);
	    grass3 = sheet.crop(WIDTH*3, 0, WIDTH, HEIGHT);
	    grass4 = sheet.crop(WIDTH*4, 0, WIDTH, HEIGHT);
	    entrancePadL = sheet.crop(WIDTH*5, 0, WIDTH, HEIGHT);
	    entrancePadR = sheet.crop(WIDTH*6, 0, WIDTH, HEIGHT);
	    path = sheet.crop(WIDTH*7, 0, WIDTH, HEIGHT);
	    path2 = sheet.crop(WIDTH*8, 0, WIDTH, HEIGHT);
	    path3 = sheet.crop(WIDTH*9, 0, WIDTH, HEIGHT);
	    path4 = sheet.crop(WIDTH*10, 0, WIDTH, HEIGHT);
	    dirt = sheet.crop(0, HEIGHT, WIDTH, HEIGHT);
	    dirt2 = sheet.crop(WIDTH, HEIGHT, WIDTH, HEIGHT);
	    dirt3 = sheet.crop(WIDTH*2, HEIGHT, WIDTH, HEIGHT);
	    water = sheet.crop(0, HEIGHT*2, WIDTH, HEIGHT);
	    water2 = sheet.crop(WIDTH, HEIGHT*2, WIDTH, HEIGHT);
	    waterRock = sheet.crop(WIDTH*2, HEIGHT*2, WIDTH, HEIGHT);
	    snow = sheet.crop(WIDTH*15, 0, WIDTH, HEIGHT);
	    snow2 = sheet.crop(WIDTH*16, 0, WIDTH, HEIGHT);
	    fencetop = sheet.crop(WIDTH*12, HEIGHT*3, WIDTH, HEIGHT);
	    fencebottom = sheet.crop(WIDTH*12, HEIGHT*4, WIDTH, HEIGHT);
	    fenceconnecttop = sheet.crop(WIDTH*13, HEIGHT*3, WIDTH, HEIGHT);
	    fenceconnectshadow = sheet.crop(WIDTH*13, HEIGHT*4, WIDTH, HEIGHT);
	    fenceconnectdown = sheet.crop(WIDTH*14, HEIGHT*4, WIDTH, HEIGHT);
	    
	    
	    //2x2 tiles
	    bush1 = sheet.crop(0, HEIGHT*3, WIDTH, HEIGHT);
	    bush2 = sheet.crop(WIDTH, HEIGHT*3, WIDTH, HEIGHT);
	    bush3 = sheet.crop(0, HEIGHT*4, WIDTH, HEIGHT);
	    bush4 = sheet.crop(WIDTH, HEIGHT*4, WIDTH, HEIGHT);
	    rock1 = sheet.crop(WIDTH*2, HEIGHT*3, WIDTH, HEIGHT);
	    rock2 = sheet.crop(WIDTH*3, HEIGHT*3, WIDTH, HEIGHT);
	    rock3 = sheet.crop(WIDTH*2, HEIGHT*4, WIDTH, HEIGHT);
	    rock4 = sheet.crop(WIDTH*3, HEIGHT*4, WIDTH, HEIGHT);
	    struc1 = sheet.crop(WIDTH*4, HEIGHT*3, WIDTH, HEIGHT);
	    struc2 = sheet.crop(WIDTH*5, HEIGHT*3, WIDTH, HEIGHT);
	    struc3 = sheet.crop(WIDTH*4, HEIGHT*4, WIDTH, HEIGHT);
	    struc4 = sheet.crop(WIDTH*5, HEIGHT*4, WIDTH, HEIGHT);
	    tallgrass1 = sheet.crop(WIDTH*6, HEIGHT*3, WIDTH, HEIGHT);
	    tallgrass2 = sheet.crop(WIDTH*7, HEIGHT*3, WIDTH, HEIGHT);
	    tallgrass3 = sheet.crop(WIDTH*6, HEIGHT*4, WIDTH, HEIGHT);
	    tallgrass4 = sheet.crop(WIDTH*7, HEIGHT*4, WIDTH, HEIGHT);
	    sign1 = sheet.crop(WIDTH*8, HEIGHT*3, WIDTH, HEIGHT);
	    sign2 = sheet.crop(WIDTH*9, HEIGHT*3, WIDTH, HEIGHT);
	    sign3 = sheet.crop(WIDTH*8, HEIGHT*4, WIDTH, HEIGHT);
	    sign4 = sheet.crop(WIDTH*9, HEIGHT*4, WIDTH, HEIGHT);
	    tallgrassend1 = sheet.crop(WIDTH*10, HEIGHT*3, WIDTH, HEIGHT);
	    tallgrassend2 = sheet.crop(WIDTH*11, HEIGHT*3, WIDTH, HEIGHT);
	    tallgrassend3 = sheet.crop(WIDTH*10, HEIGHT*4, WIDTH, HEIGHT);
	    tallgrassend4 = sheet.crop(WIDTH*11, HEIGHT*4, WIDTH, HEIGHT);
	    stonepath1 = sheet.crop(WIDTH*8, HEIGHT, WIDTH, HEIGHT);
	    stonepath2 = sheet.crop(WIDTH*9, HEIGHT, WIDTH, HEIGHT);
	    stonepath3 = sheet.crop(WIDTH*8, HEIGHT*2, WIDTH, HEIGHT);
	    stonepath4 = sheet.crop(WIDTH*9, HEIGHT*2, WIDTH, HEIGHT);


	    //4x4 tiles
	    stump1 = sheet.crop(WIDTH*8, HEIGHT*5, WIDTH, HEIGHT);
	    stump2 = sheet.crop(WIDTH*9, HEIGHT*5, WIDTH, HEIGHT);
	    stump3 = sheet.crop(WIDTH*10, HEIGHT*5, WIDTH, HEIGHT);
	    stump4 = sheet.crop(WIDTH*11, HEIGHT*5, WIDTH, HEIGHT);
	    stump5 = sheet.crop(WIDTH*8, HEIGHT*6, WIDTH, HEIGHT);
	    stump6 = sheet.crop(WIDTH*9, HEIGHT*6, WIDTH, HEIGHT);
	    stump7 = sheet.crop(WIDTH*10, HEIGHT*6, WIDTH, HEIGHT);
	    stump8 = sheet.crop(WIDTH*11, HEIGHT*6, WIDTH, HEIGHT);
	    stump9 = sheet.crop(WIDTH*8, HEIGHT*7, WIDTH, HEIGHT);
	    stump10 = sheet.crop(WIDTH*9, HEIGHT*7, WIDTH, HEIGHT);
	    stump11 = sheet.crop(WIDTH*10, HEIGHT*7, WIDTH, HEIGHT);
	    stump12 = sheet.crop(WIDTH*11, HEIGHT*7, WIDTH, HEIGHT);
	    stump13 = sheet.crop(WIDTH*8, HEIGHT*8, WIDTH, HEIGHT);
	    stump14 = sheet.crop(WIDTH*9, HEIGHT*8, WIDTH, HEIGHT);
	    stump15 = sheet.crop(WIDTH*10, HEIGHT*8, WIDTH, HEIGHT);
	    stump16 = sheet.crop(WIDTH*11, HEIGHT*8, WIDTH, HEIGHT);
	    
	    //EVEN LARGER TILES...
	    tree1 = sheet.crop(WIDTH, HEIGHT*5, WIDTH, HEIGHT);
	    tree2 = sheet.crop(WIDTH*2, HEIGHT*5, WIDTH, HEIGHT);
	    tree3 = sheet.crop(WIDTH*3, HEIGHT*5, WIDTH, HEIGHT);
	    tree4 = sheet.crop(WIDTH*4, HEIGHT*5, WIDTH, HEIGHT);
	    tree5 = sheet.crop(WIDTH*5, HEIGHT*5, WIDTH, HEIGHT);
	    tree6 = sheet.crop(WIDTH*6, HEIGHT*5, WIDTH, HEIGHT);
	    tree7 = sheet.crop(0, HEIGHT*6, WIDTH, HEIGHT);
	    tree8 = sheet.crop(WIDTH, HEIGHT*6, WIDTH, HEIGHT);
	    tree9 = sheet.crop(WIDTH*2, HEIGHT*6, WIDTH, HEIGHT);
	    tree10 = sheet.crop(WIDTH*3, HEIGHT*6, WIDTH, HEIGHT);
	    tree11 = sheet.crop(WIDTH*4, HEIGHT*6, WIDTH, HEIGHT);
	    tree12 = sheet.crop(WIDTH*5, HEIGHT*6, WIDTH, HEIGHT);
	    tree13 = sheet.crop(WIDTH*6, HEIGHT*6, WIDTH, HEIGHT);
	    tree14 = sheet.crop(WIDTH*7, HEIGHT*6, WIDTH, HEIGHT);
	    tree15 = sheet.crop(0, HEIGHT*7, WIDTH, HEIGHT);
	    tree16 = sheet.crop(WIDTH, HEIGHT*7, WIDTH, HEIGHT);
	    tree17 = sheet.crop(WIDTH*2, HEIGHT*7, WIDTH, HEIGHT);
	    tree18 = sheet.crop(WIDTH*3, HEIGHT*7, WIDTH, HEIGHT);
	    tree19 = sheet.crop(WIDTH*4, HEIGHT*7, WIDTH, HEIGHT);
	    tree20 = sheet.crop(WIDTH*5, HEIGHT*7, WIDTH, HEIGHT);
	    tree21 = sheet.crop(WIDTH*6, HEIGHT*7, WIDTH, HEIGHT);
	    tree22 = sheet.crop(WIDTH*7, HEIGHT*7, WIDTH, HEIGHT);
	    tree23 = sheet.crop(0, HEIGHT*8, WIDTH, HEIGHT);
	    tree24 = sheet.crop(WIDTH, HEIGHT*8, WIDTH, HEIGHT);
	    tree25 = sheet.crop(WIDTH*2, HEIGHT*8, WIDTH, HEIGHT);
	    tree26 = sheet.crop(WIDTH*3, HEIGHT*8, WIDTH, HEIGHT);
	    tree27 = sheet.crop(WIDTH*4, HEIGHT*8, WIDTH, HEIGHT);
	    tree28 = sheet.crop(WIDTH*5, HEIGHT*8, WIDTH, HEIGHT);
	    tree29 = sheet.crop(WIDTH*6, HEIGHT*8, WIDTH, HEIGHT);
	    tree30 = sheet.crop(WIDTH*7, HEIGHT*8, WIDTH, HEIGHT);
	    tree31 = sheet.crop(0, HEIGHT*9, WIDTH, HEIGHT);
	    tree32 = sheet.crop(WIDTH, HEIGHT*9, WIDTH, HEIGHT);
	    tree33 = sheet.crop(WIDTH*2, HEIGHT*9, WIDTH, HEIGHT);
	    tree34 = sheet.crop(WIDTH*3, HEIGHT*9, WIDTH, HEIGHT);
	    tree35 = sheet.crop(WIDTH*4, HEIGHT*9, WIDTH, HEIGHT);
	    tree36 = sheet.crop(WIDTH*5, HEIGHT*9, WIDTH, HEIGHT);
	    tree37 = sheet.crop(WIDTH*6, HEIGHT*9, WIDTH, HEIGHT);
	    tree38 = sheet.crop(WIDTH*7, HEIGHT*9, WIDTH, HEIGHT);
	    tree39 = sheet.crop(0, HEIGHT*10, WIDTH, HEIGHT);
	    tree40 = sheet.crop(WIDTH, HEIGHT*10, WIDTH, HEIGHT);
	    tree41 = sheet.crop(WIDTH*2, HEIGHT*10, WIDTH, HEIGHT);
	    tree42 = sheet.crop(WIDTH*3, HEIGHT*10, WIDTH, HEIGHT);
	    tree43 = sheet.crop(WIDTH*4, HEIGHT*10, WIDTH, HEIGHT);
	    tree44 = sheet.crop(WIDTH*5, HEIGHT*10, WIDTH, HEIGHT);
	    tree45 = sheet.crop(WIDTH*6, HEIGHT*10, WIDTH, HEIGHT);
	    tree46 = sheet.crop(WIDTH*7, HEIGHT*10, WIDTH, HEIGHT);
	    tree47 = sheet.crop(0, HEIGHT*11, WIDTH, HEIGHT);
	    tree48 = sheet.crop(WIDTH, HEIGHT*11, WIDTH, HEIGHT);
	    tree49 = sheet.crop(WIDTH*2, HEIGHT*11, WIDTH, HEIGHT);
	    tree50 = sheet.crop(WIDTH*3, HEIGHT*11, WIDTH, HEIGHT);
	    tree51 = sheet.crop(WIDTH*4, HEIGHT*11, WIDTH, HEIGHT);
	    tree52 = sheet.crop(WIDTH*5, HEIGHT*11, WIDTH, HEIGHT);
	    tree53 = sheet.crop(WIDTH*6, HEIGHT*11, WIDTH, HEIGHT);
	    tree54 = sheet.crop(WIDTH*7, HEIGHT*11, WIDTH, HEIGHT);
	    tree55 = sheet.crop(0, HEIGHT*12, WIDTH, HEIGHT);
	    tree56 = sheet.crop(WIDTH, HEIGHT*12, WIDTH, HEIGHT);
	    tree57 = sheet.crop(WIDTH*2, HEIGHT*12, WIDTH, HEIGHT);
	    tree58 = sheet.crop(WIDTH*3, HEIGHT*12, WIDTH, HEIGHT);
	    tree59 = sheet.crop(WIDTH*4, HEIGHT*12, WIDTH, HEIGHT);
	    tree60 = sheet.crop(WIDTH*5, HEIGHT*12, WIDTH, HEIGHT);
	    tree61 = sheet.crop(WIDTH*6, HEIGHT*12, WIDTH, HEIGHT);
	    tree62 = sheet.crop(WIDTH*7, HEIGHT*12, WIDTH, HEIGHT);
	    tree63 = sheet.crop(WIDTH, HEIGHT*13, WIDTH, HEIGHT);
	    tree64 = sheet.crop(WIDTH*2, HEIGHT*13, WIDTH, HEIGHT);
	    tree65 = sheet.crop(WIDTH*3, HEIGHT*13, WIDTH, HEIGHT);
	    tree66 = sheet.crop(WIDTH*4, HEIGHT*13, WIDTH, HEIGHT);
	    tree67 = sheet.crop(WIDTH*5, HEIGHT*13, WIDTH, HEIGHT);
	    tree68 = sheet.crop(WIDTH*6, HEIGHT*13, WIDTH, HEIGHT);
	    tree69 = sheet.crop(WIDTH*2, HEIGHT*14, WIDTH, HEIGHT);
	    tree70 = sheet.crop(WIDTH*3, HEIGHT*14, WIDTH, HEIGHT);
	    tree71 = sheet.crop(WIDTH*4, HEIGHT*14, WIDTH, HEIGHT);
	    tree72 = sheet.crop(WIDTH*5, HEIGHT*14, WIDTH, HEIGHT);

	    //full ones.
	    treeFull = sheet.crop(0, HEIGHT*5, WIDTH*8, HEIGHT*10);
	    stumpFull= sheet.crop(WIDTH*8, HEIGHT*5, WIDTH*4, HEIGHT*4);
	    bushFull = sheet.crop(0, HEIGHT*3, WIDTH*2, HEIGHT*2);
	    rockFull = sheet.crop(WIDTH*2, HEIGHT*3, WIDTH*2, HEIGHT*2);
	    strucFull = sheet.crop(WIDTH*4, HEIGHT*3, WIDTH*2, HEIGHT*2);
	    tallgrassFull = sheet.crop(WIDTH*6, HEIGHT*3, WIDTH*2, HEIGHT*2);
	    signFull = sheet.crop(WIDTH*8, HEIGHT*3, WIDTH*2, HEIGHT*2);
	    fenceFull = sheet.crop(WIDTH*12, HEIGHT*3, WIDTH*2, HEIGHT*2);
	}
}