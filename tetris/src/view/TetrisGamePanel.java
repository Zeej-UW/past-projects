/* 
 * TCSS 305 Autumn 2017 - Assignment 6: Tetris
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Block;
import model.Board;
import model.Point;
import score.ScoreEnum;

/**
 * TetrisGamePanel holds everything for the game and its display, as in the portion
 * of the game which shows the current status of the Board and what's currently there.
 * 
 * @author cjjaxx
 * @version 27 November 2017
 */
public class TetrisGamePanel extends JPanel implements Observer {
        
    /** The initial preferred width of the panel. */
    public static final int INITIAL_P_WIDTH = 250;
    
    /** The initial preferred height of the panel. */
    public static final int INITIAL_P_HEIGHT = 500;
    
    /** The initial block size (panel height / 20). */
    public static final int INITIAL_BLOCK_SZ = 25;
    
    /** The initial delay (in milliseconds) for the move timer. */
    public static final int INITIAL_DELAY = 0;
    
    /** The default delay (in milliseconds) for the move timer. */
    public static final int MOVE_DELAY = 1000;
    
    /** The speed change per level. */
    private static final int SPEED_PER_LEVEL = 50;
    
    /** 
     * The list initially has a size of 20, however will increase after first tick.
     * This amends counting issues on iterations.
     */
    private static final int LIST_SZ = 4;
    
    /** The presumed size of the array within the lists. (width of board) */
    private static final int ARRAY_SZ = 10;
    
    /** Randomly generated ID used for serialization. */
    private static final long serialVersionUID = 4778464076859855684L;
    
    /** A JSwing timer which is used to advance the game a step. */
    private Timer myTimer;
    
    /** A KeyListener which listens to the keyboard and does stuff based on input. */
    private KeyListener myListener;
    
    /** Holds the Tetris board (the logic element of the program). */
    private Board myBoard;

    /** A List of Block arrays which will be used in the drawing of the board. */
    private List<Block[]> myBlockData;

    /** Determines if grid mode will be activated. */
    private boolean myGridEnable;
    
    /** A map of all keys and their associated direction. */
    private Map<Integer, Direction> myKeyMapping;

    /** Determines if color madness will be activated. */
    private boolean myColorEnable;

    /** A backup map of the keymaps, mainly used for pausing purposes. */
    private Map<Integer, Direction> myCopyMap;

    /** Boolean which tells if the game is paused or not. */
    private boolean myPaused;

    /** TetrisGUI object. */
    private final TetrisGUI myGUI;

    /** Speed at which the animation steps. */
    private int myAnimationSpeed;

    /** Current level the game is at. */
    private int myLevel;

    
    
    /**
     * Constructor for the TetrisGamePanel object.
     * 
     * @param theBoard a Board object which contains the logic behind Tetris.
     * @param theGUI a TetrisGUI object as it eases working with new games.
     */
    public TetrisGamePanel(final Board theBoard, final TetrisGUI theGUI) {
        super();
        myGUI = theGUI;
        myBoard = theBoard;
        initialize();
        initialKeys();
        myCopyMap.putAll(myKeyMapping);

    }
    
    /**
     * Initializes various things within the TetrisGamePanel.
     */
    private void initialize() {
        setPreferredSize(new Dimension(INITIAL_P_WIDTH + 1, INITIAL_P_HEIGHT + 1));
        myListener = new KeyListener();
        addKeyListener(myListener);
        requestFocus();
        myAnimationSpeed = MOVE_DELAY;
        myTimer = new Timer(myAnimationSpeed, new MoveListener());
        myBlockData = initializeBlockData();
        myPaused = false;
        myGridEnable = false;
        myColorEnable = false;
        myLevel = 1;
        
        myCopyMap = new HashMap<Integer, Direction>();
        myKeyMapping = new HashMap<Integer, Direction>();
    }
    
    /**
     * The initial keys which will be used to play the game.
     */
    private void initialKeys() {
        // Keys that moves pieces left.
        myKeyMapping.put(KeyEvent.VK_A, Direction.LEFT);
        myKeyMapping.put(KeyEvent.VK_LEFT, Direction.LEFT);
        
        // Keys that moves pieces right.
        myKeyMapping.put(KeyEvent.VK_D, Direction.RIGHT);
        myKeyMapping.put(KeyEvent.VK_RIGHT, Direction.RIGHT);
        
        // Keys that move pieces down. 
        myKeyMapping.put(KeyEvent.VK_S, Direction.DOWN);
        myKeyMapping.put(KeyEvent.VK_DOWN, Direction.DOWN);
        
        // Keys that rotate a piece CW.
        myKeyMapping.put(KeyEvent.VK_W, Direction.ROTATE_CW);
        myKeyMapping.put(KeyEvent.VK_UP, Direction.ROTATE_CW);
        
        // Keys that drop a piece.
        myKeyMapping.put(KeyEvent.VK_SPACE, Direction.DROP);
        

    }
    
