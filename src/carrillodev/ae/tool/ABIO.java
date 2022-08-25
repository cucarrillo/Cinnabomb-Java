package carrillodev.ae.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ABIO
{
	public static List<String> readFile(String filePath) // file reader for the .aoba file
	{
		List<String> lineList = new ArrayList<String>(); // list for lines that we will obtain from the file being read
		
		try // try to read the file
		{
			FileReader reader = new FileReader(filePath); // file reader
			
			BufferedReader bufferedReader = new BufferedReader(reader); // line reader
			
			String line; // string line for reading use
			
			while((line = bufferedReader.readLine()) != null) // go through all lines
			{
				lineList.add(line); // add the current line to the list
			}
			
			bufferedReader.close(); // close the reader
		}
		catch(Exception e) { e.printStackTrace(); } // Catch any errors that might of happened after trying to read the file and print it
		
		return lineList; // return the list
	}
	
	public static void writeFile(String filePath, List<String> lines) // file writer for the .aoba file
	{
		try // try to write a file
		{
			FileWriter writer = new FileWriter(filePath); // file writer
			
			BufferedWriter bufferedWriter = new BufferedWriter(writer); // line writer
			
			for(String line : lines) // go through the lines provided
			{
				bufferedWriter.write(line + "\n"); // add that line to the file
			}
		    
			bufferedWriter.close(); // close the writer
		}
		catch(Exception e) { e.printStackTrace(); } // Catch any errors that might of happened after trying to read the file and print it
	}
}