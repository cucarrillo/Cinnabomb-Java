package carrillodev.ae.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Graphics
{
	// Graphics class objects
	private BufferStrategy bufferStrategy;
	private Graphics2D graphics;
	private BufferedImage doubleBuffer;
	
	boolean create(Canvas windowCanvas) // creates graphics & returns if creation was successful
	{
		doubleBuffer = new BufferedImage(Game.getInt("windowWidth"), Game.getInt("windowHeight"), BufferedImage.TYPE_INT_ARGB); // create the double buffer
		
		bufferStrategy = windowCanvas.getBufferStrategy(); // get the buffer stratgey
		
		if(windowCanvas.getBufferStrategy() == null) // test if buffer strategy is null
		{
			windowCanvas.createBufferStrategy(2); // create the buffer strategy
		
			return false; //return false to the function
		}
		
		graphics = (Graphics2D) bufferStrategy.getDrawGraphics(); // get the 2d draw graphics
		
		return true; // return true to the function
	}
	
	void destroyGraphics() // destroys the graphics so they can be shown
	{
		graphics = (Graphics2D) bufferStrategy.getDrawGraphics(); // get the graphics
		
		graphics.drawImage( // draw the double buffer image
							doubleBuffer, 
							0, 
							0,
							((int) (Game.getInt("windowWidth")*Game.getFloat("windowScale"))),
							((int) (Game.getInt("windowHeight")*Game.getFloat("windowScale"))),
							null);
		graphics.dispose(); // dispose of the graphics
		bufferStrategy.show(); // show the buffer strategy
	}

	Graphics2D getGraphics() { return (Graphics2D) doubleBuffer.getGraphics(); } // returns graphics
	
	public void setColor(Color color) { getGraphics().setColor(color); } // set color
	public void drawText(String text, int positionX, int positionY) { doubleBuffer.getGraphics().drawString(text, positionX, positionY); } // draw string text
	public void drawText(int text, int positionX, int positionY) { doubleBuffer.getGraphics().drawString(Integer.toString(text), positionX, positionY); } // draw integer text
	public void drawText(float text, int positionX, int positionY) { doubleBuffer.getGraphics().drawString(Float.toString(text), positionX, positionY); } // draw float text
	public void drawImage(BufferedImage image, int positionX, int positionY) { doubleBuffer.getGraphics().drawImage(image, positionX, positionY, null); } // draw image w/o size
	public void drawImage(BufferedImage image, int positionX, int positionY, int width, int height) { doubleBuffer.getGraphics().drawImage(image, positionX, positionY, width, height, null); } // draw image w/ size
	public void fillRect(int positionX, int positionY, int width, int height) { doubleBuffer.getGraphics().setColor(Color.RED); doubleBuffer.getGraphics().fillRect(positionX, positionY, width, height); } // draw a filled rect
	public void drawImage(BufferedImage image, int positionX, int positionY, int width, int height, float rotation)
	{
		AffineTransform transform = AffineTransform.getTranslateInstance(positionX, positionY);
		transform.rotate(Math.toRadians(rotation));
	
		getGraphics().drawImage(image, transform, null);
	}
	
	public void fillRect(int positionX, int positionY, int width, int height, float rotation)
	{
		Rectangle tangle = new Rectangle(0, 0, width, height);
		getGraphics().translate(positionX + width / 2, positionY + height / 2);
		getGraphics().rotate((rotation));
		getGraphics().draw(tangle);
	}
	
	
	public void setTextStyle(float size) { getGraphics().setFont(new Font("Courier", Font.PLAIN, (int) size)); }
}