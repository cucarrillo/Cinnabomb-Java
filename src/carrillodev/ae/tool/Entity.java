package carrillodev.ae.tool;

import java.awt.Color;
import java.awt.image.BufferedImage;

import carrillodev.ae.core.Game;

public class Entity
{
	public Vector position; // position of the object in the game
	public Vector cameraOffset; // offset of the object for the camera
	public Vector centre;
	public Vector size; // size of the object
	
	public Spritesheet spriteSheet;
	
	public String tag; // objects name / tag 
	
	public BufferedImage image; // image that the object uses
	
	public Color color;
	
	public float rotation;
	
	public boolean active = true;
	
	public Entity() { carrillodev.ae.core.Game.gameEntities.add(this); } // creates an empty object
	
	public Entity(float positionX, float positionY, float width, float height) // create an object with size and position
	{
		carrillodev.ae.core.Game.gameEntities.add(this); // Add the object to the game entity list
		
		position = new Vector(positionX, positionY); // Set the position
		centre = position;
		size = new Vector(width, height); // Set the size
	}
	
	public void engineUpdate() // updates the object
	{
		if(active)
		{
			
			if(spriteSheet != null)
			{
				spriteSheet.update();
				image = spriteSheet.getImage();
			}
			
			if(image == null && color != null)
			{
				image = new BufferedImage((int) this.size.x, (int) this.size.y, BufferedImage.TYPE_INT_RGB);
				
				for(int i = 0; i < image.getWidth(); i++)
				{
					for(int j= 0; j < image.getHeight(); j++)
					{
						image.setRGB(i, j, color.getRGB());
					}
				}
			}
			
			Vector newPosition = Vector.add(position, cameraOffset);
			
			// draws the object with the according position (if camera is locked then draw the centre vector, if not draw the new position vector)
			if(!Game.camera.testLockedObject(this)) { carrillodev.ae.core.ApplicationCore.getGraphics().drawImage(image, (int) newPosition.x, (int) newPosition.y, (int) size.x, (int) size.y); }
			else { carrillodev.ae.core.ApplicationCore.getGraphics().drawImage(image, (int) centre.x, (int) centre.y, (int) size.x, (int) size.y); }
			update();
		}
	}
	
	public void update() { /*...*/ } // custom update
	
	public void setPoint(Vector point) // sets the point
	{
		position = point; // add the direction
		
		if(Game.camera.testLockedObject(this)) { Game.camera.lockOnObject(this); } // test if being followed by camera (if so then add the direction)
	}
	
	public void move(Vector direction) // moves the entity
	{
		position = Vector.add(position, direction); // add the direction
		
		if(Game.camera.testLockedObject(this)) { Game.camera.position = Vector.add(Game.camera.position, direction); } // test if being followed by camera (if so then add the direction)
	}
	
	public void setImage(BufferedImage image) { this.image = image; } // sets the image

	public void setSpriteSheet(Spritesheet spriteSheet) { this.spriteSheet = spriteSheet; }
	
	public static boolean testCollision(Entity entity1, Entity entity2)
	{
		return (entity1.position.x < entity2.position.x + entity2.size.x &&
				   entity1.position.x + entity1.size.x > entity2.position.x &&
				   entity1.position.y < entity2.position.y + entity2.size.y &&
				   entity1.position.y + entity1.size.y > entity2.position.y);
	}
}