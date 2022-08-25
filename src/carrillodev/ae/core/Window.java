package carrillodev.ae.core;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import carrillodev.ae.tool.Image;

public class Window
{
	// Window class objects
	private Canvas canvas;
	private JFrame frame;
	private Input input;
	
	Graphics windowGraphics; // Public window graphics
	
	// Window class variables
	private int windowWidth;
	private int windowHeight;
	private float windowScale;
	
	// Window class device for fullscreen
	private GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	
	// Window creation function
	Window(int windowWidth, int windowHeight, float windowScale, String windowTitle)
	{
		// Assign variables
		this.windowHeight = windowHeight;
		this.windowWidth = windowWidth;
		this.windowScale = windowScale;
		
		Dimension windowDim = new Dimension(((int) (windowWidth*windowScale)), ((int) (windowHeight*windowScale)));
		
		// Assign game variables for the window
		Game.setVariable("windowWidth", Integer.toString(windowWidth - 4));
		Game.setVariable("windowHeight", Integer.toString(windowHeight - 10));
		Game.setVariable("windowScale", Float.toString(windowScale));
		Game.setVariable("fullScreen", "0"); 
		
		// Set up the canvas
		canvas = new Canvas();
		canvas.setSize(windowDim);
		
		// Add Key Listener
		input = new Input();
		canvas.addKeyListener(input);
		canvas.addMouseMotionListener(input);
		canvas.addMouseListener(input);
		
		// Set up the frame
		frame = new JFrame();
		frame.setTitle(windowTitle);
		frame.setIconImage(Image.readImage("source/ui/ui_logo.png"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.setSize(windowDim);
		//frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(1,1), "null"));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		// Create the graphics
		windowGraphics = new Graphics();
		
		boolean graphicsCreated = false;
		
		while(graphicsCreated == false) { graphicsCreated = windowGraphics.create(canvas); }
	}
	
	public int getWidth() { return windowWidth - 4; } // return width
	public int getHeight() { return windowHeight - 10; } // return height
	public float getScale() { return windowScale; } // return scale
	public void goFullScreen() { device.setFullScreenWindow(frame); } // enters fullscreen mode
	public void leaveFullScreen() { device.setFullScreenWindow(null); } // leaves fullscreen mode
}