package cd.gemu.main;

import carrillodev.ae.core.Game;
import carrillodev.ae.tool.Entity;
import carrillodev.ae.tool.Spritesheet;
import carrillodev.ae.tool.Vector;

public class Lean extends Entity
{
	private int leanState;

	private float moveVelocity = 10f;
	
	private float currentJumpVelocity = 0f;
	private float currentFallVelocity = 0f;
	
	private final float jumpVelocity = 5f;
	private final float fallVelocity = 6f;
	
	public final int STATE_NOTHING = 0;
	public final int STATE_JUMPING = 1;
	public final int STATE_FALLING = 2;

	private String[] spriteSheetAnimations = { "name:running,speed:2,animation:{(0:0),(1:0),(2:0),(3:0),(4:0),(5:0),(6:0)}" };
	
	Lean()
	{
		restart();
	}


	public void restart()
	{
		this.spriteSheet = new Spritesheet("source/sprites/leen_spritesheet.png", 115, 137, 1, 1, spriteSheetAnimations);
		this.size = Vector.mul(new Vector(115,137), 1.5f);
		this.position = new Vector(GameHandler.background.size.x / 8 - (this.size.x / 2), GameHandler.ground.position.y - this.size.y);
		
		leanState = STATE_FALLING;
	}
	
	public void update()
	{
		updateMovement();
		updateCollison();
	}
	
	public void updateCollison()
	{
		Entity ground = Game.getEntity("GROUND");

		if(position.y + size.y >= ground.position.y)
		{
			stopFalling();
			setPoint(new Vector(position.x, ground.position.y - size.y));
		}
		
		for(Obstacle obs : GameHandler.obsList)
		{
			if(Entity.testCollision(this, obs) && obs.isUsed)
			{
				obs.isUsed = false;
				
				obs.destroy();
			}
		}
	}
	
	public void updateMovement()
	{
		if(GameHandler.player.power == Item.ITEM_MOP)
		{
			this.move(new Vector(-Core.game.worldSpeed, 0f));
		}

		if(this.position.x < GameHandler.background.size.x / 8 - (this.size.x / 2))
		{
			this.move(new Vector(moveVelocity, 0f));
		}
		
		switch(leanState)
		{
			case STATE_FALLING:
				
				move(new Vector(0f, currentFallVelocity));
				
				currentFallVelocity += fallVelocity / 10;
				
				if(currentFallVelocity > fallVelocity)
				{
					currentFallVelocity = fallVelocity;
				}
				
				break;
				
			case STATE_JUMPING:
				
				if(currentJumpVelocity > 0)
				{
					move(new Vector(0f, -currentJumpVelocity));
					
					currentJumpVelocity -= jumpVelocity / 15;
					
					if(currentJumpVelocity < 0)
					{
						currentJumpVelocity = 0;
					}
				}
				else
				{
					leanState = STATE_FALLING;
					currentJumpVelocity = jumpVelocity;
				}
				
				break;
		}
	}
	
	void stopFalling()
	{
		leanState = STATE_NOTHING;
	}
}