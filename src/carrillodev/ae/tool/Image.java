package carrillodev.ae.tool;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import carrillodev.ae.core.Game;

public class Image
{
	public static BufferedImage _readImage(String URL) // Global image reader class
	{
		BufferedImage loadedImage = null; // loaded image
		BufferedImage newImage = null; // new image (for removing backgorund color)
		
		try { loadedImage = ImageIO.read(new File(URL)); } // try to read the file
		catch(Exception e) { e.printStackTrace(); } // catch any errors while trying to read the file and print it
		
		if(loadedImage == null) { Game.log("Failed to load image " + '"' + URL + '"'); } // if failed to load image print it out 
		else
		{
			newImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_ARGB); // set a blank image
			
			for(int i = 0; i < loadedImage.getWidth(); i++) // go through the vertical pixels
			{
				for(int j = 0; j < loadedImage.getHeight(); j++) // go through the horizontal pixels
				{
					Color pixelColor = new Color(loadedImage.getRGB(i, j)); // get the pixel color
					
					if(pixelColor.getRed() == 255 && pixelColor.getBlue() == 255 && pixelColor.getGreen() == 0) { newImage.setRGB(i, j, Color.TRANSLUCENT); } // if the color is 255,255,255 then set the alpha to zero
					else { newImage.setRGB(i, j, pixelColor.getRGB()); } // if not then the color is set to normal
				}
			}
			
			Game.log("Loaded image " + '"' + URL + '"'); // log it
		}
		
		return newImage; // return the new image produced.
	}
	
	public static BufferedImage readImage(String URL) // Global image reader class
	{
		BufferedImage loadedImage = null; // loaded image
		
		try { loadedImage = ImageIO.read(new File(URL)); } // try to read the file
		catch(Exception e) { e.printStackTrace(); } // catch any errors while trying to read the file and print it
		
		return loadedImage;
	}
}