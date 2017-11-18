package util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils 
{
	public static String loadFileAsString(String path)
	{
		//New way to read file
		String fileString = null;
		try
		{
			fileString = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return fileString;
		//OLD WAY
		/*
		StringBuilder builder = new StringBuilder();
		
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(Utils.class.getClassLoader().getResourceAsStream(path)));	
			String line;
			while((line = br.readLine()) != null)
				builder.append(line +"\n");
			
			br.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return builder.toString();
		*/
	}
	
	public static int parseInt(String number)
	{
		try
		{
			return Integer.parseInt(number);
		}catch(NumberFormatException e)
		{
			e.printStackTrace();
			return 0;
		}
	}
}
