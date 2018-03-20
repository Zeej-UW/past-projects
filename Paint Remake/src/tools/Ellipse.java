/*
 * TCSS 305 - PowerPaint
 */

package tools;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 * Tool Ellipse tool object used in PowerPaintGUI.
 * 
 * @author cjjaxx
 * @version 13 November 2017
 */
public class Ellipse extends AbstractTool {

    /**
     * Constructor for the Ellipse tool.
     * 
     * @param theColor a color object for the tool.
     * @param theThickness a thickness for the tool's brush width.
     * @param thePress the point where it was initially clicked at.
     * @param theEnd the point where the cursor is at.
     */
    public Ellipse(final Color theColor, final int theThickness, 
                   final Point thePress, final Point theEnd) {
        super(theColor, theThickness, thePress, theEnd);
    }
    
    @Override
    public Shape getShape() {   
        int aWidth = Math.abs((int) (getRelease().getX() - getPress().getX()));
        int aHeight = (int) (getRelease().getY() - getPress().getY());
        Shape elip = new Ellipse2D.Double(getPress().getX(), 
                                             getPress().getY(),
                                             aWidth,
                                             aHeight);
        if (getRelease().getX() < getPress().getX() 
                        && getRelease().getY() < getPress().getY()) {
            aWidth = Math.abs((int) (getPress().getX() - getRelease().getX()));
            aHeight = Math.abs((int) (getPress().getY() - getRelease().getY()));
            
            elip = new Ellipse2D.Double(getRelease().getX(),
                                          getRelease().getY(),
                                          aWidth,
                                          aHeight);
        } else if (getRelease().getY() < getPress().getY()) {
            aHeight = Math.abs((int) (getPress().getY() - getRelease().getY()));
            elip = new Ellipse2D.Double(getPress().getX(),
                                          getRelease().getY(),
                                          aWidth,
                                          aHeight);
        } else if (getRelease().getX() < getPress().getX()) {
            aWidth = Math.abs((int) (getPress().getX() - getRelease().getX()));
            elip = new Ellipse2D.Double(getRelease().getX(),
                                          getPress().getY(),
                                          aWidth,
                                          aHeight);
        }
        
        //if Thickness is < 1, will return an empty ellipse.
        if (getThickness() < 1) {
            elip = new Ellipse2D.Double();
        }
        return elip;
    }
}
