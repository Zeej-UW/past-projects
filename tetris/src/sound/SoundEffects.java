/* 
 * TCSS 305 Autumn 2017 - Assignment 6: Tetris
 */
package sound;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import model.TetrisPiece;

/**
 * Does sound effects for the game.
 * 
 * @author cjjaxx
 * @version 8 December 2017
 */
public class SoundEffects implements Observer {
    
    /**
     * Determines if sound is on or off.
     */
    private boolean mySoundEnable;

    /**
     * Constructor for the SoundEffects.
     */
    public SoundEffects() {
        mySoundEnable = true;
    }
    
    @Override
    public void update(final Observable theObservable, final Object arg0) {
        if (mySoundEnable) {
            if (arg0 instanceof TetrisPiece) {
                createAndDoSound("./sounds/frozen1.wav");
            } else if (arg0 instanceof List) {
                createAndDoSound("./sounds/frozen.wav");
            } else if (arg0 instanceof Integer[]) {
                createAndDoSound("./sounds/lineclear.wav");
            }
        }
    }
    
    /**
     * Creates and executes a sound.
     * 
     * @param theFileName a file name to be used as a sound.
     */
    private void createAndDoSound(final String theFileName) {
        try {
            final File f = new File(theFileName);
            final AudioInputStream move = AudioSystem.getAudioInputStream(f);
            final Clip clip = AudioSystem.getClip();
            
            clip.open(move);
            clip.start();
        } catch (final UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Will enable/disable the sound effects (because they're kind of annoying).
     * 
     * @param theFlag a boolean which will determine if sound is enabled or disabled.
     */
    public void soundEnable(final boolean theFlag) {
        mySoundEnable = theFlag;
    }
    
}
