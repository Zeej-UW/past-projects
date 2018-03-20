/* 
 * TCSS 305 Autumn 2017 - Assignment 6: Tetris
 */

package score;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import model.TetrisPiece;



/**
 * This class will keep observe a Tetris board's lines cleared, updating an accompanying score.
 * 
 * @author cjjaxx
 * @version 7 December 2017
 *
 */
public class ScoreV extends Observable implements Observer {

    /** Constant for the points given to score for a frozen piece. */
    private static final int PIECE_FROZEN_SCORE = 4;
    
    /** Constant for the lines until next difficulty. */
    private static final int LINES_FOR_LEVEL = 5;
    
    /** Constant for the score given to a line clear. */
    private static final int BASE_LINE_SCORE = 40;
    
    /** Array of Lines that are cleared. */
    private Integer[] myLinesCleared;
    
    /** Number of lines cleared. */
    private int myNumLinesCleared;

    /** Score of this current game. */
    private int myScore;
    
    /** Current difficulty of the game. */
    private int myLevel;
    
    /** Lines until the next level is reached. */
    private int myLineForNextLvl;
    
    /**
     * Constructor for the Score.
     */
    public ScoreV() {
        super();
        myLinesCleared = new Integer[0];
        myNumLinesCleared = 0;
        myScore = 0;
        myLevel = 1;
    }
    
    @Override
    public void update(final Observable theObservable, final Object theArg) {
        if (theArg instanceof Integer[]) {
            myLinesCleared = (Integer[]) theArg;
            myNumLinesCleared += myLinesCleared.length;
            
            
            
            
            calculateScore(myLinesCleared.length);
            determineLevel();
            
            setChanged();
            notifyObservers(createScoreMap());
        } else if (theArg instanceof TetrisPiece) {
            setChanged();
            notifyObservers(createScoreMap());
            myScore += PIECE_FROZEN_SCORE;
        }
    }
    /**
     * Returns a map of updated info.
     * 
     * @return a map of updated scoring info.
     */
    private Map<ScoreEnum, Integer> createScoreMap() {
        final Map<ScoreEnum, Integer> aMap = new HashMap<ScoreEnum, Integer>();
        aMap.put(ScoreEnum.SCORE, myScore);
        aMap.put(ScoreEnum.LINES_CLEARED, myNumLinesCleared);
        aMap.put(ScoreEnum.LEVEL, myLevel);
        aMap.put(ScoreEnum.LINES_UNTIL_NEXT_LVL, myLineForNextLvl);
        
        return aMap;
    }
    /**
     * Calculates score.
     * 
     * @param theLinesCleared number of lines cleared on this update.
     */
    private void calculateScore(final int theLinesCleared) {
        int aScore = 0;
        for (int i = 0; i < theLinesCleared; i++) {
            if (i == 0) {
                aScore = BASE_LINE_SCORE;
            } else {
                aScore = (int) (aScore * (2 + Math.pow(2, i - 2)));
            }
            
        }
        aScore *= myLevel;
        
        myScore += aScore;
    }
    
    
    /**
     * Determines the level the game is currently at.
     */
    private void determineLevel() {
        myLevel = myNumLinesCleared / LINES_FOR_LEVEL + 1;
        myLineForNextLvl = LINES_FOR_LEVEL - (myNumLinesCleared % LINES_FOR_LEVEL);
    }
    
    /**
     * Resets the score data to initial values.
     */
    public void resetInfo() {
        myScore = 0;
        myLevel = 1;
        myNumLinesCleared = 0;
        myLineForNextLvl = LINES_FOR_LEVEL - (myNumLinesCleared % LINES_FOR_LEVEL);
    }
    
    /**
     * Gets the current level at that point.
     * 
     * @return an integer relating the current level.
     */
    public static int getLineForLevel() {
        return LINES_FOR_LEVEL;
    }
    /**
     * Gets the score at that point.
     * 
     * @return an integer relating the score at this point.
     */
    public int getScore() {
        return myScore;
    }
    
    /**
     * Returns the number of lines cleared at this point.
     * 
     * @return an integer relating the number of lines cleared.
     */
    public int getLinesCleared() {
        return myNumLinesCleared;
    }

}
