/*
 * TCSS 305 - PowerPaint
 */

package view;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import tools.ToolAction;

/**
 * This handles the creation of toolbars and menus used in the PowerPaint GUI.
 * 
 * @author cjjaxx
 * @version 17 November 2017
 */
public class ToolbarAndMenu {
    
    /** Thickness Slider's minimum value. */
    private static final int THICKNESS_MIN = 0;
    
    /** Thickness Slider's maximum value. */
    private static final int THICKNESS_MAX = 20;
    
    /** Thickness Slider's initial value. */
    private static final int THICKNESS_INIT = 10;
    
    /** Thickness Slider's major tick mark values. */
    private static final int THICKNESS_MAJOR_TICKS = 5;
    
    /** Thickness Slider's minor tick mark values. */
    private static final int THICKNESS_MINOR_TICKS = 1;
    
    /** Primary color menu item located in options. */
    private JMenuItem myPrimaryColorItem;
    
    /** Secondary color menu item located in options. */
    private JMenuItem mySecondaryColorItem;
    
    /** List of actions for tools. */
    private final List<ToolAction> myToolActions;
    
    /** About menu item located in Help. */
    private final AbstractAction myAboutAction;
    
    /** An action used for clear button. */
    private final AbstractAction myClearAction;
    
    /** A PowerPaintGUI used to find out states of certain aspects of the panel. */
    private final PowerPaintGUI myGUI;
    
    /** An action used for the Primary Color button. */
    private final AbstractAction myPrimaryAction;
    
    /** An action used for the Secondary Color button. */
    private final AbstractAction mySecondaryAction;

    /** Initial primary color selected. */
    private Color myInitialPrimary;

    /** Initial secondary color selected. */
    private Color myInitialSecondary;

    /** Default tool selected at start. */
    private String myInitialToolNm;

    /** Clear menu item located in options. */
    private JMenuItem myClear;

    /**
     * Constructor for an object which will be used to create toolbars and menus.
     * 
     * @param theTools a List of ToolActions.
     * @param theClearAction an AbstractAction used to clear.
     * @param theAboutAction an AbstractAction used for the about.
     * @param thePrimaryAction an AbstractAction used for the primary color chooser.
     * @param theSecondaryAction an AbstractAction used for the secondary color chooser.
     * @param theGUI a PowerPaintGUI which will be used to get certain states of the panel.
     */
    public ToolbarAndMenu(final List<ToolAction> theTools, 
                          final AbstractAction theClearAction,
                          final AbstractAction theAboutAction,
                          final AbstractAction thePrimaryAction,
                          final AbstractAction theSecondaryAction,
                          final PowerPaintGUI theGUI) {
        myToolActions = theTools;
        myAboutAction = theAboutAction;
        myClearAction = theClearAction;
        myPrimaryAction = thePrimaryAction;
        mySecondaryAction = theSecondaryAction;
        myGUI = theGUI;
    }
    
    /**
     * Sets the initial values of menu items to arguments passed through.
     * 
     * @param thePrimary a Color which will be used for the initial icon color.
     * @param theSecondary a Color which will be used for the initial secondary icon color.
     * @param theToolNm a String which is the name of the default tool to be selected.
     */
    public void initialValues(final Color thePrimary,
                              final Color theSecondary, final String theToolNm) {
        myInitialPrimary = thePrimary;
        myInitialSecondary = theSecondary;
        myInitialToolNm = theToolNm;
    }
    /**
     * Makes a JToolBar consisting of tools that will be added to the window.
     * 
     * @return a JToolBar consisting of the tools.
     */
    public JToolBar makeToolBar() {
        final JToolBar toolbar = new JToolBar();
        
        final ButtonGroup bttnGrp = new ButtonGroup();
        
        for (final AbstractAction action : myToolActions) {
            final JToggleButton aButton = new JToggleButton(action);
            bttnGrp.add(aButton);
            toolbar.add(aButton);
        }
        
        return toolbar;
    }
    
    /**
     * Sets up the JMenuBar seen at the top of the frame.
     * 
     * @return a JMenuBar consisting of all of its sub-menus (Options, Tools, and Help.)
     */
    public JMenuBar setupMainMenu() {
        final JMenuBar menuBar = new JMenuBar();
        
        final JMenu options = setupOptionsMenu();
        options.setMnemonic(KeyEvent.VK_O);
        
        final JMenu tools = makeToolsMenu();
        tools.setMnemonic(KeyEvent.VK_T);
        
        final JMenu help = setupHelp();
        help.setMnemonic(KeyEvent.VK_H);
        
        menuBar.add(options);  
        menuBar.add(tools);
        menuBar.add(help);
        
        return menuBar;
    }
    
