package carrillodev.ae.tool;

import java.awt.image.BufferedImage;

public class Spritesheet
{
	public BufferedImage sheet;
	
	private Animation[] animationList;
	
	private int spriteWidth;
	private int spriteHeight;
	
	private int verticalSpacing;
	private int horizontalSpacing;
	
	private int currentAnimation;

	public boolean paused = false;
	
	public Spritesheet(String filePath, int _spriteWidth, int _spriteHeight, int _verticalSpacing, int _horizontalSpacing, String[] _animations)
	{
		spriteWidth = _spriteWidth;
		spriteHeight = _spriteHeight;
		
		verticalSpacing = _verticalSpacing;
		horizontalSpacing = _horizontalSpacing;

		animationList = new Animation[_animations.length];
		
		sheet = Image.readImage(filePath);
		
		// <name>(speed)|x,y|x,y|...
		// name:NAME,speed:NUMBER,animation:{(x,y),...}
		
		for(int i = 0; i < _animations.length; i++)
		{
			String animationName = _animations[i].split("name:")[1].split(",")[0];
			String animationSpeed = _animations[i].split("speed:")[1].split(",")[0];
			String animationLine = _animations[i].split("animation:")[1];
			
			animationList[i] = new Animation(animationName, Integer.parseInt(animationSpeed), animationLine);
		}
	}
	
	BufferedImage getImage()
	{
		int x = (int) ((animationList[currentAnimation].getPosition().x * spriteWidth) + ((animationList[currentAnimation].getPosition().x + 1) * verticalSpacing));
		int y = (int) ((animationList[currentAnimation].getPosition().y * spriteHeight) + ((animationList[currentAnimation].getPosition().y + 1) * horizontalSpacing));
		
		return sheet.getSubimage(x, y, spriteWidth, spriteHeight);
	}
	
	void update()
	{
		if(!paused ) { animationList[currentAnimation].update(); }
	}
	
	public int getCurrentFrameLength()
	{
		return animationList[currentAnimation].getLength();
	}
	
	public int getCurrentFrame()
	{
		return animationList[currentAnimation].getFrame();
	}
	
	public void playAnimation(String animationName)
	{
		for(int i = 0; i < animationList.length; i++)
		{
			if(animationList[i].name.equalsIgnoreCase(animationName))
			{
				currentAnimation = i; break;
			}
		}
	}
}