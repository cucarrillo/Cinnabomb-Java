package carrillodev.ae.tool;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

public class Audio
{
	private Clip clip; // audio clip
	
	public Audio(String path) // creates the audio file
	{
		try // try to read the file 
		{
			InputStream inputStream = new FileInputStream(path);
			InputStream bufferedInputStream = new BufferedInputStream(inputStream);
			
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
			AudioFormat audioFormat = audioInputStream.getFormat();
			
			DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, audioFormat);
			
			clip = (Clip) AudioSystem.getLine(dataLineInfo); // set the clip
			clip.open(audioInputStream); // open the provided clip
		}
		catch(Exception e) { e.printStackTrace(); } // try to catch any errors that could have happened while trying to read the file and print it
	}
	
	public void playLoop()
	{
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		play();
	}
	
	public void play() { clip.setFramePosition(0); clip.start(); } // plays the clip
	public void stop() { clip.stop(); clip.setFramePosition(0); } // stops the clip
	public void pause() { clip.stop(); } // pauses the clip
	public void resume() { clip.start(); } // resumes the clip
	public void setVolume(float volume) { FloatControl floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); floatControl.setValue(volume); } // sets the volume
	public void setPosition(int pos) { clip.setFramePosition(pos); }
	public int getLength() { return clip.getFrameLength(); } // returns the length of the clip
	public int getCurrentFrame() { return clip.getFramePosition(); } // returns the current position of the clip
}