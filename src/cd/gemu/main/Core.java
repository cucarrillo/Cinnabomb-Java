package cd.gemu.main;

import carrillodev.ae.core.*;

public class Core extends ApplicationCore
{
	static GameHandler game;
	
	public static void main(String[] args)
	{
		load(args);
		
		GameHandler.loadResources();
		
		game = new GameHandler();
		game.loadMenu();


		String[] credits = 
			
			{
					"Game Credits",
					"Background Art: Aleece Harmon",
					"Posters: Arianna Pappas, Cedar Edwards",
					"Sprites: Noah Soule",
					"Programming: Cesar Carrillo",
					"Music: David Bowman"
			};
		
		for(String cred : credits)
		{
			Game.log(cred);
		}
		
		_enterLoop();
	}
}