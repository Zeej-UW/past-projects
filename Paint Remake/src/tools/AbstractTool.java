/*
 * TCSS 305 - PowerPaint
 */

package tools;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract tool to be used in power paint.
 * 
 * @author cjjaxx
 * @version 10 November 2017
 */
public abstract class AbstractTool implements Tool {
    
    /** The color of the tool. */
    private Color myColor;
    
    /** The thickness (brush width) of the tool. */
    private int myThickness;
    
    /** Coordinates of where it was initially clicked. */
    private Point myPress;
    
    /** Coordinates of where the mouse cursor currently is. */
    private Point myRelease;
    
    /** The previous Point where the mouse was. */
    private Point myPreviousPoint;
    
    /** A list of points. */
    private final List<Point> myPoints;
    
    /**
     * Constructor for abstract tools.
     * 
     * @param theColor a Color object.
     * @param theThickness an integer relating to the thickness of line is drawn.
     * @param thePress the point where it is released (beginning).
     * @param theEnd the point where cursor is (end).
     */
    protected AbstractTool(final Color theColor, 
                        final int theThickness, final Point thePress,
                        final Point theEnd) {
        super();
        myColor = theColor;
        myThickness = theThickness;
        myPress = thePress;
        myRelease = theEnd;
        myPreviousPoint = theEnd;
        myPoints = new ArrayList<Point>();
        myPoints.add(myPress);
        myPoints.add(myRelease);
        myPoints.add(myPreviousPoint);
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getThickness() {
        return myThickness;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setThickness(final int theThickness) {
        this.myThickness = theThickness;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getColor() {
        return myColor;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setColor(final Color theColor) {
        this.myColor = theColor;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setPress(final Point thePoint) {
        myPress = thePoint;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Point getPress() {
        return (Point) myPress.clone();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Point> getPointList() {
        return myPoints;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setRelease(final Point thePoint) {
        myRelease = thePoint;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Point getRelease() {
        return (Point) myRelease.clone();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Point getPreviousPoint() {
        return myPreviousPoint;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setPreviousPoint(final Point thePoint) {
        myPreviousPoint = thePoint;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void removePoints() {
        myPoints.clear();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public abstract Shape getShape();
    
}
