/* 
 * TCSS 305 Autumn 2017 - Assignment 6: Tetris
 */
package view;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import model.Block;
import model.Point;

/**
 * A DrawableBlock which will draw the blocks of a Tetris piece.
 * 
 * @author cjjaxx
 * @version 27 November 2017
 */
public class DrawableBlock {
    
    /** Default block size. */
    private static final int BLOCK_SZ = 25;
    
    /** The maximum RGB value. */
    private static final int MAX_RGB_VAL = 255;
    
    /** a Block object that will be used to draw the block. */
    private Block myBlock;
    
    /** A model.Point object which will come from myBlock. */
    private Point myPoint;
    

    /** Height of the TetrisPiece the block is a part of. */
    private int myHeight;

    /** Width of the TetrisPiece the block is a part of. */
    private int myWidth;

    /** Size of the preview panel. */
    private int myPanelSize;
    
    /**
     * Default constructor for the DrawableBlock object.
     */
    public DrawableBlock() {
        this(new Point(0, 0), Block.EMPTY);
    }
    
    /** 
     * Overloaded constructor for the DrawableBlock object.
     * 
     * @param thePoint a model.Point which will be used to draw the block.
     * @param theBlock a Block obj. which will be used to determine how a block will be drawn.
     */
    public DrawableBlock(final Point thePoint, final Block theBlock) {
        this(thePoint, theBlock, 0, 0, 0);
    }
    
    /** 
     * Overloaded constructor for the DrawableBlock object.
     * 
     * @param thePoint a model.Point which will be used to draw the block.
     * @param theBlock a Block obj. which will be used to determine how a block will be drawn.
     * @param thePanelSize an int which will be used to determine origin point of the block.
     * @param theHeight an int which is the height of the tetris piece the block is a part of.
     * @param theWidth an int which is the width of the tetris piece the block is a part of.
     */
    public DrawableBlock(final Point thePoint, final Block theBlock,
                         final int thePanelSize, final int theHeight,
                         final int theWidth) {
        myPoint = thePoint;
        myBlock = theBlock;
        myPanelSize = thePanelSize;
        myHeight = theHeight;
        myWidth = theWidth;
        if (myBlock == Block.O) {
            myWidth = myWidth - 1;
        }
    }
    
    /**
     * Determines color of block. 
     * 
     * @return a Color relating to the type of Tetris Piece.
     */
    public Color determineColor() {
        Color aColor = Color.white;
        if (myBlock == Block.Z) {
            aColor = Color.RED;
        } else if (myBlock == Block.T) {
            aColor = Color.PINK;
        } else if (myBlock == Block.S) {
            aColor = Color.GREEN;
        } else if (myBlock == Block.O) {
            aColor = Color.YELLOW;
        } else if (myBlock == Block.L) {
            aColor = Color.ORANGE;
        } else if (myBlock == Block.J) {
            aColor = Color.BLUE;
        } else {
            aColor = Color.CYAN;
        }
        return aColor;
    }
    
    /**
     * Each time a repaint happens, the color of each block is now a Random RBG value.
     * A 1 is added to MAX_RGB_VAL as nextInt is exclusive.
     * 
     * @return a Color which is determined via a Random object.
     */
    public Color colorMadness() {
        final Random rndm = new Random();
        return new Color(rndm.nextInt(MAX_RGB_VAL + 1),
                         rndm.nextInt(MAX_RGB_VAL + 1),
                         rndm.nextInt(MAX_RGB_VAL + 1));
    }
    
    /** 
     * Determines which divisor (for x) should be used based on the block/piece that is in use.
     * 
     * @return an int which will be used in determining how the shape will be centered.
     */
    private int determineXOffset() {
        int xOffset = 1;
        if (Math.abs(myWidth) % 2 == 1) {
            xOffset = (int) (((double) BLOCK_SZ / 2) * (myWidth * myHeight + 1));
        } else if (myWidth % 2 == 0) {
            xOffset = (int) (((double) BLOCK_SZ) * myWidth * myHeight);
        }
        return xOffset;
    }
    
    /** 
     * Determines which divisor (for y) should be used based on the block/piece that is in use.
     * 
     * @return an int which will be used in determining how the shape will be centered.
     */
    private int determineYOffset() {
        int yOffset = 1;
        if (Math.abs(myHeight) % 2 == 1) {
            yOffset = (int) (myPanelSize / 2) +  myHeight * BLOCK_SZ + BLOCK_SZ / 2;
        } else if (myHeight % 2 == 0) {
            yOffset = myPanelSize / 2 + BLOCK_SZ;
        }
        return yOffset;
    }
    
    /**
     * Similar to getShape(), but will center (with some accuracy) the shape that's 
     * being previewed in a panel.
     * 
     * @return a Shape object, specifically a Rectangle2D one.
     */
    public Shape getPreviewShape() {
        final int x = -myPoint.x() * BLOCK_SZ + determineXOffset();
        final int y = -myPoint.y() * BLOCK_SZ + determineYOffset();
        return new Rectangle2D.Double(x, y, BLOCK_SZ, BLOCK_SZ);
    }
    
    /**
     * Gets the shape (a rectangle) which will be used by the paintComponents of the GamePanel
     * and PreviewPanel.
     * 
     * @return a Shape object, specifically a Rectangle2D one.
     */
    public Shape getShape() {
        return new Rectangle2D.Double(myPoint.y() * BLOCK_SZ,
                                               myPoint.x() * BLOCK_SZ, 
                                               BLOCK_SZ,
                                               BLOCK_SZ);
        
    }
    
}
