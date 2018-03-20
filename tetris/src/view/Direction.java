/* 
 * TCSS 305 Autumn 2017 - Assignment 6: Tetris
 */
package view;

/**
 * The different types of directions that can be stored as key mappings.
 * 
 * @author cjjaxx
 * @version 28 November 2017
 */
public enum Direction {
    
    /** Does nothing. */
    NONE,
    
    /** Direction the piece wants to move in. (down) */
    DOWN,
    
    /** Direction the piece wants to move in. (left) */
    LEFT,
    
    /** Direction the piece wants to move in. (right) */
    RIGHT,
    
    /** Direction the piece wants to rotate in. (clockwise) */
    ROTATE_CW,
    
    /** Drops the piece to whatever is directly under it. */
    DROP;
}
