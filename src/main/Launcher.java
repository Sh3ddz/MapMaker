package main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import MapMaker.MapMaker;

public class Launcher 
{

   public static void main(String[] args)
   {
      MapMaker mapmaker = new MapMaker("MapMaker", 640, 360);
      mapmaker.start();
      
      try
      {
    	  UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
      {
    	  e.printStackTrace();
      }
   }
}