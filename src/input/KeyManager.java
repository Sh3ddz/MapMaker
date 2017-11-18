package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.SwingUtilities;

public class KeyManager implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{
	private boolean[] keys;
	public boolean up, down, left, right, shift, upArrow, downArrow, leftArrow, rightArrow, highlight, gridView, fillTool, openTileSelection, layerUp, layerDown, layerBase, openMenu, holding, leftClick, rightClick, middleClick, mouseWheelUp, mouseWheelDown, dragging, ctrl, z, y;
	public int mX,mY,cX,cY,dX,dY;
	
	public KeyManager()
	{
		keys = new boolean[256];
	}
	
	public void tick()
	{
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		shift = keys[KeyEvent.VK_SHIFT];
		upArrow = keys[KeyEvent.VK_UP];
		downArrow = keys[KeyEvent.VK_DOWN];
		leftArrow = keys[KeyEvent.VK_LEFT];
		rightArrow = keys[KeyEvent.VK_RIGHT];
		ctrl = keys[KeyEvent.VK_CONTROL];
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		keys[e.getKeyCode()] = true;
		holding = true;
		if(e.getKeyCode() == KeyEvent.VK_E)
			openTileSelection = true;
		if(e.getKeyCode() == KeyEvent.VK_EQUALS)
			layerUp = true;
		if(e.getKeyCode() == KeyEvent.VK_MINUS)
			layerDown = true;
		if(e.getKeyCode() == KeyEvent.VK_0)
			layerBase = true;
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			openMenu = true;
		if(e.getKeyCode() == KeyEvent.VK_H)
			highlight = true;
		if(e.getKeyCode() == KeyEvent.VK_G)
			gridView = true;
		if(e.getKeyCode() == KeyEvent.VK_Z)
			z = true;
		if(e.getKeyCode() == KeyEvent.VK_Y)
			y = true;
		if(e.getKeyCode() == KeyEvent.VK_F)
			fillTool = true;
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		keys[e.getKeyCode()] = false;
		holding = false;
		if(e.getKeyCode() == KeyEvent.VK_E)
			openTileSelection = false;
		if(e.getKeyCode() == KeyEvent.VK_EQUALS)
			layerUp = false;
		if(e.getKeyCode() == KeyEvent.VK_MINUS)
			layerDown = false;
		if(e.getKeyCode() == KeyEvent.VK_0)
			layerBase = false;
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			openMenu = false;
		if(e.getKeyCode() == KeyEvent.VK_H)
			highlight = false;
		if(e.getKeyCode() == KeyEvent.VK_G)
			gridView = false;
		if(e.getKeyCode() == KeyEvent.VK_Z)
			z = false;
		if(e.getKeyCode() == KeyEvent.VK_Y)
			y = false;
		if(e.getKeyCode() == KeyEvent.VK_F)
			fillTool = false;
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}

	@Override
	public void mouseDragged(MouseEvent e) 
	{
	    dragging = true;
		dY = e.getY();
	    dX = e.getX();
		mY = e.getY();
		mX = e.getX();
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
	    mY=e.getY();
	    mX=e.getX();		
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
	    cY=e.getY();
	    cX=e.getX();
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		if(SwingUtilities.isLeftMouseButton(e))
		{
			leftClick = true;
		}
		if(SwingUtilities.isRightMouseButton(e))
		{
			rightClick = true;
			//for right click camera movements
			cY = e.getY();
			cX = e.getX();
		}
		if(SwingUtilities.isMiddleMouseButton(e))
		{
			middleClick = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		dragging = false;
		if(SwingUtilities.isLeftMouseButton(e))
		{
			leftClick = false;
		}
		if(SwingUtilities.isRightMouseButton(e))
		{
			rightClick = false;
		}
		if(SwingUtilities.isMiddleMouseButton(e))
		{
			middleClick = false;
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
	    int notches = e.getWheelRotation();
	    if (notches < 0)
	    {
	    	mouseWheelUp = true;
	    	mouseWheelDown = false;
	    } 
	    else 
	    {
	    	mouseWheelDown = true;
	    	mouseWheelUp = false;
	    }
	}

}