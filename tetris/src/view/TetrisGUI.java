/* 
 * TCSS 305 Autumn 2017 - Assignment 6: Tetris
 */
package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import model.Board;
import score.ScoreEnum;
import score.ScoreV;
import sound.SoundEffects;

/**
 * Main panel for the Tetris GUI.
 * 
 * @author cjjaxx
 * @version 21 November 2017
 */
public class TetrisGUI extends JPanel implements Observer {
    
    /** Font size for the score font. */
    private static final int FONT_SZ = 14;
    
    /** Font for the score. */
    private static final Font SCORE_FONT = new Font(null, Font.PLAIN, FONT_SZ);
    
    /** Initial border size for this panel. */
    private static final int BORDER_SZ = 5;

    /** Randomly generated Serial ID. */
    private static final long serialVersionUID = -489620441548797466L;
    
    /** Stores the game panel of Tetris (where the game is). */
    private TetrisGamePanel myTetrisGamePanel;
    
    /** Stores frame where everything will be displayed. */
    private JFrame myFrame;

    /** Stores the game panel (where things are being dropped). */
    private TetrisPreviewPiece myTetrisPreviewPanel;

    /** An abstract action for enabling/disabling grid mode. */
    private AbstractAction myGridAction;

    /** An abstract action for enabling/disabling color madness mode. */
    private AbstractAction myColorMadnessAction;

    /** A List of Abstract Actions. */
    private List<AbstractAction> myActionList;

    /** An AbstracAction relating to the keybinding capability. */
    private AbstractAction myKeybindAction;
    
    /** A list of Actions which pertain to the game status (e.g. end, new, etc.) */
    private List<AbstractAction> myGameActionList;

    /** Action which starts a new game. */
    private AbstractAction myNewGameAction;
    
    /** Action which ends the game. */
    private AbstractAction myEndGameAction;
    
    /** Action which enables/disabled the sound effects. */
    private AbstractAction mySFXEnable;

    /** Text area which contains controls. */
    private JTextArea myTextArea;

    /** Key Mappings. */
    private Map<Integer, Direction> myKeyMap;
    
    /** The Tetris game board. */
    private Board myBoard;

    /** An Object which keeps track of scoring things. */
    private ScoreV myScore;

    /** Score data. (Lines cleared, score, etc.) */
    private Map<ScoreEnum, Integer> myScoreData;
    
    /** JLabel which contains the score you currently have. */
    private JLabel myScoreInfo; 
    
    /** JLabel which contains the level you are currently on. */
    private JLabel myLevelInfo;
    
    /** JLabel which contains the number of lines cleared. */
    private JLabel myLineInfo;

    /** JLabel which contains the number of lines until next level. */
    private JLabel myLinesForLevelInfo;
    
    /** JLabel which contains the speed of the game. */
    private JLabel mySpeedInfo;
    
    /** Holds a class which plays sound effects. */
    private SoundEffects mySounds;
    
    /**
     * Constructor for the base of the GUI.
     */
    public TetrisGUI() {
        super();
        initialize();
    }
    
    /** 
     * Initializes things (panels, observers, Board in use) used in TetrisGUI. 
     */
    private void initialize() {
        setupActions();
        initializeBoardStuff();

    }
    
    /**
     * Initializes stuff relating to the game board.
     */
    private void initializeBoardStuff() {
        myBoard = new Board();
        myScore = new ScoreV();
        mySounds = new SoundEffects();
        
        // initialize the map.
        myScoreData = new HashMap<ScoreEnum, Integer>();
        myScoreData.put(ScoreEnum.SCORE, 0);
        myScoreData.put(ScoreEnum.LEVEL, 1);
        myScoreData.put(ScoreEnum.LINES_CLEARED, 0);
        
        
        myTetrisPreviewPanel = new TetrisPreviewPiece();
        myTetrisGamePanel = new TetrisGamePanel(myBoard, this);
        
        myBoard.newGame();
        myKeyMap = myTetrisGamePanel.getKeyMap();
        setupPanel();
        
        myBoard.addObserver(mySounds);
        myBoard.addObserver(myTetrisGamePanel);
        myBoard.addObserver(myScore);
        
        myScore.addObserver(myTetrisGamePanel);
        myScore.addObserver(this);
        
        myFrame = new JFrame();
        myEndGameAction.setEnabled(false);
    }
    
