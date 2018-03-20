/*
 * TCSS 305 - PowerPaint 
 */

package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import tools.Tool;

/**
 * A JPanel used within the PowerPaintGUI that will serve as the drawing area.
 * 
 * @author cjjaxx
 * @version 16 November 2017
 */
public class DrawingArea extends JPanel {
    
    /**
     * 
     */
    private static final long serialVersionUID = -3994947857895936845L;
    
    /** Collection of shapes. */
    private final List<Shape> myShapes;
    
    /** The PowerPaint GUI. */
    private final PowerPaintGUI myPanel;

    /** A list of colors of the previously drawn shapes. */
    private final List<Color> myColors;
    
    /** A list of thicknesses of previously drawn shapes. */
    private final List<Integer> myThicknesses;

    /** A Tool object which will be used to draw components on a JPanel. */
    private Tool myTool;

    /** The point where the mouse was pressed at. */
    private Point myPress;

    /** The point where the mouse was released at. */
    private Point myRelease;
    
    /** 
     * Constructor for the DrawingArea objects. Takes in a PowerPaintGUI and a Tool object.
     * 
     * @param thePanel the GUI panel which it will grab tools, colors, and shapes from.
     */
    public DrawingArea(final PowerPaintGUI thePanel) {
        super();
        setLayout(new BorderLayout());
        this.addMouseListener(new MyMouseInputAdapter());
        this.addMouseMotionListener(new MyMouseInputAdapter());
        myShapes = new ArrayList<Shape>();
        myColors = new ArrayList<Color>();
        myThicknesses = new ArrayList<Integer>();
        myTool = thePanel.getTool();
        myPanel = thePanel;
    }
    
    /**
     * Clears all collections of the previously drawn shapes.
     */
    public void clearLists() {
        myColors.clear();
        myShapes.clear();
        myThicknesses.clear();
        repaint();
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2D = (Graphics2D) theGraphics;
        
        // for better graphics display
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw previous shapes
        for (int i = 0; i < myShapes.size(); i++) {
            g2D.setColor(myColors.get(i));
            g2D.setStroke(new BasicStroke(myThicknesses.get(i)));
            g2D.draw(myShapes.get(i));
        }
        
        g2D.setColor(myTool.getColor());
        g2D.setStroke(new BasicStroke(myTool.getThickness()));
        g2D.draw(myTool.getShape());
        
        determineClearStatus();
    }
    
    /**
     * Determines the status of the button based on whether or not there are components 
     * (shapes) drawn on the screen.
     */
    private void determineClearStatus() {
        
        /* Determines if there are shapes on screen. If there are, will disable/enable 
        the clear button.*/
        myPanel.enableClear(!(myShapes.isEmpty()));
    }
    /**
     * Mouse adapter used for drawing.
     * 
     * @author cjjaxx
     * @version 8 November 2017
     */
    class MyMouseInputAdapter extends MouseInputAdapter {
        
        @Override
        public void mousePressed(final MouseEvent theEvent) {
            updateTool();
            
            myTool = myPanel.getTool();
            myTool.removePoints();
            
            myPress = theEvent.getPoint();
            myRelease = theEvent.getPoint();
            
            myTool.setPress(myPress);
            myTool.setRelease(myRelease);
            
            // Changes color of tool based on what mouse button is pressed.
            if (theEvent.getButton() == MouseEvent.BUTTON1) {
                myTool.setColor(myPanel.getPrimary());
            } else if (theEvent.getButton() == MouseEvent.BUTTON3) {
                myTool.setColor(myPanel.getSecondary());
            }

            
            
            
        }
        
        @Override
        public void mouseReleased(final MouseEvent theEvent) {
            myTool.setPreviousPoint(myRelease);
            myRelease = theEvent.getPoint();
            addToLists();
            myTool.removePoints();
            updateTool();
        }
        
        @Override
        public void mouseEntered(final MouseEvent theEvent) {
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }
        
        @Override
        public void mouseExited(final MouseEvent theEvent) {
            setCursor(null);
        }
        
        @Override
        public void mouseDragged(final MouseEvent theEvent) {            
            myTool.setPreviousPoint(myTool.getRelease()); 
            myRelease = theEvent.getPoint();
            updateTool();
            repaint();
            
        }
        
        /**
         * Adds fields of tools to their respective collections.
         */
        private void addToLists() {
            myShapes.add(myTool.getShape());
            myThicknesses.add(myTool.getThickness());
            myColors.add(myTool.getColor());
        }
        /**
         * Updates the tool to the GUIs values.
         */
        private void updateTool() {
            myTool.setThickness(myPanel.getThickness());
            myTool.setPress(myPress);
            myTool.setRelease(myRelease);
            determineClearStatus();
        }
    }
}
