package carrillodev.ae.core;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import carrillodev.ae.tool.Variables;
import carrillodev.ae.tool.Vector;
import carrillodev.ae.UI.UI;
import carrillodev.ae.tool.ABIO;
import carrillodev.ae.tool.Entity;

public class Game
{
	// Class variables
	public static HashMap<String, String> globalVariables = new HashMap<String, String>(); // variables for game use
	public static ArrayList<Entity> gameEntities = new ArrayList<Entity>(); // Game entites list
	public static ArrayList<UI> gameUI = new ArrayList<UI>(); // Game UI list
	
	public static Camera camera; // game camera
	
	public static BufferedImage Aoba; // engine logo
	
	public static boolean running = true;
	
	
	public static void update() // updates the game & draws objects
	{
		for(Entity entity : gameEntities)
		{
			if(entity.active)
			{
				entity.cameraOffset = Vector.neg(camera.position);
				
				entity.engineUpdate(); // update the entity
			}
		}
		
		for(UI ui : gameUI)
		{
			if(ui.active)
			{
				ui.engineUpdate();
			}
		}
	}
	
	public static void loadResources() // loads game resources
	{
		camera = new Camera(); // create the camera
		
		for(String line : ABIO.readFile("source/Keys.aoba")) // import key inputs
		{
			line = line.trim();
			
			String lineCode = line.split(":")[1]; // get the key code
			String lineName = line.split(":")[0]; // get the key name
			
			for(String key : Variables.KEYS.split(","))
			{
				key = key.trim();
				
				String keyCode = key.split(":")[1]; // get the key code
				String keyName = key.split(":")[0]; // get the key name
				
				if(keyName.equalsIgnoreCase(lineCode))
				{
					setVariable(lineName, keyCode); // set the keycode and keyname to the hash map (KEYNAME, KEYCODE)
					
					Input.keys.put(Integer.parseInt(keyCode), false); // put the key in the input list
					
					log(keyCode + ' ' + '"' + lineName + '"' + " set to false in Input.keys"); // log the input
				}
			}
		}
	}
	
	public static Entity getEntity(String tag) // returns the desired entity with the correct tag
	{
		for(Entity entity : gameEntities) // filter through all entites
		{
			if(entity.tag == tag) { return entity; } // if that tag matches the one provided then return it
		}
		
		return null; // return nothing if the entity was not found
	}
	
	public static void log(String args) { System.out.println(LocalDateTime.now() + "]: " + args); } // print strings
	public static void log(int args) { System.out.println(LocalDateTime.now() + "]: " + args); }
	public static int getInt(String path) { return Integer.parseInt(globalVariables.get(path)); } // return variable integer value
	public static void setVariable(String path, String var) { globalVariables.put(path, var); } // set variable name and value

	public static void log(Object args) { System.out.println(LocalDateTime.now() + "]: " + args.toString()); }

	public static float getFloat(String path) { return Float.parseFloat(globalVariables.get(path)); }
}