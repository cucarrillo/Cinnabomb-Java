package carrillodev.ae.UI;

import java.awt.image.BufferedImage;

import carrillodev.ae.core.Game;
import carrillodev.ae.core.Input;
import carrillodev.ae.tool.Image;
import carrillodev.ae.tool.Vector;

public class Button extends UI
{
	// Button Variables
	public Vector size;
	private int style;
	
	// Button Images
	private BufferedImage imageIdle;
	private BufferedImage imagePressed;
	private BufferedImage imageHover;
	private BufferedImage imageCurrent;
	
	public boolean isPressed;
	
	public Button(float positionX, float positionY, String style)
	{
		position = new Vector(positionX, positionY);
		
		this.style = 1;
		
		// idle='',press='',hover=''
		
		imageIdle = Image.readImage(style.split("idle='")[1].split("'")[0]);
		imagePressed = Image.readImage(style.split("press='")[1].split("'")[0]);
		imageHover = Image.readImage(style.split("hover='")[1].split("'")[0]);

		size = new Vector(imageIdle.getWidth(), imageIdle.getHeight());
	}
	
	public Button(float positionX, float positionY, int width, int height, String style)
	{
		position = new Vector(positionX, positionY);
		size = new Vector(width, height);
		
		this.style = 1;
		
		// idle='',press='',hover=''
		
		Game.log(style.split("idle='")[1].split("'")[0]);
		
		imageIdle = Image.readImage(style.split("idle='")[1].split("'")[0]);
		imagePressed = Image.readImage(style.split("press='")[1].split("'")[0]);
		imageHover = Image.readImage(style.split("hover='")[1].split("'")[0]);
	}
	
	public void update()
	{
		super.update();
		
		style = 1; // Reset the style
		
		// Check for interaction
		if(Input.mousePosition.x > position.x &&
				Input.mousePosition.x < position.x + size.x &&
				Input.mousePosition.y > position.y &&
				Input.mousePosition.y < position.y + size.y)
		{
			if(Input.mousePressed) { style = 2; }
			else { style = 3; }
		}
		
		switch(style) // Check to see what image you will use
		{
			case 1:
				imageCurrent = imageIdle;
				break;
			case 2:
				isPressed=true;
				imageCurrent = imagePressed;
				break;
			case 3:
				imageCurrent = imageHover;
				break;
		}
		
		carrillodev.ae.core.ApplicationCore.getGraphics().drawImage(imageCurrent, (int) position.x, (int) position.y, (int) size.x, (int) size.y);
	}

	public void move(Vector direction)
	{
		position = Vector.add(position, direction); // add the direction
	}
}