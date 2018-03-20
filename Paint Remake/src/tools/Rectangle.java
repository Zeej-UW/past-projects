/*
 * TCSS 305 - PowerPaint 
 */

package tools;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;


/**
 * The rectangle tool object used in PowerPaintGUI.
 * 
 * @author cjjaxx
 * @version 13 November 2017
 */
public class Rectangle extends AbstractTool {
    
    /**
     * Constructor for the Ellipse tool.
     * 
     * @param theColor a color object for the tool.
     * @param theThickness a thickness for the tool's brush width.
     * @param thePress the point where it was initially clicked at.
     * @param theEnd the point where the cursor is at.
     */
    public Rectangle(final Color theColor, final int theThickness, 
                     final Point thePress, final Point theEnd) {
        super(theColor, theThickness, thePress, theEnd);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public Shape getShape() {           

        int aWidth = Math.abs((int) (getRelease().getX() - getPress().getX()));
        int aHeight = (int) (getRelease().getY() - getPress().getY());
        Shape rect = new Rectangle2D.Double(getPress().getX(), 
                                             getPress().getY(),
                                             aWidth,
                                             aHeight);
        if (getRelease().getX() < getPress().getX() 
                        && getRelease().getY() < getPress().getY()) {
            aWidth = Math.abs((int) (getPress().getX() - getRelease().getX()));
            aHeight = Math.abs((int) (getPress().getY() - getRelease().getY()));
            
            rect = new Rectangle2D.Double(getRelease().getX(),
                                          getRelease().getY(),
                                          aWidth,
                                          aHeight);
        } else if (getRelease().getY() < getPress().getY()) {
            aHeight = Math.abs((int) (getPress().getY() - getRelease().getY()));
            rect = new Rectangle2D.Double(getPress().getX(),
                                          getRelease().getY(),
                                          aWidth,
                                          aHeight);
        } else if (getRelease().getX() < getPress().getX()) {
            aWidth = Math.abs((int) (getPress().getX() - getRelease().getX()));
            rect = new Rectangle2D.Double(getRelease().getX(),
                                          getPress().getY(),
                                          aWidth,
                                          aHeight);
        }
        
        //if Thickness is < 1, will return an empty rectangle.
        if (getThickness() < 1) {
            rect = new Rectangle2D.Double();
        }
        return rect;
    }

}
