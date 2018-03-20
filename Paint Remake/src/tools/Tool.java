/*
 * TCSS 305 - PowerPaint
 */

package tools;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.util.List;

/**
 * Interface for objects that will be tools to be used in an interactive
 * GUI used for drawing.
 * 
 * @author cjjaxx
 * @version 16 November 2017
 */
public interface Tool {
    
    /**
     * Getter for the thickness of the tool at the current time.
     * 
     * @return an int relating to thickness.
     */
    int getThickness();
    
    /**
     * Setter for the thickness of the tool.
     * 
     * @param theThickness an integer that relates to thickness of a tool's brush.
     */
    void setThickness(int theThickness);
    
    /**
     * Getter for the color of the tool.
     * 
     * @return a Color object.
     */
    Color getColor();
    
    /**
     * Setter for the color of the tool.
     * 
     * @param theColor a color that relates to the color of the tool's brush.
     */
    void setColor(Color theColor);
    
    /**
     * Setter for the press' point.
     * 
     * @param thePoint where the mouse was clicked.
     */
    void setPress(Point thePoint);
    
    /**
     * Returns a Point object which is the point where it was initially pressed.
     * 
     * @return the point where it was initially pressed.
     */
    Point getPress();
    
    /** 
     * Returns a list of all points the cursor has clicked, released, and dragged upon.
     * 
     * @return a List of points.
     */
    List<Point> getPointList();
    
    /**
     * Setter for the release point.
     * 
     * @param thePoint where the mouse was released. 
     */
    void setRelease(Point thePoint);
    
    /**
     * Returns a Point object which is the point where the press was released.
     * 
     * @return the point where the cursor is released.
     */
    Point getRelease();
    
    /** 
     * Returns point which mouse was previously at. 
     * 
     * @return a Point object relating to where the mouse last was.
     */
    Point getPreviousPoint();
    
    /**
     * Sets the previous point the mouse was previously at.
     * 
     * @param thePoint a Point object relating to where the mouse last was.
     */
    void setPreviousPoint(Point thePoint);
    
    /**
     * Clears the list containing all points touched by tool.
     */
    void removePoints();
    
    /**
     * Returns the associated shape of the Tool.
     * 
     * @return a Shape object.
     */
    Shape getShape();
}
