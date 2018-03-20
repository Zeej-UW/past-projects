/* 
 * TCSS 305 Autumn 2017 - Assignment 6: Tetris
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import model.Point;
import model.TetrisPiece;

/**
 * The panel which displays the next piece up.
 * 
 * @author cjjaxx
 * @version 27 November 2017
 */
public class TetrisPreviewPiece extends JPanel implements Observer {

    /** Randomly generated ID used for serialization. */
    private static final long serialVersionUID = -4921186675919159196L;
    
    /** Initial size of the panel. */
    private static final int INITIAL_P_SIZE = 150;
    
    /** A TetrisPiece object which is pushed to observers. */
    private TetrisPiece myBlock;
    
    /** The width of a tetris piece. */
    private int myWidth;
    
    /** The height of a tetris piece. */
    private int myHeight;
    
    /** An array of model.points which will be used to construct DrawableBlocks. */
    private Point[] myPoints;
    
    /**
     * Constructor for the TetrisPreviewPiece object.
     */
    public TetrisPreviewPiece() {
        super();
        setupPanel();
        myPoints = new Point[0];
        myWidth = 0;
        myHeight = 0;
    }
    
    /**
     * Sets up the (this) panel.
     */
    private void setupPanel() {
        setPreferredSize(new Dimension(INITIAL_P_SIZE, INITIAL_P_SIZE));
    }
    
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        
        if (theObject instanceof TetrisPiece) {
            myBlock = (TetrisPiece) theObject;
            myHeight = myBlock.getHeight();
            myWidth = myBlock.getWidth();
            myPoints = myBlock.getPoints();
            repaint();
        }
        
    }
    
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setPaint(Color.WHITE);
        g2d.fill(new Rectangle2D.Double(0, 0, INITIAL_P_SIZE - 1, INITIAL_P_SIZE - 1));
        g2d.setPaint(Color.BLACK);
        g2d.draw(new Rectangle2D.Double(0, 0, INITIAL_P_SIZE - 1, INITIAL_P_SIZE - 1));
        for (final Point aPoint : myPoints) {
            final DrawableBlock aBlock = new DrawableBlock(aPoint,
                                                     myBlock.getBlock(),
                                                     INITIAL_P_SIZE,
                                                     myHeight,
                                                     myWidth);
            g2d.setStroke(new BasicStroke(1));
            g2d.setPaint(aBlock.determineColor());
            g2d.fill(aBlock.getPreviewShape());
            
          //Draws outline of a block. 
            g2d.setPaint(Color.BLACK);
            g2d.draw(aBlock.getPreviewShape());
        }
    }
}
