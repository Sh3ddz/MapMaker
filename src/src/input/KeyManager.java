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
	public boolean up, down, left, right, shift, upArrow, downArrow, leftArrow, rightArrow, stateSwap, zoomIn, zoomOut, zoomReset, escape, holding, leftClick, rightClick, middleClick, mouseWheelUp, mouseWheelDown, dragging;
	public int mX,mY,cX,cY;
	
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
		//zoomIn = keys[KeyEvent.VK_EQUALS];
		//zoomOut = keys[KeyEvent.VK_MINUS];
		//stateSwap = keys[KeyEvent.VK_E];
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		keys[e.getKeyCode()] = true;
		holding = true;
		if(e.getKeyCode() == KeyEvent.VK_E)
			stateSwap = true;
		if(e.getKeyCode() == KeyEvent.VK_EQUALS)
			zoomIn = true;
		if(e.getKeyCode() == KeyEvent.VK_MINUS)
			zoomOut = true;
		if(e.getKeyCode() == KeyEvent.VK_0)
			zoomReset = true;
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			escape = true;
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		keys[e.getKeyCode()] = false;
		holding = false;
		if(e.getKeyCode() == KeyEvent.VK_E)
			stateSwap = false;
		if(e.getKeyCode() == KeyEvent.VK_EQUALS)
			zoomIn = false;
		if(e.getKeyCode() == KeyEvent.VK_MINUS)
			zoomOut = false;
		if(e.getKeyCode() == KeyEvent.VK_0)
			zoomReset = false;
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			escape = false;
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}

	@Override
	public void mouseDragged(MouseEvent e) 
	{
	    mY=e.getY();
	    mX=e.getX();
	    dragging = true;
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