    /**
     * Initializes the BlockData.
     * 
     * @return a list of empty arrays relating to the size of the board.
     */
    private List<Block[]> initializeBlockData() {
        final List<Block[]> blockData = new ArrayList<Block[]>();
        for (int i = 0; i < myBoard.getHeight() + LIST_SZ; i++) {
            blockData.add(new Block[ARRAY_SZ]);
        }
        
        return blockData;
    }
    
    /** 
     * Starts the timer. 
     */
    public void start() {
        myTimer.start();
    }
    
    /** 
     * Sends a map of all keys that are currently mapped with their associated direction.
     * 
     * @return a Map consisting of Integers (key presses), and a Direction.
     */
    public Map<Integer, Direction> getKeyMap() {
        return myKeyMapping;
    }
    
    /**
     * Returns the listener attached to the game panel.
     * 
     * @return a KeyListener which is attached to this game's panel.
     */
    public KeyListener getListener() {
        return myListener;
    }
    
    @Override
    public void update(final Observable theObservable, final Object theObject) {        
        if (theObject instanceof List) {
            myBlockData = (List<Block[]>) theObject;
            myGUI.setSpeedString(createSpeedString());
            repaint();
        } else if (theObject instanceof Boolean) {
            final boolean gameflag =  (boolean) theObject;
            myBoard = new Board();
            if (gameflag) {
                gameover();
                myBlockData = new ArrayList<Block[]>();
            }
        } else if (theObject instanceof Map) {
            final Map<?, ?> scoreData = (Map<?, ?>) theObject;
            
            final Map<ScoreEnum, Integer> scoreInfo = checkMap(scoreData);
            
            final int level = scoreInfo.get(ScoreEnum.LEVEL);
            if (level >= myLevel) {
                if (myLevel == 1) {
                    myAnimationSpeed = MOVE_DELAY;
                } else {
                    myAnimationSpeed = MOVE_DELAY - SPEED_PER_LEVEL * (myLevel - 1);
                }
                
                if (myAnimationSpeed < 0) {
                    myAnimationSpeed = SPEED_PER_LEVEL;
                }
                
                myTimer.setDelay(myAnimationSpeed);
            }
            myLevel = scoreInfo.get(ScoreEnum.LEVEL);
        }
    }
    
    /**
     * Creates a string representing the current speed of the game.
     * 
     * @return a string representing the current speed of the game.
     */
    public String createSpeedString() {
        return "Current Speed: " + myAnimationSpeed + "ms";
    }
    
    /**
     * Returns animations speed.
     * 
     * @return an integer based on how fast the animation is stepping.
     */
    public int getSpeed() {
        return myAnimationSpeed;
    }
    
    /**
     * Checks a map for types ScoreEnum and Integer.
     * 
     * @param theMap a Map of generic types.
     * @return a Map of types ScoreEnum and Integer.
     */
    private Map<ScoreEnum, Integer> checkMap(final Map<?, ?> theMap) {
        final Map<ScoreEnum, Integer> scoreInfo = new HashMap<ScoreEnum, Integer>();
        // A check for the map.
        for (final Map.Entry<?, ?> entry : theMap.entrySet()) {
            if (entry.getKey().getClass() == ScoreEnum.class 
                            && entry.getValue().getClass() == Integer.class) {
                final ScoreEnum key = (ScoreEnum) entry.getKey();
                final Integer val = (Integer) entry.getValue();
                scoreInfo.put(key, val);
            }
        }
        
        return scoreInfo;
    }
    
    /**
     * It's game over.
     */
    public void gameover() {
        
        endgame();
        
        final JFrame gameOverWindow = new JFrame();
        gameOverWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final int answer = JOptionPane.showConfirmDialog(gameOverWindow,
                                      "Game Over!\nNew Game?",
                                      "Tetris",
                                      JOptionPane.YES_NO_OPTION);
        
        if (answer == JOptionPane.YES_OPTION) {
            newgame();
        } else if (answer == JOptionPane.NO_OPTION) {
            myGUI.enableNewAction(true);
        }
    }
    
    
    /**
     * Starts a new game.
     */
    public void newgame() {
        myAnimationSpeed = MOVE_DELAY;
        myTimer = new Timer(myAnimationSpeed, new MoveListener());
        myTimer.start();
        myGUI.enableNewAction(false);
        myGUI.newBoard();
        myKeyMapping.putAll(myCopyMap);
    }
    
    /**
     * Ends the game, no matter where at.
     */
    public void endgame() {
        myTimer.stop();
        myPaused = false;
        myCopyMap.putAll(myKeyMapping);
        myKeyMapping.clear();
    }
    
