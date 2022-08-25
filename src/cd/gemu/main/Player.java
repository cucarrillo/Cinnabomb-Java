package cd.gemu.main;

import carrillodev.ae.core.ApplicationCore;
import carrillodev.ae.core.Game;
import carrillodev.ae.core.Input;
import carrillodev.ae.tool.Audio;
import carrillodev.ae.tool.Entity;
import carrillodev.ae.tool.Spritesheet;
import carrillodev.ae.tool.Vector;

public class Player extends Entity
{
	private int playerState;
	
	private float moveVelocity = 1f;
	
	private float currentJumpVelocity = 0f;
	private float currentFallVelocity = 0f;
	
	private final float jumpVelocity = 20f;
	private final float fallVelocity = 15f;
	
	public final int STATE_NOTHING = 0;
	public final int STATE_JUMPING = 1;
	public final int STATE_FALLING = 2;
	
	private boolean collided = false;

	boolean alive = true;
	
	private String[] runningSpriteSheetAnimation = { "name:running,speed:2,animation:{(0:0),(1:0),(2:0),(3:0),(4:0)}" };
	private String[] mopSpriteSheetAnimation = { "name:mop,speed:1,animation:{(0:0),(1:0)}" };
	
	private Spritesheet runningSpriteSheet = new Spritesheet("source/sprites/player_spritesheet.png", 137, 114, 1, 1, runningSpriteSheetAnimation);
	private Spritesheet mopSpriteSheet = new Spritesheet("source/sprites/player_mop_spritesheet.png", 142, 167, 1, 1, mopSpriteSheetAnimation);
	
	private Audio soundJanitor;

	private int soundCooldown = 0;
	
	private int distance = 0;
	
	public Item item;

	int power = -1;
	
	private float oldSpeed = 0;
	
	private int powerDuration = 100;
	
	Player()
	{
		restart();
	}
	
	public void restart()
	{
		this.spriteSheet = runningSpriteSheet;
		this.size = new Vector(137, 114);
		this.position = new Vector(GameHandler.background.size.x / 2 - (this.size.x / 2), GameHandler.ground.position.y - this.size.y);
		
		soundJanitor = new Audio("source/sounds/sound_janitor1.wav");
		
		playerState = STATE_FALLING;
		
		currentJumpVelocity = jumpVelocity;
	}
	
	public void update()
	{
		if(alive)
		{
			this.size = new Vector(this.image.getWidth(), this.image.getHeight());
			
			if(power > -1 && powerDuration > 0)
			{
				powerDuration--;
				if(oldSpeed == 0) { oldSpeed = Core.game.worldSpeed; }
				Core.game.worldSpeed = 50;
				this.spriteSheet = mopSpriteSheet;
			}
			else
			{
				if(oldSpeed > 0)
				{
					power = -1;
					Core.game.worldSpeed = oldSpeed;
					oldSpeed = 0;
					powerDuration = 100;
					this.spriteSheet = runningSpriteSheet;
					this.size = new Vector(137f, 114f);
					playerState = STATE_FALLING;
				}
			}
			if(soundJanitor.getCurrentFrame() >= soundJanitor.getLength())
			{
				soundJanitor.stop();
			}
			
			if(soundJanitor.getCurrentFrame() == 0)
			{
				if(GameHandler.randomRange(0, 25) == 7 && soundCooldown == 0)
				{
					soundJanitor.play(); soundCooldown = 200;
				}
				else
				{ 
					if(soundCooldown > 0) { soundCooldown--; }
				}
			}
			
			distance += Core.game.worldSpeed / 2;
			
			ApplicationCore.getGraphics().drawText(distance, 0, 12);
			
			updateInput();
			updateMovement();
			updateCollison();
		}
	}
	
	public void updateInput()
	{
		if(Input.getKey(Game.getInt("use")))
		{
			if(item != null && item.owner != null)
			{
				item.use();
			}
		}
		
		if(Input.getKey(Game.getInt("jump")))
		{
			if((currentJumpVelocity == jumpVelocity))
			{
				playerState = STATE_JUMPING;
			}
		}
	}
	
	public void updateCollison()
	{
		Entity ground = Game.getEntity("GROUND");

		collided = false;
		
		if(position.y + size.y >= ground.position.y)
		{
			stopFalling();
			setPoint(new Vector(position.x, ground.position.y - size.y));
		}
		
		boolean canMoveForward = true;
		
		if(Entity.testCollision(this, GameHandler.lean))
		{
			
			if(Core.game.topScore < distance)
			{
				Game.log("You beat the hiscore! Distance: " + distance);
			}
			else
			{
				Game.log("Your score: " + distance + ". Score to beat: " + Core.game.topScore);
			}
			
			Core.game.topScore = distance;
			distance = 0;
			kill();
			soundJanitor.stop();
			Core.game.endRun();
		}
		
		for(Obstacle obs : GameHandler.obsList)
		{
			if(Entity.testCollision(this, obs) && obs.isUsed)
			{
				if(position.y <= obs.position.y + (obs.size.y / 4) && position.x + size.x > obs.position.x + (obs.size.x / 4) && position.x < obs.position.x - (obs.size.x / 4) + obs.size.x)
				{
					stopFalling();
					setPoint(new Vector(position.x, obs.position.y - size.y));
					collided = true;
				}
				
				if(position.x + size.x <= obs.position.x + (obs.size.x / 4))
				{
					setPoint(new Vector(obs.position.x - size.x, position.y));
					canMoveForward = false;
				}
			}
		}
		
		if(canMoveForward)
		{
			if(this.position.x <= (GameHandler.background.size.x / 2) - (this.size.y / 2))
			{
				move(new Vector(moveVelocity, 0f));
			}
		}
		
		if(collided)
		{
			playerState = STATE_FALLING;
			currentJumpVelocity = jumpVelocity;
		}
	}
	
	private void kill() { alive  = false; }

	public void updateMovement()
	{
		switch(playerState)
		{
		
		case STATE_NOTHING:

			currentJumpVelocity = jumpVelocity;
			
			break;
			
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
					playerState = STATE_FALLING;
				}
				
				break;
		}
	}
	
	void powerUp(int type)
	{
		this.power = type;
	}
	
	void stopFalling()
	{
		playerState = STATE_NOTHING;
	}
}