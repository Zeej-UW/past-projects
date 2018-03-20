/*
 * TCSS 305 - PowerPaint 
 */

package tools;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import view.PowerPaintGUI;

/**
 * A class of Actions that will be used by tools.
 * 
 * @author cjjaxx
 * @version 16 November 2017
 */
public class ToolAction extends AbstractAction {
    /** Randomly generated ID number. */
    private static final long serialVersionUID = -2070311155080041489L;
    
    /** The tool to be used. */
    private final Tool myToolToUse;
    
    /** The tool currently in use. */
    private final PowerPaintGUI myGUI;
    
    /**
     * Constructor for ToolAction objects. Takes in a name, icon, and tool. 
     * Will use the first letter  of the String passed through as the mnemonic
     * attached to this action.
     * 
     * @param theName a String that is the action's name.
     * @param theIcon an Icon which will be the icon representing the action.
     * @param theToolToUse a Tool which will be the tool attached to the action.
     * @param theGUI the GUI.
     */
    public ToolAction(final String theName, final Icon theIcon, 
                      final Tool theToolToUse, final PowerPaintGUI theGUI) {
        
        this(theName, theIcon, theToolToUse, theGUI,
             KeyEvent.getExtendedKeyCodeForChar(theName.charAt(0)));
    }
    
    /**
     * Constructor for ToolAction objects. Takes in a name, icon, and tool. Can explicitly
     * state which mnemonic you wish to use for this particular Action.
     * 
     * @param theName a String that is the action's name.
     * @param theIcon an Icon which will be the icon representing the action.
     * @param theToolToUse a Tool which will be the tool attached to the action.
     * @param theGUI the GUI.
     * @param theMnemonic 
     */
    public ToolAction(final String theName, final Icon theIcon,
                      final Tool theToolToUse, final PowerPaintGUI theGUI,
                      final int theMnemonic) {
        super(theName);
        
        setupIcons(theIcon);
        
     // set a mnemonic on the first character of the name
        putValue(Action.MNEMONIC_KEY, theMnemonic);

        // tool tips
        putValue(Action.SHORT_DESCRIPTION, theName + " background");
        
        // coordinate button selection
        putValue(Action.SELECTED_KEY, true);
        
        myGUI = theGUI;
        myToolToUse = theToolToUse;
        
    }
    
    /**
     * Sets up the icons passed through for the Action. 
     * 
     * @param theIcon an Icon object which will be used in set up of the Action.
     */
    private void setupIcons(final Icon theIcon) {
        putValue(SMALL_ICON, theIcon);
        
        // Here is how to assign a larger icon to the tool bar.
        final ImageIcon icon = (ImageIcon) theIcon;
        final Image largeImage =
            icon.getImage().getScaledInstance(15, -1, java.awt.Image.SCALE_SMOOTH);
        final ImageIcon largeIcon = new ImageIcon(largeImage);
        putValue(Action.LARGE_ICON_KEY, largeIcon);
    }

    
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        myGUI.setTool(myToolToUse);
    }
}
