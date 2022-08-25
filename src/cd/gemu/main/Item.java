package cd.gemu.main;

import carrillodev.ae.tool.Entity;
import carrillodev.ae.tool.Vector;

public class Item extends Entity
{
	public int id;
	
	public static final int ITEM_NOTHING = -1;
	public static final int ITEM_MOP = 0;
	public static final int ITEM_CINNABOMB = 1;
	
	public boolean spawned = false;
	
	public Player owner = null;
	
	Item()
	{
		this.position = new Vector(0f, 0f);
		this.size = new Vector(1f, 1f);
	}
	
	public void spawn(int type, Vector position)
	{
		this.id = type;
		this.active = true;
		spawned = true;
		
		switch(id)
		{
			case ITEM_MOP: this.image = GameHandler.RESOURCE_IMAGE_ITEM_MOP; break; 
			case ITEM_CINNABOMB: this.image = GameHandler.RESOURCE_IMAGE_ITEM_CINNABOMB; break;
		}
		
		this.size = new Vector(this.image.getWidth(), this.image.getHeight());
		this.position = Vector.sub(position, new Vector(0f, this.size.y));
	}
	
	public void use()
	{
		switch(id)
		{
			case ITEM_MOP:
				owner.powerUp(ITEM_MOP);
				break;
			case ITEM_CINNABOMB:
				Core.game.destoryObstaclesOnScreen();
				break;
		}
		
		id = ITEM_NOTHING;
		active = false;
		spawned = false;
		owner = null;
	}
	
	public void update()
	{
		if(Entity.testCollision(GameHandler.player, this))
		{
			GameHandler.player.item = this;
			owner = GameHandler.player;
		}
		
		if(owner != null)
		{
			if(this.position.x > 0) { move(new Vector(-50f, 0f)); }
			if(this.position.y > 0) { move(new Vector(0f, -50f)); }
			if(this.position.x < 0) { setPoint(new Vector(0f, this.position.y)); }
			if(this.position.y < 0) { setPoint(new Vector(this.position.x, 0f)); }
		}
		
		if(spawned && owner == null)
		{
			move(new Vector(-Core.game.worldSpeed, 0f));
		}
	}
}