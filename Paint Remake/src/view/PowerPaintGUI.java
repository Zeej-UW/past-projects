/*
 * Assignment 5 - TCSS 305 - PowerPaint
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import tools.Ellipse;
import tools.Eraser;
import tools.Line;
import tools.Pencil;
import tools.Rectangle;
import tools.Tool;
import tools.ToolAction;

/**
 * This program is meant to allow users to draw stuff with various colors
 * using JSwing.
 * 
 * @author cjjaxx
 * @version 16 November 2017
 *
 */
public class PowerPaintGUI extends JPanel {

    /** Randomly generated serial version ID. */
    private static final long serialVersionUID = -4115179122316461403L;
    
    /** Drawing Panel's preferred width. */
    private static final int PREF_WIDTH = 500;

    /** Drawing Panel's preferred height. */
    private static final int PREF_HEIGHT = 300;
    
    /** Initial thickness selected. */
    private static final int INITIAL_THICKNESS = 10;
    
    /** Default tool selected at start. */
    private static final String DEFAULT_TOOL_NM = "Line";
    
    /** Color chooser's name. */
    private static final String CC_NAME = "A Color Chooser";

    /** Initial primary color selected. */
    private static final Color INITIAL_COLOR = new Color(51, 0, 111);
    
    /** Initial secondary color selected. */
    private static final Color INITIAL_SECOND_COLOR = new Color(232, 211, 162);
    
    /** Starting point for initial shape. */
    private static final Point INITIAL_POINT = new Point(-25, -25);
    
    /** The point during initial press of mouse 1. */
    private Point myPress;

    /** The point during the release of mouse 1. */
    private Point myRelease;
    
    /** a JLabel that represents the drawing area. */
    private final DrawingArea myDrawingArea;
    
    /** Current selected thickness. */
    private int myCurrentThickness; 
    
    /** Current primary color. */
    private Color myCurrentPrimary;
    
    /** Current secondary color. */
    private Color myCurrentSecondary;
    
    /** List of actions for tools. */
    private List<ToolAction> myToolActions;
    
    /** A tool object. */
    private Tool myTool;
    
    /** An action used for clear button. */
    private AbstractAction myClearAction;
    
    /** An action used for the Primary Color button. */
    private AbstractAction myPrimaryAction;

    /** An action used for the Secondary Color button. */
    private AbstractAction mySecondaryAction;
    
    /** About menu item located in Help. */
    private AbstractAction myAboutAction;
    
    /** An object which handles creation of toolbars and menus. */
    private final ToolbarAndMenu myToolbarAndMenu;
    
    /**
     * Constructor for the PowerPaintGUI.
     */
    public PowerPaintGUI() {
        super();
        initialize();
        setupActions();
        myToolbarAndMenu = new ToolbarAndMenu(myToolActions, myClearAction,
                                              myAboutAction, myPrimaryAction, 
                                              mySecondaryAction, this);
        myToolbarAndMenu.initialValues(INITIAL_COLOR, INITIAL_SECOND_COLOR, DEFAULT_TOOL_NM);
        myDrawingArea = new DrawingArea(this);
        myDrawingArea.setBackground(Color.white);
        myDrawingArea.setPreferredSize(new Dimension(PREF_WIDTH, PREF_HEIGHT));
        myDrawingArea.setOpaque(true);
    }
    
    /**
     * This method initializes some instance fields.
     */
    private void initialize() {        
        myPress = INITIAL_POINT;
        myRelease = INITIAL_POINT;
        myCurrentPrimary = INITIAL_COLOR;
        myCurrentSecondary = INITIAL_SECOND_COLOR;
        myCurrentThickness = INITIAL_THICKNESS;
        
        myTool = new Line(myCurrentPrimary, myCurrentThickness, myPress, myRelease);
        myTool.setPreviousPoint(INITIAL_POINT);
    }
    
    
    /**
     * Returns selected tool.
     * 
     * @return the Tool which is currently selected.
     */
    public Tool getTool() {
        return myTool;
    }
    
    /**
     * Changes the selected tool.
     * 
     * @param theTool a Tool which the current tool will be changed to.
     */
    public void setTool(final Tool theTool) {
        myTool = theTool;
    }
    
    /**
     * Returns the currently selected primary Color for the Tool.
     * 
     * @return a Color which is the currently selected primary Color.
     */
    public Color getPrimary() {
        return myCurrentPrimary;
    }
    
    /**
     * Sets the primary Color to whatever Color is passed through.
     * 
     * @param theColor a Color which will be the next primary color.
     */
    public void setPrimary(final Color theColor) {
        myCurrentPrimary = theColor;
    }
    
    /**
     * Returns the currently selected secondary Color for the Tool.
     * 
     * @return a Color which is the currently selected secondary Color.
     */
    public Color getSecondary() {
        return myCurrentSecondary;
    }
    
    /**
     * Sets the secondary Color to whatever Color is passed through.
     * 
     * @param theColor a Color which will be the next secondary color.
     */
    public void setSecondary(final Color theColor) {
        myCurrentSecondary = theColor;
    }
    
