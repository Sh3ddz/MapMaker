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
