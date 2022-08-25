package carrillodev.ae.core;

import carrillodev.ae.tool.Entity;
import carrillodev.ae.tool.Vector;

public class Camera
{
	public Vector position = new Vector(0f, 0f); // Position of the camera
	
	Entity lockedEntity; // Entity that the camera can follow

	public void lockOnObject(Entity entity) // sets the locked entity
	{
		position = new Vector(0f, 0f);
		entity.centre = entity.position; // Sets the entites centre, which allows it to stay wherever on the screen it was before being followed by the camera
		lockedEntity = entity; // set the locked entity
	}
	
	public boolean testLockedObject(Entity entity) { return lockedEntity == entity; } // test to see if an entity is the locked entity
}