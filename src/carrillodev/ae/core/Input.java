package carrillodev.ae.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

import carrillodev.ae.tool.Vector;

public class Input implements KeyListener, MouseListener, MouseMotionListener
{
	public static HashMap<Integer, Boolean> keys = new HashMap<Integer, Boolean>(); // Global key input hashmap

	// Mouse booleans
	public static boolean mousePressed = false;
	public static boolean mouseDraggin = false;
	
	public static Vector mousePosition = Vector.ZERO; // Mouse position
	
	public void keyPressed(KeyEvent event) { keys.put(event.getKeyCode(), true); } // key pressed event function
	public void keyReleased(KeyEvent event) { keys.put(event.getKeyCode(), false); } // key released event function
	
	public void mousePressed(MouseEvent event) // mouse pressed event function
	{
		mousePressed = true;
		mousePosition.set(event.getX() / Game.getFloat("windowScale"), event.getY() / Game.getFloat("windowScale"));
	}
	
	public void mouseReleased(MouseEvent event) // mouse released event function
	{
		mousePressed = false;
		mouseDraggin = false;
		mousePosition.set(event.getX() / Game.getFloat("windowScale"), event.getY() / Game.getFloat("windowScale"));
	}
	
	public void mouseMoved(MouseEvent event) // mouse move event function
	{
		mousePosition.set(event.getX() / Game.getFloat("windowScale"), event.getY() / Game.getFloat("windowScale"));
	}
	
	public void mouseDragged(MouseEvent event) // mouse drag event function
	{
		mouseDraggin = true;
		mousePosition.set(event.getX() / Game.getFloat("windowScale"), event.getY() / Game.getFloat("windowScale"));
	}

	public static boolean getKey(int keycode) { return keys.get(keycode); } // returns boolean of a key being pressed
	
	public void keyTyped(KeyEvent event) { /*...*/ }
	public void mouseClicked(MouseEvent event) { /*...*/ }
	public void mouseEntered(MouseEvent event) { /*...*/ }
	public void mouseExited(MouseEvent event) { /*...*/ }
}