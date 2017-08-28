package gfx;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound
{//SOUNDS
	public static Clip music;
	public static Clip sfx;
	public static AudioInputStream sfxStream; //where the sfc is played from (what sfx is playing)
	public static AudioInputStream musicStream; //where the music is played from (Aka what song is playing)

	public static float musicVolume = 50;
	public static float sfxVolume = 50;
	public static float musicVolumeChange = 0;
	public static float sfxVolumeChange = 0;
	
	public static void init()
	{

	}
	
	public static void setSfxVolume(float vol)
	{
		if(Sound.sfxVolumeChange < -50)
			Sound.sfxVolumeChange = -50;
		if(Sound.sfxVolumeChange > 0)
			Sound.sfxVolumeChange = 0;
	    
	    Sound.sfxVolumeChange = vol;
	    Sound.sfxVolume =  50 + sfxVolumeChange;
	}
	
	public static void setMusicVolume(float vol)
	{
		if(Sound.musicVolumeChange-vol < -50)
			Sound.musicVolumeChange = -50;
		if(Sound.musicVolumeChange+vol > 0)
			Sound.musicVolumeChange = 0;
			
	    Sound.musicVolumeChange = vol;
	    Sound.musicVolume = 50 + musicVolumeChange;
	    
	    //need this since it's looping continuously 
		FloatControl gainControl = (FloatControl) Sound.music.getControl(FloatControl.Type.MASTER_GAIN);
	    gainControl.setValue(Sound.musicVolumeChange); 
	}
	
	public static void playSfx(String url)
	{
		try
		{
			sfx = AudioSystem.getClip();
			sfxStream = AudioSystem.getAudioInputStream(new File("src/resources/sound/soundeffects/"+url));
			sfx.open(sfxStream);
			FloatControl gainControl = (FloatControl) Sound.sfx.getControl(FloatControl.Type.MASTER_GAIN);
		    gainControl.setValue(Sound.sfxVolumeChange); 
		    sfx.start(); 
		}
		catch(Exception e)
		{
	         System.err.println(e.getMessage());
		}
	}
	
	public static void playMusic(String url)
	{
		try
		{
			music = AudioSystem.getClip();
			musicStream = AudioSystem.getAudioInputStream(new File("src/resources/sound/music/"+url));
			music.open(musicStream);
			music.loop(Clip.LOOP_CONTINUOUSLY);
	        music.start(); 
		}
		catch(Exception e)
		{
	         System.err.println(e.getMessage());
		}
	}
}