    /**
     * Pauses the game.
     */
    public void pause() {
        
        // when the game is paused
        if (myPaused) {
            myCopyMap.putAll(myKeyMapping);
            myKeyMapping.clear();
            myTimer.stop();
        //when the game isn't paused.
        } else {
            myKeyMapping.putAll(myCopyMap);
            myCopyMap.clear();
            myTimer.start();
            myBoard.step();
        }

    }
    
    /**
     * Changes the board of the game panel. 
     * 
     * @param theBoard a new Board.
     */
    public void changeBoard(final Board theBoard) {
        myBoard = theBoard;
    }
    
    /**
     * Changes keybind map.
     * 
     * @param theKeyMap a Map of new keybinds and their directions.
     */
    public void changeKeybinds(final Map<Integer, Direction> theKeyMap) {
        myKeyMapping = theKeyMap;
    }
    /**
     * This will determine if grid mode will be enabled on the game panel.
     * 
     * @param theFlag a boolean value which will determine if the grid mode will be enabled.
     */
    public void setGridEnable(final boolean theFlag) {
        myGridEnable = theFlag;
    }
    
    /**
     * This will determine if color madness mode will be enabled for the tetris blocks.
     * 
     * @param theFlag a boolean value which will determine if blocks are in "color madness".
     */
    public void setColorMadnessEnable(final boolean theFlag) {
        myColorEnable = theFlag;
    }
    
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(Color.WHITE);
        g2d.fill(new Rectangle2D.Double(0, 0, INITIAL_P_WIDTH, INITIAL_P_HEIGHT));
        
        g2d.setPaint(Color.BLACK);
        g2d.draw(new Rectangle2D.Double(0, 0, INITIAL_P_WIDTH, INITIAL_P_HEIGHT));
        
        for (int i = 0; i < myBlockData.size(); i++) {
            for (int j = 0; j < myBlockData.get(i).length; j++) {
                
                /* Represents the point at where it should be 
                (flipped upside down and mirrored). */
                final Point aPoint = new Point(-i + 19, -j + 9);
                
                if (myGridEnable) {
                    g2d.setPaint(Color.BLACK);
                    
                    //Horizontal line.
                    g2d.draw(new Line2D.Double(0, 
                                               INITIAL_BLOCK_SZ * (i - LIST_SZ + 1),
                                               INITIAL_BLOCK_SZ * myBlockData.get(i).length,
                                               INITIAL_BLOCK_SZ * (i - LIST_SZ + 1)));
                    
                    // Draws vertical lines 
                    g2d.draw(new Line2D.Double(INITIAL_BLOCK_SZ * j, 0,
                                               INITIAL_BLOCK_SZ * j, 
                                               INITIAL_BLOCK_SZ 
                                               * (myBlockData.size() - LIST_SZ)));
                }
                
                if (myBlockData.get(i)[j] != null) {
                    final DrawableBlock aDrawableBlock = new DrawableBlock(aPoint,
                                                                   myBlockData.get(i)[j]);
                    if (myColorEnable) {
                        g2d.setPaint(aDrawableBlock.colorMadness());
                    } else {
                        g2d.setPaint(aDrawableBlock.determineColor());
                    }
                    g2d.fill(aDrawableBlock.getShape());
                    
                    //Draws outline of a block. 
                    g2d.setPaint(Color.BLACK);
                    g2d.draw(new DrawableBlock(aPoint,
                                               myBlockData.get(i)[j]).getShape());
                }
            }
        }
        
    }
    
    /**
     * An extension of Keyboard adapter (an abstract class that implements KeyboardListener).
     * Listens to keyboard events and does stuff to the game board based on key input.
     * 
     * @author cjjaxx
     * @version 21 November 2017
     */
    public class KeyListener extends KeyAdapter {
        
        /**
         * Listens to the keyboard and does things.
         * 
         *  @param theEvent the Keyboard event being listened to.
         */
        public void keyPressed(final KeyEvent theEvent) {

            Direction aDirection = Direction.NONE;
            final int keyPress = theEvent.getKeyCode();
            if (myKeyMapping.containsKey(keyPress)) {
                aDirection = myKeyMapping.get(keyPress);
                
                if (aDirection == Direction.LEFT) {
                    myBoard.right();
                } else if (aDirection == Direction.RIGHT) {
                    myBoard.left();
                } else if (aDirection == Direction.DOWN) {
                    myBoard.down();
                } else if (aDirection == Direction.ROTATE_CW) {
                    myBoard.rotateCW();
                } else if (aDirection == Direction.DROP) {
                    myBoard.drop();
                }
            
            }
            
            if (theEvent.getKeyCode() == KeyEvent.VK_P) {
                myPaused = !myPaused;
                pause();
            }
        }
        
    }
    
    /**
     * Private inner class which will step the timer forward.
     * 
     * @author cjjaxx
     * @version 21 November 2017
     */
    private class MoveListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myBoard.step();
        }
        
    }

}