    /**
     * Returns the currently selected thickness used in the current tool.
     * 
     * @return an int which is the thickness of the current tool.
     */
    public int getThickness() {
        return myCurrentThickness;
    }
    
    /**
     * Sets the thickness of the current tool.
     * 
     * @param theThickness an int which will be the next thickness.
     */
    public void setThickness(final int theThickness) {
        myCurrentThickness = theThickness;
    }

    /**
     * Sets up the actions that will be associated with the buttons/menu items.
     */
    private void setupActions() {        
        myToolActions = new ArrayList<ToolAction>();
        
        myToolActions.add(new ToolAction("Pencil", new ImageIcon("./images/pencil.gif"),
                                         new Pencil(myCurrentPrimary, myCurrentThickness,
                                                    myPress, myRelease), this));
        myToolActions.add(new ToolAction(DEFAULT_TOOL_NM, new ImageIcon("./images/line.gif"),
                                         new Line(myCurrentPrimary, myCurrentThickness,
                                                    myPress, myRelease), this));
        myToolActions.add(new ToolAction("Rectangle", new ImageIcon("./images/rectangle.gif"),
                                         new Rectangle(myCurrentPrimary, myCurrentThickness,
                                                    myPress, myRelease), this));
        myToolActions.add(new ToolAction("Ellipse", new ImageIcon("./images/ellipse.gif"),
                                         new Ellipse(myCurrentPrimary, myCurrentThickness,
                                                    myPress, myRelease), this));
        myToolActions.add(new ToolAction("Eraser", new ImageIcon("./images/eraser.gif"),
                                         new Eraser(myCurrentPrimary, myCurrentThickness,
                                                    myPress, myRelease), this, KeyEvent.VK_A));
        
        myClearAction = new AbstractAction("Clear") {
            
            /** Randomly generated serial ID. */
            private static final long serialVersionUID = 1889138847697112202L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myTool.removePoints();
                myTool.setPreviousPoint(INITIAL_POINT);
                myTool.setPress(INITIAL_POINT);
                myTool.setRelease(INITIAL_POINT);
                myDrawingArea.clearLists();
                setEnabled(false);
            }
        };
        
        myPrimaryAction = new AbstractAction("Primary Color...") {
            
            /** Randomly generated ID number. */
            private static final long serialVersionUID = -2269553351866867715L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final Color aColor = JColorChooser.showDialog(null, CC_NAME, null);
                if (aColor != null) {
                    myTool.setColor(aColor);
                    myCurrentPrimary = aColor;
                    myToolbarAndMenu.setPrimaryIcon(aColor);
                    
                }
                
            }
            
        };
        
        mySecondaryAction = new AbstractAction("Secondary Color...") {
            
            /** Randomly generated ID number. */
            private static final long serialVersionUID = -8371771944069371063L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final Color aColor = JColorChooser.showDialog(null, CC_NAME, null);
                if (aColor != null) {
                    myTool.setColor(aColor);
                    myCurrentSecondary = aColor;
                    myToolbarAndMenu.setSecondaryIcon(aColor);
                    
                }
                
            }
            
        };
        
        myAboutAction = new AbstractAction("About...") {

            /** Randomly generated ID number. */
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final JFrame aboutWindow = new JFrame();
                JOptionPane.showMessageDialog(aboutWindow, "Charles Jackson"
                                + "\nAutumn 2017"
                                + "\nTCSS 305 Assignment 5", "About", 
                                JOptionPane.INFORMATION_MESSAGE,
                                new ImageIcon("./images/Shine.png"));
                aboutWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
            
            
        };
    }
    
    /**
     * Enables/disables the clear button based on the parameter passed through.
     * 
     * @param theFlag a boolean which is used to determine if the button should be enabled.
     */
    public void enableClear(final boolean theFlag) {
        myToolbarAndMenu.enableClear(theFlag);
    }
    
    /**
     * Sets up components of the JPanel to draw the GUI.
     */
    private void setupComponents() {
        setLayout(new BorderLayout());
        add(myDrawingArea, BorderLayout.CENTER);
    }
    
    /**
     * Sets up the window to be visible.
     */
    private void setupWindow() {
        final JFrame window = new JFrame("Assignment 5");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try {
            window.setIconImage(ImageIO.read(new File("images/shine.png")));
        } catch (final IOException e) {
            e.printStackTrace();
            System.err.println("failed to set icon");
        }
        
        
        
        setupComponents();
        setOpaque(true);
        window.setContentPane(this);
        window.setJMenuBar(myToolbarAndMenu.setupMainMenu());
        add(myToolbarAndMenu.makeToolBar(), BorderLayout.SOUTH);
        window.pack();
        window.setVisible(true);
        
        
    }
    
    /**
     * Kicks off the program.
     */
    public void start() {
        setupWindow();
    }
    
    /**
     * The main method, invokes the PowerPaint GUI. Command line arguments are
     * ignored.
     * 
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        /* 
         * Use an appropriate Look and Feel 
         * https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         * 
         */
        try {
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
                new PowerPaintGUI().start();
            }
        });
    }   
}