    /**
     * Sets up the JMenu that holds all the stuff located in help.
     * 
     * @return a JMenu consisting of JMenuItems that do various things.
     */
    private JMenu setupHelp() {
        final JMenu menu = new JMenu("Help");
        menu.add(makeMenuItem(KeyEvent.VK_A, myAboutAction));
        
        return menu;
    }
    
    /**
     * Sets up the Tools menu of the menu bar at the top.
     * 
     * @return a JMenu cons
     */
    private JMenu makeToolsMenu() {
        final JMenu menu = new JMenu("Tools");
        
        final ButtonGroup bttnGroup = new ButtonGroup();
        for (final AbstractAction ta : myToolActions) {
            final JRadioButtonMenuItem aButton = new JRadioButtonMenuItem(ta);
            bttnGroup.add(aButton);
            menu.add(aButton);
            aButton.setSelected(aButton.getText().equals(myInitialToolNm));
        }
        
        return menu;
    }
    
    /**
     * Sets the primary color for this class.
     * 
     * @param theColor a color which will represent the primary color.
     */
    public void setPrimaryIcon(final Color theColor) {
        myPrimaryColorItem.setIcon(new ColorIcon(theColor));
    }
    
    /**
     * Sets the secondary color for this class.
     * 
     * @param theColor a color which will represent the secondary color.
     */
    public void setSecondaryIcon(final Color theColor) {
        mySecondaryColorItem.setIcon(new ColorIcon(theColor));
    }
    /**
     * This method sets up the menu for the Options part of the toolbar and returns it.
     * 
     * @return a JMenu consisting of a JSlider and 2 JButtons.
     */
    private JMenu setupOptionsMenu() {
        final JMenu optionMenu = new JMenu("Options");
        final JSlider thickness = new JSlider(JSlider.HORIZONTAL, THICKNESS_MIN,
                                              THICKNESS_MAX, THICKNESS_INIT);
        thickness.setMajorTickSpacing(THICKNESS_MAJOR_TICKS);
        thickness.setMinorTickSpacing(THICKNESS_MINOR_TICKS);
        thickness.setPaintTicks(true);
        thickness.setPaintLabels(true);
        thickness.addChangeListener(new ChangeListener() {
            /** Called in response to slider events in this window. */
            @Override
            public void stateChanged(final ChangeEvent theEvent) {
                final int value = thickness.getValue();
                if (value > 0) {
                    myGUI.setThickness(value);
                } else {
                    myGUI.setThickness(0);
                }
            }
        });
        
        
        final JMenu thicknessMenu = new JMenu("Thickness");
        thicknessMenu.setMnemonic(KeyEvent.VK_T);
        thicknessMenu.add(thickness);
        
        myPrimaryColorItem = makeMenuItem(KeyEvent.VK_P, myPrimaryAction);
        mySecondaryColorItem = makeMenuItem(KeyEvent.VK_S, mySecondaryAction);   
        myPrimaryColorItem.setIcon(new ColorIcon(myInitialPrimary));
        mySecondaryColorItem.setIcon(new ColorIcon(myInitialSecondary));
        
        myClear = makeMenuItem(KeyEvent.VK_C, myClearAction);
        

        //Sets up the Option menu's appearance.
        optionMenu.add(thicknessMenu);
        optionMenu.addSeparator();
        
        optionMenu.add(myPrimaryColorItem);
        optionMenu.add(mySecondaryColorItem);
        optionMenu.addSeparator();
        optionMenu.add(myClear);
        
        return optionMenu;
    }
    
    /**
     * Creates a simple menu item using a String and int passed through
     * from the parameters.
     * 
     * @param theMnemonic an int that will be the JMenuItem's mnemonic.
     * @param theAction 
     * @return a JMenuItem with an accompanying mnemonic.
     */
    private JMenuItem makeMenuItem(final int theMnemonic, 
                                   final Action theAction) {
        final JMenuItem item = new JMenuItem(theAction);
        item.setMnemonic(theMnemonic);
        
        return item;
    }
    
    /**
     * Enables/disables the clear button based on the parameter passed through.
     * 
     * @param theFlag a boolean which is used to determine if the button should be enabled.
     */
    public void enableClear(final boolean theFlag) {
        myClear.setEnabled(theFlag);
    }
    
    
}
