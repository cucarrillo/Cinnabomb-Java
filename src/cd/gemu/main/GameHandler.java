package cd.gemu.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import carrillodev.ae.UI.Button;
import carrillodev.ae.core.Game;
import carrillodev.ae.tool.Audio;
import carrillodev.ae.tool.Entity;
import carrillodev.ae.tool.Image;
import carrillodev.ae.tool.Spritesheet;
import carrillodev.ae.tool.Vector;

public class GameHandler extends Entity
{
	public static ArrayList<Obstacle> obsList = new ArrayList<Obstacle>();

	public float worldSpeed = 5f;
	
	private float spawnRate = 1;
	private float rate = .01f;
	
	private Vector obsSpawn;
	
	static Entity firstBackground;
	static Entity secondBackground;
	
	static Entity firstPoster;
	static Entity secondPoster;
	static Entity thirdPoster;
	
	static Entity background;
	static Entity ground;
	static Player player;
	static Lean lean;
	
	static Item item;
	
	static Button playButton;
	static Button replay;
	static Entity title;
	
	public int gameState = -1;
	
	static int currentAudioLoop = 0;
	
	static Audio musicRunning;
	static Audio deathSound;
	
	public boolean runStopped = false;
	
	public float spawnerCoolDown = 300;
	public int currentSpawnerCoolDown = (int) spawnerCoolDown;

	public int topScore = 0;
	
	static Entity deathScreen;
	
	static Vector posterBounds[][] = 
		{
			{ new Vector(0f, 0f), new Vector(1490f, 245f) },
			{ new Vector(0f, 0f), new Vector(1490, 185) },
			{ new Vector(0f, 0f), new Vector(1490, 360) },
			{ new Vector(0f, 0f), new Vector(1490, 185) },
			{ new Vector(770f, 0f), new Vector(720, 340) }
		};
	
	public static final int STATE_MENU = 0;
	public static final int STATE_PLAYING = 1;
	public static final int STATE_PAUSED = 2;
	public static final int STATE_LOST = 3;
	
	public static BufferedImage RESOURCE_IMAGE_OBS_TABLE;
	public static BufferedImage RESOURCE_IMAGE_OBS_SEAT;
	public static BufferedImage RESOURCE_IMAGE_BACKGROUND_1;
	public static BufferedImage RESOURCE_IMAGE_BACKGROUND_2;
	public static BufferedImage RESOURCE_IMAGE_BACKGROUND_3;
	public static BufferedImage RESOURCE_IMAGE_BACKGROUND_4;
	public static BufferedImage RESOURCE_IMAGE_BACKGROUND_5;
	public static BufferedImage RESOURCE_IMAGE_POSTER_1;
	public static BufferedImage RESOURCE_IMAGE_POSTER_2;
	public static BufferedImage RESOURCE_IMAGE_POSTER_3;
	public static BufferedImage RESOURCE_IMAGE_POSTER_4;
	public static BufferedImage RESOURCE_IMAGE_POSTER_5;
	public static BufferedImage RESOURCE_IMAGE_POSTER_6;
	public static BufferedImage RESOURCE_IMAGE_TITLE;
	public static BufferedImage RESOURCE_IMAGE_ITEM_CINNABOMB;
	public static BufferedImage RESOURCE_IMAGE_ITEM_MOP;
	
	public GameHandler()
	{
		this.position = new Vector(0f, 0f);
		this.size = new Vector(1f, 1f);
		
		background = new Entity(0, 0, Game.getInt("windowWidth"), Game.getInt("windowHeight"));
		background.color = Color.GRAY;
		
		loadGame();
	}
	
