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
	public static Tile grassTile = new Tile(Assets.grass, 0);
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
	public static Tile path5Tile = new Tile(Assets.path5, 256);
	public static Tile dirtTile = new Tile(Assets.dirt, 10);
	public static Tile dirt2Tile = new Tile(Assets.dirt2, 139);
	public static Tile dirt3Tile = new Tile(Assets.dirt3, 140);
	public static Tile dirt4Tile = new Tile(Assets.dirt4, 141);
	public static Tile dirt5Tile = new Tile(Assets.dirt5, 142);
	public static Tile smallRockTile = new SolidTile(Assets.smallRock, 143);
	public static Tile mushroomTile = new SolidTile(Assets.mushroom, 144);
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
	public static Tile spawnPad1Tile = new Tile(Assets.spawnPad1, 257);
	public static Tile spawnPad2Tile = new Tile(Assets.spawnPad2, 258);
	public static Tile spawnPad3Tile = new Tile(Assets.spawnPad3, 259);
	public static Tile spawnPad4Tile = new Tile(Assets.spawnPad4, 260);
	public static Tile mushroomGroup1Tile = new SolidTile(Assets.mushroomGroup1, 145);
	public static Tile mushroomGroup2Tile = new SolidTile(Assets.mushroomGroup2, 146);
	public static Tile mushroomGroup3Tile = new SolidTile(Assets.mushroomGroup3, 147);
	public static Tile mushroomGroup4Tile = new SolidTile(Assets.mushroomGroup4, 148);
	public static Tile bench1 = new Tile(Assets.bench1, 234);
	public static Tile bench2 = new Tile(Assets.bench2, 235);
	public static Tile bench3 = new Tile(Assets.bench3, 236);
	public static Tile bench4 = new Tile(Assets.bench4, 237);
	public static Tile bench5 = new Tile(Assets.bench5, 238);
	public static Tile bench6 = new Tile(Assets.bench6, 239);
	public static Tile chair1 = new Tile(Assets.chair1, 252);
	public static Tile chair2 = new Tile(Assets.chair2, 253);
	public static Tile chair3 = new Tile(Assets.chair3, 254);
	public static Tile chair4 = new Tile(Assets.chair4, 255);
	
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
	public static Tile stumpTree1Tile = new SolidTile(Assets.stumpTree1, 149);
	public static Tile stumpTree2Tile = new SolidTile(Assets.stumpTree2, 150);
	public static Tile stumpTree3Tile = new SolidTile(Assets.stumpTree3, 151);
	public static Tile stumpTree4Tile = new SolidTile(Assets.stumpTree4, 152);
	public static Tile stumpTree5Tile = new SolidTile(Assets.stumpTree5, 153);
	public static Tile stumpTree6Tile = new SolidTile(Assets.stumpTree6, 154);
	public static Tile stumpTree7Tile = new SolidTile(Assets.stumpTree7, 155);
	public static Tile stumpTree8Tile = new SolidTile(Assets.stumpTree8, 156);
	public static Tile stumpTree9Tile = new SolidTile(Assets.stumpTree9, 157);
	public static Tile stumpTree10Tile = new SolidTile(Assets.stumpTree10, 158);
	public static Tile stumpTree11Tile = new SolidTile(Assets.stumpTree11, 159);
	public static Tile stumpTree12Tile = new SolidTile(Assets.stumpTree12, 160);
	public static Tile stumpTree13Tile = new SolidTile(Assets.stumpTree13, 161);
	public static Tile stumpTree14Tile = new SolidTile(Assets.stumpTree14, 162);
	public static Tile stumpTree15Tile = new SolidTile(Assets.stumpTree15, 163);
	public static Tile stumpTree16Tile = new SolidTile(Assets.stumpTree16, 164);
	public static Tile table1 = new SolidTile(Assets.table1, 240);
	public static Tile table2 = new SolidTile(Assets.table2, 241);
	public static Tile table3 = new SolidTile(Assets.table3, 242);
	public static Tile table4 = new SolidTile(Assets.table4, 243);
	public static Tile table5 = new SolidTile(Assets.table5, 244);
	public static Tile table6 = new SolidTile(Assets.table6, 245);
	public static Tile table7 = new SolidTile(Assets.table7, 246);
	public static Tile table8 = new SolidTile(Assets.table8, 247);
	public static Tile table9 = new SolidTile(Assets.table9, 248);
	public static Tile table10 = new SolidTile(Assets.table10, 249);
	public static Tile table11 = new SolidTile(Assets.table11, 250);
	public static Tile table12 = new SolidTile(Assets.table12, 251);
	
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
	//house
	public static Tile house1Tile = new SolidTile(Assets.house1, 165);
    public static Tile house2Tile = new SolidTile(Assets.house2, 166);
    public static Tile house3Tile = new SolidTile(Assets.house3, 167);
    public static Tile house4Tile = new SolidTile(Assets.house4, 168);
    public static Tile house5Tile = new SolidTile(Assets.house5, 169);
    public static Tile house6Tile = new SolidTile(Assets.house6, 170);
    public static Tile house7Tile = new SolidTile(Assets.house7, 171);
    public static Tile house8Tile = new SolidTile(Assets.house8, 172);
    public static Tile house9Tile = new SolidTile(Assets.house9, 173);
    public static Tile house10Tile = new SolidTile(Assets.house10, 174);
    public static Tile house11Tile = new SolidTile(Assets.house11, 175);
    public static Tile house12Tile = new SolidTile(Assets.house12, 176);
    public static Tile house13Tile = new SolidTile(Assets.house13, 177);
    public static Tile house14Tile = new SolidTile(Assets.house14, 178);
    public static Tile house15Tile = new SolidTile(Assets.house15, 179);
    public static Tile house16Tile = new SolidTile(Assets.house16, 180);
    public static Tile house17Tile = new SolidTile(Assets.house17, 181);
    public static Tile house18Tile = new SolidTile(Assets.house18, 182);
    public static Tile house19Tile = new SolidTile(Assets.house19, 183);
    public static Tile house20Tile = new SolidTile(Assets.house20, 184);
    public static Tile house21Tile = new SolidTile(Assets.house21, 185);
    public static Tile house22Tile = new SolidTile(Assets.house22, 186);
    public static Tile house23Tile = new SolidTile(Assets.house23, 187);
    public static Tile house24Tile = new SolidTile(Assets.house24, 188);
    public static Tile house25Tile = new SolidTile(Assets.house25, 189);
    public static Tile house26Tile = new SolidTile(Assets.house26, 190);
    public static Tile house27Tile = new SolidTile(Assets.house27, 191);
    public static Tile house28Tile = new SolidTile(Assets.house28, 192);
    public static Tile house29Tile = new SolidTile(Assets.house29, 193);
    public static Tile house30Tile = new SolidTile(Assets.house30, 194);
    public static Tile house31Tile = new SolidTile(Assets.house31, 195);
    public static Tile house32Tile = new SolidTile(Assets.house32, 196);
    public static Tile house33Tile = new SolidTile(Assets.house33, 197);
    public static Tile house34Tile = new SolidTile(Assets.house34, 198);
    public static Tile house35Tile = new SolidTile(Assets.house35, 199);
    public static Tile house36Tile = new SolidTile(Assets.house36, 200);
    public static Tile house37Tile = new SolidTile(Assets.house37, 201);
    public static Tile house38Tile = new SolidTile(Assets.house38, 202);
    public static Tile house39Tile = new SolidTile(Assets.house39, 203);
    public static Tile house40Tile = new SolidTile(Assets.house40, 204);
    public static Tile house41Tile = new SolidTile(Assets.house41, 205);
    public static Tile house42Tile = new SolidTile(Assets.house42, 206);
    public static Tile house43Tile = new SolidTile(Assets.house43, 207);
    public static Tile house44Tile = new SolidTile(Assets.house44, 208);
    public static Tile house45Tile = new SolidTile(Assets.house45, 209);
    public static Tile house46Tile = new SolidTile(Assets.house46, 210);
    public static Tile house47Tile = new SolidTile(Assets.house47, 211);
    public static Tile house48Tile = new SolidTile(Assets.house48, 212);
    public static Tile house49Tile = new SolidTile(Assets.house49, 213);
    public static Tile house50Tile = new SolidTile(Assets.house50, 214);
    public static Tile house51Tile = new SolidTile(Assets.house51, 215);
    public static Tile house52Tile = new SolidTile(Assets.house52, 216);
    public static Tile house53Tile = new SolidTile(Assets.house53, 217);
    public static Tile house54Tile = new SolidTile(Assets.house54, 218);
    public static Tile house55Tile = new SolidTile(Assets.house55, 219);
    public static Tile houseShadow1Tile = new Tile(Assets.houseShadow1, 220);
    public static Tile houseShadow2Tile = new Tile(Assets.houseShadow2, 221);
    public static Tile houseShadow3Tile = new Tile(Assets.houseShadow3, 222);
    //cliff
    public static Tile cliff1 = new Tile(Assets.cliff1, 261);
    public static Tile cliff2 = new Tile(Assets.cliff2, 262);
    public static Tile cliff3 = new Tile(Assets.cliff3, 263);
    public static Tile cliff4 = new SolidTile(Assets.cliff4, 264);
    public static Tile cliff5 = new SolidTile(Assets.cliff5, 265);
    public static Tile cliff6 = new Tile(Assets.cliff6, 266);
    public static Tile cliff7 = new Tile(Assets.cliff7, 267);
    public static Tile cliff8 = new SolidTile(Assets.cliff8, 268);
    public static Tile cliff9 = new SolidTile(Assets.cliff9, 269);
    public static Tile cliff10 = new SolidTile(Assets.cliff10, 270);
    public static Tile cliff11 = new SolidTile(Assets.cliff11, 271);
    public static Tile cliff12 = new Tile(Assets.cliff12, 272);
    public static Tile cliff13 = new SolidTile(Assets.cliff13, 273);
    public static Tile cliff14 = new SolidTile(Assets.cliff14, 274);
    public static Tile cliff15 = new SolidTile(Assets.cliff15, 275);
    public static Tile cliff16 = new SolidTile(Assets.cliff16, 276);
    public static Tile cliff17 = new SolidTile(Assets.cliff17, 277);
    public static Tile cliff18 = new SolidTile(Assets.cliff18, 278);
    public static Tile cliff19 = new SolidTile(Assets.cliff19, 279);
    public static Tile cliff20 = new SolidTile(Assets.cliff20, 280);
    public static Tile cliff21 = new SolidTile(Assets.cliff21, 281);
    public static Tile cliff22 = new SolidTile(Assets.cliff22, 282);
    public static Tile cliff23 = new SolidTile(Assets.cliff23, 283);
    public static Tile cliff24 = new SolidTile(Assets.cliff24, 284);
    public static Tile cliff25 = new SolidTile(Assets.cliff25, 285);
    public static Tile cliff26 = new SolidTile(Assets.cliff26, 286);
    public static Tile cliff27 = new SolidTile(Assets.cliff27, 287);
    public static Tile cliff28 = new SolidTile(Assets.cliff28, 288);
    public static Tile cliff29 = new SolidTile(Assets.cliff29, 289);
    public static Tile cliff30 = new SolidTile(Assets.cliff30, 290);
    public static Tile cliff31 = new SolidTile(Assets.cliff31, 291);
    public static Tile cliff32 = new SolidTile(Assets.cliff32, 292);
    public static Tile cliff33 = new SolidTile(Assets.cliff33, 293);
    public static Tile cliff34 = new SolidTile(Assets.cliff34, 294);
    public static Tile cliff35 = new SolidTile(Assets.cliff35, 295);
    public static Tile cliff36 = new SolidTile(Assets.cliff36, 296);
    public static Tile cliff37 = new SolidTile(Assets.cliff37, 297);
    public static Tile cliff38 = new SolidTile(Assets.cliff38, 298);
    public static Tile cliff39 = new SolidTile(Assets.cliff39, 299);
    public static Tile cliff40 = new SolidTile(Assets.cliff40, 300);
    public static Tile cliff41 = new SolidTile(Assets.cliff41, 301);
    public static Tile cliff42 = new SolidTile(Assets.cliff42, 302);
    public static Tile cliff43 = new SolidTile(Assets.cliff43, 303);
    public static Tile cliff44 = new SolidTile(Assets.cliff44, 304);
    public static Tile cliff45 = new SolidTile(Assets.cliff45, 305);
    public static Tile cliff46 = new SolidTile(Assets.cliff46, 306);
    public static Tile cliff47 = new SolidTile(Assets.cliff47, 307);
    public static Tile cliff48 = new SolidTile(Assets.cliff48, 308);
    public static Tile cliff49 = new SolidTile(Assets.cliff49, 309);
    public static Tile cliff50 = new SolidTile(Assets.cliff50, 310);
    public static Tile cliff51 = new SolidTile(Assets.cliff51, 311);
    public static Tile cliff52 = new SolidTile(Assets.cliff52, 312);
    public static Tile cliff53 = new SolidTile(Assets.cliff53, 313);
    public static Tile cliff54 = new SolidTile(Assets.cliff54, 314);
    public static Tile cliff55 = new SolidTile(Assets.cliff55, 315);
    public static Tile cliff56 = new SolidTile(Assets.cliff56, 316);
    public static Tile cliff57 = new SolidTile(Assets.cliff57, 317);
    public static Tile cliff58 = new SolidTile(Assets.cliff58, 318);
    public static Tile cliff59 = new SolidTile(Assets.cliff59, 319);
    public static Tile cliff60 = new SolidTile(Assets.cliff60, 320);
    public static Tile cliff61 = new SolidTile(Assets.cliff61, 321);
    public static Tile cliff62 = new SolidTile(Assets.cliff62, 322);
    public static Tile cliff63 = new SolidTile(Assets.cliff63, 323);
    public static Tile cliff64 = new SolidTile(Assets.cliff64, 324);
    public static Tile cliff65 = new SolidTile(Assets.cliff65, 325);
    public static Tile cliff66 = new SolidTile(Assets.cliff66, 326);
    public static Tile cliff67 = new SolidTile(Assets.cliff67, 327);
    public static Tile cliff68 = new SolidTile(Assets.cliff68, 328);
    //hedge
    public static Tile hedge1 = new SolidTile(Assets.hedge1, 223);
    public static Tile hedge2 = new SolidTile(Assets.hedge2, 224);
    public static Tile hedge3 = new SolidTile(Assets.hedge3, 225);
    public static Tile hedge4 = new SolidTile(Assets.hedge4, 226);
    public static Tile hedge5 = new SolidTile(Assets.hedge5, 227);
    public static Tile hedge6 = new SolidTile(Assets.hedge6, 228);
    public static Tile hedge7 = new SolidTile(Assets.hedge7, 229);
    public static Tile hedge8 = new SolidTile(Assets.hedge8, 230);
    public static Tile hedge9 = new SolidTile(Assets.hedge9, 231);
    public static Tile hedge10 = new SolidTile(Assets.hedge10, 232);
    public static Tile hedge11 = new SolidTile(Assets.hedge11, 233);
	
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
