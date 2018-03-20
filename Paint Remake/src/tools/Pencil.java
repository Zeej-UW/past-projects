/*
 * TCSS 305 - PowerPaint 
 */

package tools;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.util.List;

/**
 * The Pencil tool object used in PowerPaintGUI.
 * 
 * @author cjjaxx
 * @version 13 November 2017
 */
public class Pencil extends AbstractTool {
    
    /**
     * Constructor for the Pencil tool.
     * 
     * @param theColor a color object for the tool.
     * @param theThickness a thickness for the tool's brush width.
     * @param thePress the point where it was initially clicked at.
     * @param theEnd the point where the cursor is at.
     */
    public Pencil(final Color theColor, final int theThickness,
                  final Point thePress, final Point theEnd) {
        super(theColor, theThickness, thePress, theEnd);
        
    }
    
    @Override
    public Shape getShape() {
        final List<Point> points = getPointList();
        
        points.add(getPreviousPoint());
        points.add(getRelease());
        final Path2D aPath = new Path2D.Double();
       
        if (getThickness() != 0) {
            for (int i = 1; i < points.size() - 1; i++) {
                aPath.moveTo(points.get(i).getX(), points.get(i).getY());
                aPath.lineTo(points.get(i + 1).getX(), points.get(i + 1).getY());
            }
        }
        
        return aPath;
        
    }

}
