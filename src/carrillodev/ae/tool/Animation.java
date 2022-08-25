package carrillodev.ae.tool;

import carrillodev.ae.core.Game;

public class Animation
{
	private Vector[] positionList;
	
	public String name;
	
	private int speed;
	private int currentSpeed = 0;
	private int currentFrame = 0;
	
	public Animation(String _name, int _speed, String _positionList)
	{
		speed = _speed;
		name = _name;
		
		String[] rowsList = _positionList.split("\\{")[1].split("\\}")[0].split(",");
		
		this.positionList = new Vector[rowsList.length];
		
		// {(x:y),....}
		for(int i = 0; i < rowsList.length; i++)
		{
			//Game.log(rowsList[i]);
			int x = Integer.parseInt(rowsList[i].split("\\(")[1].split("\\)")[0].split(":")[0]);
			int y = Integer.parseInt(rowsList[i].split("\\(")[1].split("\\)")[0].split(":")[1]);
			
			positionList[i] = new Vector(x, y);
		}
		
		Game.log("Animation " + '"' + name + '"' + " created @ " + speed + " speed");
	}
	
	Vector getPosition()
	{
		return positionList[currentFrame];
	}
	
	public int getLength()
	{
		return positionList.length;
	}
	
	public int getFrame()
	{
		return currentFrame;
	}
	
	void update()
	{
		if(currentSpeed < speed)
		{
			currentSpeed++;
		}
		else
		{
			currentSpeed = 0;
			
			currentFrame++;
			
			if(currentFrame >= positionList.length)
			{
				currentFrame = 0;
			}
		}
	}
}