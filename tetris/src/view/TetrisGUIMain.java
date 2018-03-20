/* 
 * TCSS 305 Autumn 2017 - Assignment 6: Tetris
 */

package view;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This class launches Tetris.
 * 
 * @author cjjaxx
 * @version 30 November 2017
 */
public final class TetrisGUIMain {
    
    /**
     * Private constructor, to prevent instantiation of this class.
     */
    private TetrisGUIMain() {
        throw new IllegalStateException();
    }
    
    /**
     * Creates a JFrame to demonstrate this panel.
     * It is OK, even typical to include a main method 
     * in the same class file as a GUI for testing purposes. 
     * 
     * @param theArgs Command line arguments, ignored.
     */
    public static void main(final String[] theArgs) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (final IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (final InstantiationException ex) {
            ex.printStackTrace();
        } catch (final ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TetrisGUI().createAndShowGUI();
            }
        });
    }
}
