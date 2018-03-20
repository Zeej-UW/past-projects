/* 
 * TCSS 305 Autumn 2017 - Assignment 6: Tetris
 */

package view;

import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * This sets up the JMenuBar for the TetrisGUI's frame.
 * 
 * @author cjjaxx
 * @version 28 November 2017
 */
public class MainGUIMenuBar {

    /** A list of all settings actions. */
    private final List<AbstractAction> mySettings;
    
    /** An AbstracAction relating to the keybinding capability. */
    private final AbstractAction myKeybindAction;

    /** A list of all game actions. */
    private final List<AbstractAction> myGameOptions;
    
    /**
     * Constructor for the MainGUIMenuBar.
     * 
     * @param theSettings a list of all settings actions.
     * @param theGame a list of all game actions.
     * @param theKeybindAction an AbstractAction which will changed Keybinds.
     */
    public MainGUIMenuBar(final List<AbstractAction> theSettings,
                          final List<AbstractAction> theGame,
                          final AbstractAction theKeybindAction) {
        super();
        myKeybindAction = theKeybindAction;
        mySettings = theSettings;
        myGameOptions = theGame;
    }
    
    /**
     * Creates a JMenuBar consisting of sub-menus.
     * 
     * @return a JMenuBar with menus attached.
     */
    public JMenuBar makeMenubar() {
        final JMenuBar menubar = new JMenuBar();
        
        final JMenu gameOptions = makeGameOptionsMenu();
        menubar.add(gameOptions);
        
        final JMenu settings = makeSettingsMenu();
        menubar.add(settings);
        
        return menubar;
    }
    
    /**
     * A game options menu which can start/end a game.
     * 
     * @return a JMenu which contains all the different game options.
     */
    private JMenu makeGameOptionsMenu() {
        final JMenu menu = new JMenu("Game");
        for (final AbstractAction action : myGameOptions) {
            menu.add(new JMenuItem(action));
        }
        
        return menu;
    }
    
    /**
     * A settings menu which contains various settings that can be changed.
     * 
     * @return a JMenu which contains all different setting menu items.
     */
    private JMenu makeSettingsMenu() {
        final JMenu menu = new JMenu("Settings");
        for (final AbstractAction action : mySettings) {
            final JCheckBoxMenuItem aCheckbox = new JCheckBoxMenuItem(action);
            if ("Enable Sound".equals(aCheckbox.getText())) {
                aCheckbox.setState(true);
            }
            menu.add(aCheckbox);
        }
        
        menu.addSeparator();
        menu.add(myKeybindAction);
        return menu;
    }
    
}
