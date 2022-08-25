package carrillodev.ae.core;

import java.awt.Color;

public class ApplicationCore
{
	private static Window engineWindow; // Window class
	
	public static void load(String args[]) // program entry point
	{
		engineWindow = new Window(1000, 420, 1.5f, "Taco Hell"); // Create the window
		
		Game.loadResources(); // Load game resources
	}
	
	private static void updateEngine() // updates the engine & game & graphics & input
	{
		/* Reset the screen */
		engineWindow.windowGraphics.getGraphics().setColor(Color.DARK_GRAY); // set the fill color to black
		engineWindow.windowGraphics.fillRect(0, 0, ((int) (engineWindow.getWidth() * engineWindow.getScale())), ((int) (engineWindow.getHeight() * engineWindow.getScale()))); // fill the whole screen with black
		
		/* Update the game */
		Game.update();
		
		/* Update the graphics */
		engineWindow.windowGraphics.destroyGraphics();
	}
	
	public static void _enterLoop() // enters into the game loop
	{
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;
		
		@SuppressWarnings("unused") int ticks = 0;
		@SuppressWarnings("unused") int frame = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		while(true)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			
			boolean shouldRender = false;
			
			while(delta >= 1)
			{
				ticks++;
				delta -= 1;
				shouldRender = true;
			}
			
			try { Thread.sleep(2); }
			catch(Exception e) { e.printStackTrace(); }

			if(shouldRender)
			{
				updateEngine();
				frame++;
			}
			
			if(System.currentTimeMillis() - lastTimer > 1000)
			{
				lastTimer += 1000;
				frame = 0;
				ticks = 0;
			}
		}
	}

	public static Graphics getGraphics() { return engineWindow.windowGraphics; } // returns the applications graphics
	
}