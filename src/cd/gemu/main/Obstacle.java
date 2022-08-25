package cd.gemu.main;

import java.awt.Color;
import java.awt.image.BufferedImage;

import carrillodev.ae.tool.Entity;
import carrillodev.ae.tool.Vector;

public class Obstacle extends Entity
{
	public static final int OBS_TABLE = 0;
	public static final int OBS_SEAT = 1;
	public static final int OBS_CUSTOMER = 2;
	
	public static Vector TABLE_SIZE = new Vector(35f, 10f);
	public static Vector SEAT_SIZE = new Vector(10f, 10f);
	public static Vector CUSTOMER_SIZE = new Vector(10f, 15f);
	
	public boolean isUsed = false;
	
	private int type;
	
	public boolean destroyed = false;
	
	Obstacle(int type, Vector position)
	{
		this.position = position;
		this.color = Color.blue;
		
		setType(type);
	}
	
	public void setType(int type)
	{
		this.type = type;
		
		switch(type)
		{
			case OBS_CUSTOMER:
				this.size = CUSTOMER_SIZE;
				break;
				
			case OBS_SEAT:
				this.image = GameHandler.RESOURCE_IMAGE_OBS_SEAT;

				switch(GameHandler.randomRange(0, 2))
				{

				case 1: // mirror

					BufferedImage mirroredImage = new BufferedImage(this.image.getWidth(), this.image.getHeight(), image.getType());
					
					for(int i = 0; i < this.image.getWidth(); i++)
					{
						for(int  j = 0; j < this.image.getHeight(); j++)
						{
							mirroredImage.setRGB(this.image.getWidth() - i - 1, j, this.image.getRGB(i, j));
						}
					}
					
					this.image = mirroredImage;
					
					break;
					
				case 2:

					BufferedImage rotatedImage = new BufferedImage(this.image.getHeight(), this.image.getWidth(), image.getType());
					
					for(int i = 0; i < this.image.getWidth(); i++)
					{
						for(int  j = 0; j < this.image.getHeight(); j++)
						{
							rotatedImage.setRGB(j, this.image.getWidth() - 1 - i, this.image.getRGB(i, j));
						}
					}
					
					this.image = rotatedImage;
					
					break;
					
				}
				
				break;
			
			case OBS_TABLE:
				this.image = GameHandler.RESOURCE_IMAGE_OBS_TABLE;
				break;
		}
		
		this.size = new Vector(image.getWidth(), image.getHeight());
	}
	
	public void update()
	{
		if(destroyed)
		{
			horizonalFlingVelocityy -= startingHorizontalFlingVelocity / 6;
				
			this.move(new Vector(verticalFlingVelocity, -horizonalFlingVelocityy));
			
			if(this.position.y > GameHandler.background.size.y)
			{
				this.destroyed = false;
			}
		}
		
		if(isUsed)
		{
			if(this.position.y > GameHandler.background.size.y)
			{
				isUsed = false;
			}
			
			if(this.position.x + this.size.x < 0)
			{
				isUsed = false;
			}
			
			move(new Vector(-Core.game.worldSpeed, 0f));
		}
	}

	public int getType() { return type; }
	
	private float verticalFlingVelocity = 13;
	private float horizonalFlingVelocityy = 14;
	private final float startingHorizontalFlingVelocity = 14;
	
	public void destroy()
	{
		destroyed = true;
	}

	public void reuse(Vector spawn)
	{
		horizonalFlingVelocityy = startingHorizontalFlingVelocity;
		isUsed = true;
		destroyed = false;
		setPoint(spawn);
		move(new Vector(0f, -size.y));
	}
}