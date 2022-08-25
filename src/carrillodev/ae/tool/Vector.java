package carrillodev.ae.tool;

public class Vector
{
	
	public static final Vector LEFT = new Vector(-1f, 0f);
	public static final Vector RIGHT = new Vector(1f, 0f);
	public static final Vector UP = new Vector(0f, -1f);
	public static final Vector DOWN = new Vector(0f, 1f);
	
	// vector holds two floats (x,y)
	public float x;
	public float y;
	
	public static Vector ZERO = new Vector(0f, 0f); // Static vector variable with 0,0 assigned
	
	public Vector(float x, float y) // creates the vector
	{
		this.x = x;
		this.y = y;
	}
	
	public void set(float x, float y) // re-assigns the points
	{
		this.x = x;
		this.y = y;
	}
	
	public static Vector neg(Vector vector) { return new Vector(-vector.x, -vector.y); } // returns a negative vector
	public static Vector add(Vector vector1, Vector vector2) { return new Vector(vector1.x + vector2.x, vector1.y + vector2.y); } // adds two vectors and returns it
	public static Vector sub(Vector vector1, Vector vector2) { return new Vector(vector1.x - vector2.x, vector1.y - vector2.y); } // subtracts two vectors and returns it
	public static Vector mul(Vector vector1, Vector vector2) { return new Vector(vector1.x * vector2.x, vector1.y * vector2.y); } // multiplies two vectors and returns it
	public static Vector dev(Vector vector1, Vector vector2) { return new Vector(vector1.x / vector2.x, vector1.y / vector2.y); } // divides two vectors and returns it
	public static Vector mul(Vector vector, float i) { return new Vector(vector.x * i, vector.y * i); }
}