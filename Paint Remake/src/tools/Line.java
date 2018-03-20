/*
 * TCSS 305 - PowerPaint 
 */

package tools;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;

/**
 * @author cjjaxx
 * @version 10 November 2017
 *
 */
public class Line extends AbstractTool {
    /**
     * Constructor for the Line tool.
     * 
     * @param theColor a color object for the tool.
     * @param theThickness a thickness for the tool's brush width.
     * @param thePress the point where it was initially clicked at.
     * @param theEnd the point where the cursor is at.
     */
    public Line(final Color theColor, final int theThickness, 
                final Point thePress, final Point theEnd) {
        super(theColor, theThickness, thePress, theEnd);
    }

    @Override
    public Shape getShape() {   
        Shape line = new Line2D.Double(getPress().getX(), 
                                             getPress().getY(),
                                             getRelease().getX(),
                                             getRelease().getY());
        //if Thickness is < 1, will return an empty rectangle.
        if (getThickness() < 1) {
            line = new Line2D.Double();
        }
        
        return line;
    }

}
