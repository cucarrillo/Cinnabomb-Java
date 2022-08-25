package carrillodev.ae.UI;

import carrillodev.ae.tool.Vector;

public class UI
{
	public Vector position;
	
	public boolean active = true;
	
	public UI() { carrillodev.ae.core.Game.gameUI.add(this); }
	
	public void updateGraphics()
	{
		
	}
	
	public void engineUpdate()
	{
		if(active) { update(); }
	}
	
	public void update()
	{
		
	}
}