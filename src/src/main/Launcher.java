package main;

import MapMaker.MapMaker;

public class Launcher 
{

   public static void main(String[] args)
   {
      MapMaker mapmaker = new MapMaker("MapMaker", 640, 360);
      mapmaker.start();
   }
}