	public void loadMenu()
	{
		title = new Entity();
		title.setImage(RESOURCE_IMAGE_TITLE);
		title.size = Vector.mul(new Vector(title.image.getWidth(), title.image.getHeight()), 2);
		title.position = new Vector(background.size.x / 2 - (title.size.x / 2), 100);
		
		playButton = new Button(
								background.size.x / 2,
								(background.size.y / 2) + 50,
								"idle='source/ui/button_play_idle.png',press='source/ui/button_play_pressed.png',hover='source/ui/button_play_hover.png'"
								);

		
		playButton.move(new Vector(-playButton.size.x / 2, -playButton.size.y / 2));
	}
	
	public void loadGame()
	{
		musicRunning = new Audio("source/music/music_running.wav");
		deathSound = new Audio("source/sounds/sound_lean1.wav");
		
		firstBackground = new Entity();
		firstBackground.image = RESOURCE_IMAGE_BACKGROUND_3;
		firstBackground.position = new Vector(0f, 0f);
		firstBackground.size = new Vector(firstBackground.image.getWidth(), firstBackground.image.getHeight());
		
		secondBackground = new Entity();
		secondBackground.image = RESOURCE_IMAGE_BACKGROUND_1;
		secondBackground.position = new Vector(firstBackground.size.x, 0f);
		secondBackground.size = new Vector(firstBackground.image.getWidth(), firstBackground.image.getHeight());
		
		firstPoster = new Entity();
		firstPoster.position = new Vector(-1f, 0-1f);
		firstPoster.size = new Vector(1f, 1f);
		
		secondPoster = new Entity();
		secondPoster.position = new Vector(-1f, 0-1f);
		secondPoster.size = new Vector(1f, 1f);

		thirdPoster= new Entity();
		thirdPoster.position = new Vector(-1f, 0-1f);
		thirdPoster.size = new Vector(1f, 1f);
		
		String[] deathScreenAnimation = { "name:death,speed:5,animation:{(0:0),(1:0),(1:0),(2:0),(3:0),(4:0),(5:0),(6:0),(7:0),(8:0),(10:0),}" };
		
		deathScreen = new Entity();
		deathScreen.setSpriteSheet(new Spritesheet("source/sprites/scene_death_spritesheet.png", 624, 416, 1, 1, deathScreenAnimation));
		deathScreen.size = new Vector(624, 416);
		deathScreen.position = new Vector(background.size.x / 2 - (deathScreen.size.x / 2), background.size.y / 2 - (deathScreen.size.y / 2));
		deathScreen.active = false;
		
		ground = new Entity(0, background.size.y - 30, background.size.x, 10);
		ground.tag = "GROUND";

		item = new Item();
		
		player = new Player();
		lean = new Lean();
		
		player.active = false;
		lean.active = false;
		firstBackground.active = true;
		secondBackground.active = true;
		firstPoster.active = false;
		secondPoster.active = false;
		thirdPoster.active = false;
		ground.active = false;
		
		obsSpawn = new Vector(background.size.x, ground.position.y);
		
		replay = new Button(
				background.size.x / 2,
				(background.size.y / 2) + 50,
				"idle='source/ui/button_replay_idle.png',press='source/ui/button_replay_pressed.png',hover='source/ui/button_replay_hover.png'"
				);
		
		replay.position = new Vector(deathScreen.size.x, background.size.y - replay.size.y - 20);
		
		replay.active = false;
		
		for(int i = 0; i < 2; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				Obstacle obstacle = new Obstacle(i, obsSpawn);
				
				obstacle.active = false;
				
				obsList.add(obstacle);
			}
		}
	}
	
	public void startGame()
	{
		gameState = STATE_PLAYING;
		
		replay.active = false;
		
		player.alive = true;
		
		playButton.active = false;
		title.active = false;
		
		player.active = true;
		lean.active = true;
		firstBackground.active = true;
		secondBackground.active = true;
		firstPoster.active = true;
		secondPoster.active = true;
		thirdPoster.active = true;
		ground.active = true;
		
		player.setPoint(new Vector(0f, player.position.y));
		lean.move(new Vector(-3000f, player.position.y));
		
		for(Obstacle obs : obsList)
		{
			obs.active = true;
			obs.isUsed = false;
			obs.setPoint(obsSpawn);
		}
		
		musicRunning.playLoop();
	}
	
	public void stopGame()
	{
		gameState = STATE_PLAYING;
		
		playButton.active = false;
		title.active = false;
		
		player.active = false;
		lean.active = false;
		firstBackground.active = false;
		secondBackground.active = false;
		firstPoster.active = false;
		secondPoster.active = false;
		thirdPoster.active = false;
		ground.active = false;
		
		for(Obstacle obs : obsList)
		{
			obs.active = false;
		}
		
		musicRunning.stop();
	}
	
	public void update()
	{
		if(gameState < 0)
		{
			gameState = STATE_MENU;
		}
		
		switch(gameState)
		{
			case STATE_MENU:
				
				if(playButton.isPressed)
				{
					startGame();
				}
				
				break;
			
			case STATE_PLAYING:
				
				if(!runStopped)
				{
					updateBackground();
					updateSpawner();
				}
				else
				{
					worldSpeed = 0;
					spawnRate = 0;
					
					if(replay.isPressed)
					{
						String[] deathScreenAnimation = { "name:death,speed:5,animation:{(0:0),(1:0),(1:0),(2:0),(3:0),(4:0),(5:0),(6:0),(7:0),(8:0),(10:0),}" };
						deathScreen.setSpriteSheet(new Spritesheet("source/sprites/scene_death_spritesheet.png", 624, 416, 1, 1, deathScreenAnimation));
						restartGame();
						replay.isPressed = false;
					}
					
					if(deathScreen.spriteSheet.getCurrentFrame() >= deathScreen.spriteSheet.getCurrentFrameLength() - 1 && deathScreen.spriteSheet.paused == false)
					{
						deathScreen.spriteSheet.paused = true;
						deathSound.play();
					}
				}
				break;
		}
	}
	
	private void restartGame()
	{
		firstBackground.image = RESOURCE_IMAGE_BACKGROUND_3;
		firstBackground.position = new Vector(0f, 0f);
		firstBackground.size = new Vector(firstBackground.image.getWidth(), firstBackground.image.getHeight());
		
		secondBackground.image = RESOURCE_IMAGE_BACKGROUND_1;
		secondBackground.position = new Vector(firstBackground.size.x, 0f);
		secondBackground.size = new Vector(firstBackground.image.getWidth(), firstBackground.image.getHeight());
		
		deathScreen.active = false;
		deathScreen.spriteSheet.paused = false;
		
		player.restart();
		lean.restart();

		worldSpeed = 5f;
		
		spawnRate = 1;
		rate = .01f;
		
		gameState = STATE_PLAYING;
		
		deathSound.stop();
		
		runStopped = false;
		
		startGame();
	}

	public void updateSpawner()
	{
		if(currentSpawnerCoolDown <= 0)
		{
			if(randomRange(0, 100) < spawnRate)
			{
				currentSpawnerCoolDown = (int) spawnerCoolDown;
				int d = randomRange(1, 4);
				
				switch(d)
				{
				case 0:
					spawnObstacle(randomRange(2, 3));
					break;
				case 1:
					spawnObstacle(Obstacle.OBS_TABLE, new Vector(0f, 0f));
					spawnObstacle(Obstacle.OBS_SEAT, new Vector(RESOURCE_IMAGE_OBS_TABLE.getWidth() / 2, -RESOURCE_IMAGE_OBS_TABLE.getHeight()));
					break;
				case 2:
					spawnObstacle(Obstacle.OBS_TABLE, new Vector(0f, 0f));
					spawnObstacle(Obstacle.OBS_TABLE, new Vector(RESOURCE_IMAGE_OBS_TABLE.getWidth(), 0f));
					spawnObstacle(Obstacle.OBS_TABLE, new Vector(RESOURCE_IMAGE_OBS_TABLE.getWidth() - (RESOURCE_IMAGE_OBS_TABLE.getWidth() / 2), -RESOURCE_IMAGE_OBS_TABLE.getHeight()));
					break;
				case 3:
					spawnObstacle(Obstacle.OBS_SEAT, new Vector(0f, 0f));
					spawnObstacle(Obstacle.OBS_TABLE, new Vector(RESOURCE_IMAGE_OBS_SEAT.getWidth() + 100, 0f));
					spawnObstacle(Obstacle.OBS_SEAT, new Vector(RESOURCE_IMAGE_OBS_SEAT.getWidth() + RESOURCE_IMAGE_OBS_TABLE.getWidth() + 100 + 100, 0f));
					break;
					
				case 4:
					if(item.owner == null && item.spawned == false);
					{
						item.spawn(randomRange(0, 1), obsSpawn);
					}
				}
			}
		}
		else
		{
			currentSpawnerCoolDown--;
		}
		
		if(spawnRate < 50) { spawnRate += rate; }
		
		worldSpeed += .001;
		spawnerCoolDown -= .05;
	}
	
	public void updateBackground()
	{
		firstBackground.move(new Vector(-worldSpeed, 0f));
		secondBackground.move(new Vector(-worldSpeed, 0f));
		
		if(firstPoster.position.x + firstPoster.size.x > 0) { firstPoster.move(new Vector(-worldSpeed, 0f)); }
		if(secondPoster.position.x + secondPoster.size.x > 0) { secondPoster.move(new Vector(-worldSpeed, 0f)); }
		if(thirdPoster.position.x + thirdPoster.size.x > 0) { thirdPoster.move(new Vector(-worldSpeed, 0f)); }
		
		if(firstBackground.position.x + firstBackground.size.x <= 0) { resetBackground(firstBackground, secondBackground); }
		if(secondBackground.position.x + secondBackground.size.x <= 0) { resetBackground(secondBackground, firstBackground); }
	}
	
	public void resetBackground(Entity background, Entity refrenceBackground)
	{
		background.setPoint(new Vector(refrenceBackground.position.x + refrenceBackground.size.x - 2, 0f));
		
		int backgroundType = randomRange(1, 5);
		
		switch(randomRange(1, 3))
		{
			case 1:
				if(firstPoster.position.x + firstPoster.size.x <= 0) { spawnPoster(firstPoster, background, backgroundType); }
				break;
			case 2:
				if(firstPoster.position.x + firstPoster.size.x <= 0) { spawnPoster(firstPoster, background, backgroundType); }
				if(secondPoster.position.x + secondPoster.size.x <= 0) { spawnPoster(secondPoster, background, backgroundType); }
				break;
			case 3:
				if(firstPoster.position.x + firstPoster.size.x <= 0) { spawnPoster(firstPoster, background, backgroundType); }
				if(secondPoster.position.x + secondPoster.size.x <= 0) { spawnPoster(secondPoster, background, backgroundType); }
				if(thirdPoster.position.x + thirdPoster.size.x <= 0) { spawnPoster(thirdPoster, background, backgroundType); }
				break;
		}
		
		switch(backgroundType)
		{
			case 1: background.image = RESOURCE_IMAGE_BACKGROUND_1; break;
			case 2: background.image = RESOURCE_IMAGE_BACKGROUND_2; break;
			case 3: background.image = RESOURCE_IMAGE_BACKGROUND_3; break;
			case 4: background.image = RESOURCE_IMAGE_BACKGROUND_4; break;
			case 5: background.image = RESOURCE_IMAGE_BACKGROUND_5; break;
		}
	}
	
	public void spawnPoster(Entity poster, Entity background, int backgroundtype)
	{
		switch(randomRange(1, 6))
		{
			case 1: poster.image = RESOURCE_IMAGE_POSTER_1; break;
			case 2: poster.image = RESOURCE_IMAGE_POSTER_2; break;
			case 3: poster.image = RESOURCE_IMAGE_POSTER_3; break;
			case 4: poster.image = RESOURCE_IMAGE_POSTER_4; break;
			case 5: poster.image = RESOURCE_IMAGE_POSTER_5; break;
			case 6: poster.image = RESOURCE_IMAGE_POSTER_6; break;
		}
		
		poster.size = new Vector(poster.image.getWidth(), poster.image.getHeight());
		
		float spawnX = background.position.x + posterBounds[backgroundtype - 1][0].x;
		float spawnY = background.position.y + posterBounds[backgroundtype - 1][0].x;
		float spawnWidth = spawnX + posterBounds[backgroundtype - 1][1].x - poster.size.x;
		float spawnHeight = spawnY + posterBounds[backgroundtype - 1][1].y - poster.size.y;
		
		poster.position = new Vector((float) randomRange((int) spawnX, (int) spawnWidth), (float) randomRange((int) spawnY, (int) spawnHeight));
	}
	
	public void spawnObstacle(int type, Vector pos)
	{
		for(Obstacle obs : obsList)
		{
			if(!obs.isUsed && obs.getType() == type && !obs.destroyed)
			{
				obs.reuse(Vector.add(obsSpawn, pos)); break;
			}
		}
	}
	
	public void spawnObstacle(int type)
	{
		for(Obstacle obs : obsList)
		{
			if(!obs.isUsed && obs.getType() == type && !obs.destroyed)
			{
				obs.reuse(obsSpawn); break;
			}
		}
	}
	
	public static void loadResources()
	{
		RESOURCE_IMAGE_OBS_TABLE = Image.readImage("source/sprites/obs_table.png");
		RESOURCE_IMAGE_OBS_SEAT = Image.readImage("source/sprites/obs_chair.png");
		RESOURCE_IMAGE_BACKGROUND_1 = Image.readImage("source/backgrounds/background1.png");
		RESOURCE_IMAGE_BACKGROUND_2 = Image.readImage("source/backgrounds/background2.png");
		RESOURCE_IMAGE_BACKGROUND_3 = Image.readImage("source/backgrounds/background3.png");
		RESOURCE_IMAGE_BACKGROUND_4 = Image.readImage("source/backgrounds/background4.png");
		RESOURCE_IMAGE_BACKGROUND_5 = Image.readImage("source/backgrounds/background5.png");
		RESOURCE_IMAGE_POSTER_1 = Image.readImage("source/backgrounds/poster1.png");
		RESOURCE_IMAGE_POSTER_2 = Image.readImage("source/backgrounds/poster2.png");
		RESOURCE_IMAGE_POSTER_3 = Image.readImage("source/backgrounds/poster3.png");
		RESOURCE_IMAGE_POSTER_4 = Image.readImage("source/backgrounds/poster4.png");
		RESOURCE_IMAGE_POSTER_5 = Image.readImage("source/backgrounds/poster5.png");
		RESOURCE_IMAGE_POSTER_6 = Image.readImage("source/backgrounds/poster6.png");
		RESOURCE_IMAGE_TITLE = Image.readImage("source/ui/ui_title.png");
		RESOURCE_IMAGE_ITEM_CINNABOMB = Image.readImage("source/sprites/item_cinnabomb.png");
		RESOURCE_IMAGE_ITEM_MOP = Image.readImage("source/sprites/item_mop.png");
	}
	
	public static int randomRange(int min, int max)
	{
		if (min >= max) { return -1; }

		Random random = new Random();

		return random.nextInt((max - min) + 1) + min;
	}
	
	public void destoryObstaclesOnScreen()
	{
		for(Obstacle obs : GameHandler.obsList)
		{
			if(obs.isUsed)
			{
				obs.destroy();
			}
		}
	}
	
	public void endRun()
	{
		runStopped = true;
		replay.active = true;
		deathScreen.active = true;
		
		item.owner = null;
		item.active = false;
		
		destoryObstaclesOnScreen();
		
		stopGame();
	}
}