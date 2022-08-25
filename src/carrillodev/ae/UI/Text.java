package carrillodev.ae.UI;

import java.awt.Color;

import carrillodev.ae.tool.Vector;

public class Text extends UI
{
	public String text;
	public Color color;
	public float size;
	
	public Text(String text, float positionX, float positionY)
	{
		this.text = text;
		this.position = new Vector(positionX, positionY);
	}
	
	public Text(String text, float positionX, float positionY, float size)
	{
		this.text = text;
		this.position = new Vector(positionX, positionY);
		this.size = size;
	}
	
	public Text(String text, float positionX, float positionY, float size, Color color)
	{
		this.text = text;
		this.position = new Vector(positionX, positionY);
		this.size = size;
		this.color = color;
	}
	
	public void engineUpdate()
	{
		update();

		carrillodev.ae.core.ApplicationCore.getGraphics().setColor(color);
		carrillodev.ae.core.ApplicationCore.getGraphics().setTextStyle(size);
		carrillodev.ae.core.ApplicationCore.getGraphics().drawText(text, (int) position.x, (int) position.y);
	}
	
	public void update()
	{
		
	}
}