    /**
     * Sets up actions for Tetris.
     */
    private void setupActions() {
        myGridAction = new AbstractAction("Enable Grid") {

            /** Randomly generated Serial ID. */
            private static final long serialVersionUID = -1285134797478274697L;
            private boolean myGridFlag;

            @Override
            public void actionPerformed(final ActionEvent theArg) {
                myTetrisGamePanel.setGridEnable(!myGridFlag);
                myGridFlag = !myGridFlag;
                repaint();
                
            }
            
        };
        
        myColorMadnessAction = new AbstractAction("Enable Color Madness") {
            
            /** Randomly generated Serial ID. */
            private static final long serialVersionUID = -8311314668067671137L;
            private boolean myColorFlag;

            @Override
            public void actionPerformed(final ActionEvent theArg) {
                myTetrisGamePanel.setColorMadnessEnable(!myColorFlag);
                myColorFlag = !myColorFlag;
                repaint();
            }
            
        };
        
        myKeybindAction = new AbstractAction("Keybindings") {

            /** Randomly generated Serial ID. */
            private static final long serialVersionUID = -7847116098584459218L;

            @Override
            public void actionPerformed(final ActionEvent theArg) {
                changeKeybinds();
            }
            
        };
        
        myEndGameAction = new AbstractAction("End Game") {

            /** Randomly generated Serial ID. */
            private static final long serialVersionUID = 7363376084926830655L;

            @Override
            public void actionPerformed(final ActionEvent theArg) {
                myFrame.removeKeyListener(myTetrisGamePanel.getListener());
                myTetrisGamePanel.endgame();
                
                myNewGameAction.setEnabled(true);
                myEndGameAction.setEnabled(false);
                
            }
            
        };
        
        myNewGameAction = new AbstractAction("New Game") {

            /** Randomly generated Serial ID. */
            private static final long serialVersionUID = 798162467663126549L;

            @Override
            public void actionPerformed(final ActionEvent theArg) {
                myFrame.addKeyListener(myTetrisGamePanel.getListener());
                myEndGameAction.setEnabled(true);
                myTetrisGamePanel.newgame();
                myTetrisGamePanel.start();
            }
            
        };
        
        mySFXEnable = new AbstractAction("Enable Sound") {

            /** Randomly generated Serial ID. */
            private static final long serialVersionUID = 1L;
            private boolean mySoundFlag;

            @Override
            public void actionPerformed(final ActionEvent theArg) {
                mySounds.soundEnable(mySoundFlag);
                mySoundFlag = !mySoundFlag;
                
            }
            
        };
        
        setupSettingsActions();
        setupGameActions();
    }
    
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        updateSpeedInfo();
        if (theObject instanceof Boolean) {
            myNewGameAction.setEnabled(true);
        }  else if (theObject instanceof Map) {
            final Map<?, ?> scoreData = (Map<?, ?>) theObject;
            
            // A check for the map.
            for (final Map.Entry<?, ?> entry : scoreData.entrySet()) {
                if (entry.getKey().getClass() == ScoreEnum.class 
                                && entry.getValue().getClass() == Integer.class) {
                    final ScoreEnum key = (ScoreEnum) entry.getKey();
                    final Integer val = (Integer) entry.getValue();
                    myScoreData.put(key, val);
                }
            }
            updateScorePanel();
        }
        
        
    }
    
    /**
     * Creates a scoring info panel.
     */
    private void createScoringInfo() {
        final JFrame scoring = new JFrame("Scoring System");
        final JTextArea scoringtext = new JTextArea();
        
        scoringtext.setEditable(false);
        scoringtext.setFocusable(false);
        scoringtext.setOpaque(false);
        scoringtext.setText(setupScoringString().toString());
        scoringtext.setFont(SCORE_FONT);
        
        scoring.add(scoringtext);
        scoring.pack();
        scoring.setVisible(true);
        scoring.setResizable(false);
        
        
    }
    
    /**
     * Creates a list of all settings actions.
     */
    private void setupSettingsActions() {
        myActionList = new ArrayList<AbstractAction>();
        myActionList.add(mySFXEnable);
        myActionList.add(myGridAction);
        myActionList.add(myColorMadnessAction);
    }
    
    /**
     * Creates a list of all game actions.
     */
    private void setupGameActions() {
        myGameActionList = new ArrayList<AbstractAction>();
        myGameActionList.add(myNewGameAction);
        myGameActionList.add(myEndGameAction);
    }
    
    /**
     * Creates a new board to be used.
     */
    public void newBoard() {
        myBoard = new Board();
        
        myBoard.addObserver(myTetrisGamePanel);
        myBoard.addObserver(myTetrisPreviewPanel);
        myBoard.addObserver(myScore);
        myBoard.addObserver(mySounds);
        
        myScore.resetInfo();
        myBoard.newGame();
        myTetrisGamePanel.changeBoard(myBoard);
    }
    
    /**
     * Updates the score panel.
     */
    private void updateScorePanel() {
        myScoreInfo.setText(createScoreString());
        myLineInfo.setText(createLineString());
        myLevelInfo.setText(createLevelString());
        myLinesForLevelInfo.setText(createLineLevelString());
        updateSpeedInfo();
        
    }
    
    /**
     * Updates speed info displayed.
     */
    private void updateSpeedInfo() {
        mySpeedInfo.setText(myTetrisGamePanel.createSpeedString());
    }
    /**
     * Creates a panel which displays info from the score.
     * 
     * @return a JPanel of score information.
     */
    private JPanel createScorePanel() {
        myScoreInfo = new JLabel(createScoreString());
        myScoreInfo.setFont(SCORE_FONT);
        
        myLineInfo = new JLabel(createLineString());
        myLineInfo.setFont(SCORE_FONT);
        
        myLevelInfo = new JLabel(createLevelString());
        myLevelInfo.setFont(SCORE_FONT);
        
        myLinesForLevelInfo = new JLabel(createLineLevelString());
        myLinesForLevelInfo.setFont(SCORE_FONT);
        
        mySpeedInfo = new JLabel(myTetrisGamePanel.createSpeedString());
        mySpeedInfo.setFont(SCORE_FONT);
        
        final JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        infoPanel.add(myScoreInfo);
        infoPanel.add(myLineInfo);
        infoPanel.add(myLevelInfo);
        infoPanel.add(myLinesForLevelInfo);
        infoPanel.add(mySpeedInfo);
        
        return infoPanel;
    }
    
    /**
     * Creates a string representing score.
     * @return a string representing the score.
     */
    private String createScoreString() {
        return "Score: " + myScoreData.get(ScoreEnum.SCORE);
        
    }
    
    /**
     * Creates a string representing the lines cleared.
     * @return a string representing the lines cleared.
     */
    private String createLineString() {
        return "Lines Cleared: " + myScoreData.get(ScoreEnum.LINES_CLEARED);
    }
    
    /**
     * Creates a string representing the current level.
     * @return a string representing the current level.
     */
    private String createLevelString() {
        return "Level: " + myScoreData.get(ScoreEnum.LEVEL);
    }
    
    /**
     * Creates a string representing score.
     * @return a string representing the score.
     */
    private String createLineLevelString() {
        Integer temp = myScoreData.get(ScoreEnum.LINES_UNTIL_NEXT_LVL);
        
        if (temp == null || temp == 0) {
            temp = ScoreV.getLineForLevel();
        }
        
        return "Next Level in: " + temp + " lines.";
        
    }
    
    /**
     * Changes keybinds.
     */
    private void changeKeybinds() {
        final JFrame keybind = new JFrame("Keybinds");
        keybind.getContentPane().setLayout(new BoxLayout(keybind.getContentPane(),
                                                         BoxLayout.Y_AXIS));
        
        final JTextArea howTo = new JTextArea("To remap keys, double click "
                        + "white boxes and \npress the key you wish to rebind to.");
        howTo.setEditable(false);
        howTo.setOpaque(false);
        howTo.setFocusable(false);
        
        keybind.add(howTo);
        keybind.setVisible(true);
        
        final Map<Integer, Direction> keymap =  myTetrisGamePanel.getKeyMap();
        final List<JTextArea> fields = new ArrayList<JTextArea>();
        final List<JTextArea> keys = new ArrayList<JTextArea>();
        final List<Integer> keyints = new ArrayList<Integer>();
        
        for (final Map.Entry<Integer, Direction> entry : keymap.entrySet()) {
            
            final JTextArea field = new JTextArea(entry.getValue().name());
            
            final int value = entry.getKey();
            final JTextArea textArea = new JTextArea(KeyEvent.getKeyText(value));
            field.setOpaque(false);
            field.setEditable(false);
            field.setFocusable(false);
            
            textArea.setEditable(false);
            textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
            if (!(fields.contains(field))) {
                keyints.add(entry.getKey());
                keys.add(textArea);
                fields.add(field);
            }
        }
        
        for (int i = 0; i < fields.size(); i++) {
            final JTextArea key = keys.get(i);
            final JTextArea field = fields.get(i);
            final int keyint = keyints.get(i);
            
            
            keybind.add(field);
            keybind.add(key);
           
            keys.get(i).addKeyListener(new KeyAdapter() {   
                @Override
                public void keyPressed(final KeyEvent arg0) {
                    if (!(myKeyMap.containsKey(arg0.getKeyCode()))) {
                        key.setText(KeyEvent.getKeyText(arg0.getKeyCode()));
                        final JTextArea akey = new JTextArea(KeyEvent.getKeyText
                                                             (arg0.getKeyCode()));     
                        final Direction oldDirection = keymap.get(keyint);
                        myKeyMap.put(arg0.getKeyCode(), oldDirection);
                        myKeyMap.remove(keyint, oldDirection);
                        keys.add(akey);
                        myTetrisGamePanel.changeKeybinds(keymap);
                        myKeyMap = myTetrisGamePanel.getKeyMap();
                        myKeyMap.values().removeIf(Objects::isNull);
                        updateTextArea();
                        
                    }
                }      
            });
        }
        keybind.pack();
        
        keybind.setResizable(false);
    }
    
    /**
     * Updates speed text.
     * 
     * @param theSpeedString the text to update the speed info.
     */
    public void setSpeedString(final String theSpeedString) {
        mySpeedInfo.setText(theSpeedString);
    }
    
    /**
     * Enables/disables the new game action.
     * 
     * @param theFlag a boolean which will determine if the action is enabled.
     */
    public void enableNewAction(final boolean theFlag) {
        myBoard.addObserver(myTetrisPreviewPanel);
        myNewGameAction.setEnabled(theFlag);
        myEndGameAction.setEnabled(!theFlag);
    }
    
    /**
     * Updates the JTextArea.
     */
    private void updateTextArea() {
        textThread();
    }
    
    /**
     * Sets up text for scoring algorithm.
     * 
     * @return a StringBuffer of scoring info.
     */
    private StringBuffer setupScoringString() {
        final StringBuffer scores = new StringBuffer(500);
        scores.append("Scoring System:\n" 
                        + "Freezing a piece in place will add 4 points to the score.\n"
                        + "If 1 line is cleared, the score is calculated as: 40 * (n)\n"
                        + "If 2 lines are cleared, the score is calculated as: 100 * (n)\n"
                        + "If 3 lines are cleared, the score is calculated as: 300 * (n)\n"
                        + "If 4 lines are cleared, the score is calculated as: 1200 * (n)\n"
                        + "\n(n represents the current level)");
        
        return scores;
    }
    
    /**
     * Sets up the string to be displayed for controls.
     * 
     * @return a StringBuffer consisting of the keybinds.
     */
    private StringBuffer setupControlString() {
        final String newln = "\n";
        
        final StringBuffer leftbttns = new StringBuffer("Left: ");
        final StringBuffer rightbttns =  new StringBuffer("Right: ");
        final StringBuffer dropbttns = new StringBuffer("Drop: ");
        final StringBuffer cwbttns = new StringBuffer("Rotate CW: ");
        final StringBuffer downbttns = new StringBuffer("Down: ");
        
        for (final Map.Entry<Integer, Direction> entry : myKeyMap.entrySet()) {
            if (entry.getValue() == Direction.LEFT) {
                leftbttns.append(KeyEvent.getKeyText(entry.getKey()));
                leftbttns.append(' ');
            } else if (entry.getValue() == Direction.RIGHT) {
                rightbttns.append(KeyEvent.getKeyText(entry.getKey()));
                rightbttns.append(' ');
            } else if (entry.getValue() == Direction.DOWN) {
                downbttns.append(KeyEvent.getKeyText(entry.getKey()));
                downbttns.append(' ');
            } else if (entry.getValue() == Direction.DROP) {
                dropbttns.append(KeyEvent.getKeyText(entry.getKey()));
                dropbttns.append(' ');
            } else if (entry.getValue() == Direction.ROTATE_CW) {
                cwbttns.append(KeyEvent.getKeyText(entry.getKey()));
                cwbttns.append(' ');
            }
            
                
        }
        // StringBuffer will be larger than a default of 16 characters. Up it to 32.
        final StringBuffer alltext = new StringBuffer(32);
        alltext.append("Controls:\n");
        alltext.append(leftbttns);
        alltext.append(newln);
        
        alltext.append(rightbttns);
        alltext.append(newln);
        
        alltext.append(dropbttns);
        alltext.append(newln);
        
        alltext.append(cwbttns);
        alltext.append(newln);
        
        alltext.append(downbttns);
        
        alltext.append("\nPause/Unpause: P");
        return alltext;
    }
    /**
     * Sets up a JTextArea which will display the keys.
     * 
     * @return a JTextArea which will display currently bound keys.
     */
    private JTextArea setupKeyTextArea() {
        final JTextArea keys = new JTextArea();
        keys.setEditable(false);
        keys.setFocusable(false);
        keys.setBorder(BorderFactory.createRaisedBevelBorder());
        keys.setText(setupControlString().toString());
        keys.setOpaque(false);
        return keys;
    }
    
    /**
     * Text area requires an extra thread to update dynamically as it depends on the EDT.
     * 
     * @param theNewText
     */
    private void textThread() {
        final SwingWorker<JTextArea, Void> worker = new SwingWorker<JTextArea, Void>() {

            @Override
            protected JTextArea doInBackground() throws Exception {
                return setupKeyTextArea();
            }
            
            @Override
            protected void done() {
                try {
                    myTextArea.setText(get().getText().toString());
                } catch (final ExecutionException | InterruptedException ignore) {
                    ignore.printStackTrace();
                }
            }
        };
        worker.execute();
    }
    
    /** 
     * Sets up the panel (TetrisGUI) which contains a TetrisGamePanel and TetrisPreviewPanel.
     */
    private void setupPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.black);
        add(myTetrisGamePanel, BorderLayout.WEST);
        
        
        final JPanel rightPanel = new JPanel();
        final JPanel outerRight = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        
        myTextArea = setupKeyTextArea();
        
        rightPanel.setAlignmentX(RIGHT_ALIGNMENT);
        
        rightPanel.add(myTetrisPreviewPanel);
        rightPanel.add(myTextArea);
        
        // rightPanel will not adjust on text differences with this mid-panel.
        final JPanel scorePanel = new JPanel();
        scorePanel.add(createScorePanel());
        rightPanel.add(scorePanel);
        
        outerRight.add(rightPanel);
        
        add(outerRight, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(BORDER_SZ, BORDER_SZ, BORDER_SZ, 0));
    }

    /**
     * Creates the Frame which all components will be located in.
     */
    public void createAndShowGUI() {
        myFrame = new JFrame("Tetris");
        myFrame.setContentPane(this);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setOpaque(false);
        myFrame.setJMenuBar(new MainGUIMenuBar(myActionList,
                                               myGameActionList,
                                               myKeybindAction).makeMenubar());
        myFrame.pack();
        myFrame.setResizable(false);
        myFrame.setFocusable(true);
        myFrame.setVisible(true);
        
        createScoringInfo();
    }

}
