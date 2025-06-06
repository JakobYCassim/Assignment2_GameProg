import java.io.*;		// for playing sound clips
import java.util.HashMap;
import javax.sound.sampled.*;

public class SoundManager {				// a Singleton class
	HashMap<String, Clip> clips;

	private static SoundManager instance = null;	// keeps track of Singleton instance

	private float volume;

	private SoundManager () {
		clips = new HashMap<String, Clip>();

		Clip clip = loadClip("sounds/background.wav");	// played from start of the game
		clips.put("background", clip);

		clip = loadClip("sounds/trap_warning.wav");	// played when trap is triggered
		clips.put("trap_warning", clip);

		clip = loadClip("sounds/puzzle_piece.wav");	// played when puzzle piece is triggered
		clips.put("puzzle_piece", clip);

		clip = loadClip("sounds/treasure.wav");	// played when treasure is reached
		clips.put("treasure", clip);

		clip = loadClip("sounds/walking.wav");	// played when player moves
		clips.put("walking", clip);

		clip = loadClip("sounds/game_over.wav");
		clips.put("game_over", clip);

		clip = loadClip("sounds/player_attack.wav");
		clips.put("player_attack", clip);

		clip = loadClip("sounds/enemy_attack.wav");
		clips.put("enemy_attack", clip);

		clip = loadClip("sounds/hit.wav");
		clips.put("hit", clip);

		clip = loadClip("sounds/heal.wav");
		clips.put("heal", clip);

		clip = loadClip("sounds/slow_trap.wav");
		clips.put("slow_trap", clip);

		clip = loadClip("sounds/take-damage.wav");
		clips.put("take_damage", clip);

		clip = loadClip("sounds/block.wav");
		clips.put("block", clip);

	}


	public static SoundManager getInstance() {	// class method to retrieve instance of Singleton
		if (instance == null)
			instance = new SoundManager();
		
		return instance;
	}		


    	public Clip loadClip (String fileName) {	// gets clip from the specified file
 		AudioInputStream audioIn;
		Clip clip = null;

		try {
    			File file = new File(fileName);
    			audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL()); 
    			clip = AudioSystem.getClip();
    			clip.open(audioIn);
		}
		catch (Exception e) {
 			System.out.println ("Error opening sound files: " + e);
		}
    		return clip;
    	}


	public Clip getClip (String title) {

		return clips.get(title);
	}


    	public void playClip(String title, boolean looping) {
		Clip clip = getClip(title);
		if (clip != null) {
			clip.setFramePosition(0);
			if (looping)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			else
				clip.start();
		}
    	}


    	public void stopClip(String title) {
		Clip clip = getClip(title);
		if (clip != null) {
			clip.stop();
		}
    	}

	public void setVolume (String title, float volume) {
		Clip clip = getClip(title);

		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		
		//System.out.println("Volume: " + volume);
        //System.out.println("Current Gain: " + gainControl.getValue());

		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();

		//System.out.println("Calculated Gain: " + gain);

		gainControl.setValue(gain);
	}

}