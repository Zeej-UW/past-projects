/*
 * TCSS 305 - PowerPaint 
 */

package tools;

import java.awt.Color;
import java.awt.Point;

/**
 * The Eraser tool object used in PowerPaintGUI.
 * 
 * @author cjjaxx
 * @version 13 November 2017
 */
public class Eraser extends Pencil {

    /**
     * Constructor for the Eraser tool.
     * 
     * @param theColor a color object for the tool.
     * @param theThickness a thickness for the tool's brush width.
     * @param thePress the point where it was initially clicked at.
     * @param theEnd the point where the cursor is at.
     */
    public Eraser(final Color theColor, final int theThickness, 
                  final Point thePress, final Point theEnd) {
        super(theColor, theThickness, thePress, theEnd);
    }
    
    @Override 
    public void setColor(final Color theColor) {
        super.setColor(Color.WHITE);
    }
}
