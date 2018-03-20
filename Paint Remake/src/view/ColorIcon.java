package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Icon;

/**
 * A class used to create icons to attach to Primary Color... and Secondary Color...
 * 
 * @author CJ
 * @version 9 November 2017
 */
public class ColorIcon implements Icon {
    
    /** Size of the icon. */
    private static final int DIMENSION = 10;
    
    /** Size of the icon. */
    private static final int SHADOW_DIMENSION = 12;
    
    /** Offset of the icon. */
    private static final int OFFSET = 5;
    
    /** Offset of the icon. */
    private static final int SHADOW_OFFSET = 4;
    
    /** Color of the icon. */
    private final Color myColor;
    
    
    
    /**
     * Constructor for tiny icon for PowerPaintGUI colors.
     * 
     * @param theColor the color passed through the icon.
     */
    public ColorIcon(final Color theColor) {
        myColor = theColor;
    }

    @Override
    public int getIconHeight() {
        return DIMENSION;
    }

    @Override
    public int getIconWidth() {
        return getIconHeight();
    }

    @Override
    public void paintIcon(final Component theComp, final Graphics 
                          theGfx, final int theX, final int theY) {
        final Graphics2D gfx = (Graphics2D) theGfx;
        gfx.setColor(myColor);
        
        final Graphics2D shadowGfx = (Graphics2D) gfx.create();
        shadowGfx.setColor(Color.BLACK);
        // "Shadow" of top rectangle.
        shadowGfx.fillRect(SHADOW_OFFSET, SHADOW_OFFSET, SHADOW_DIMENSION, SHADOW_DIMENSION);
        
        // Colored rectangle.
        gfx.fillRect(OFFSET, OFFSET, DIMENSION, DIMENSION);
        
    }

}
