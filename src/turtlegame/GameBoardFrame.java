package turtlegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

/**
 *
 * @author rats
 */
public class GameBoardFrame extends JFrame {

    /*
     * @param int lvlOfDifficulty basically says how many rocks we will have on the board
     */
    public GameBoardFrame(int lvlOfDifficulty) {
        super("Turtle");
        this.lvlOfDifficulty = lvlOfDifficulty;

        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/turtle-icon.png"));
        this.setSize(850, 1000);
        this.setResizable(false);
        this.setDefaultCloseOperation(3);
        initComponents(lvlOfDifficulty);
    }

    private void initComponents(int lvlOfDifficulty) {
        Container container = this.getContentPane();
        // create game board and lists with bugs and empty areas
        createBoard(lvlOfDifficulty);
        createEmptyAreasList(gameBoard);
        createBugList();
        boardPanel = new JPanel(new GridLayout(gameBoard.length, gameBoard.length));
        
        // initialize and schedle new timer object 
        timer = new Timer();
        timer.schedule(new BugTreat(), new Date(), 4500);
        
        scoreTextField = new TextField();
        // score text field should not be edited by user
        scoreTextField.setEnabled(false);
        scoreTextField.setText(Integer.toString(score));

        container.add(boardPanel);
        for (int i = gameBoard.length - 1; i >= 0; i--) {
            for (int j = gameBoard.length - 1; j >= 0; j--) {
                boardPanel.add(gameBoard[i][j]);
            }
        }
        up = new MoveButton("src/images/up.jpg");
        up.setSize(400, 50);
        up.addActionListener((ActionEvent e) -> {
            try {
                currentMoveDirection = MoveDirection.UP;

                if (moveIsPossible(currentMoveDirection)) {
                    moveTurtle(currentMoveDirection);
                    checkGameState();
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
                
            } catch (java.lang.ArrayIndexOutOfBoundsException exc) {
                Toolkit.getDefaultToolkit().beep();
            }
        });
        
        down = new MoveButton("src/images/down.jpg");
        down.setSize(400, 50);
        down.addActionListener((ActionEvent e) -> {
            try {
                currentMoveDirection = MoveDirection.DOWN;

                if (moveIsPossible(currentMoveDirection)) {
                    moveTurtle(currentMoveDirection);
                    checkGameState();
                } else {
                    Toolkit.getDefaultToolkit().beep();  
                }
                
            } catch (java.lang.ArrayIndexOutOfBoundsException exc) {
                Toolkit.getDefaultToolkit().beep();
            }
        });
        
        left = new MoveButton("/images/left.jpg");
        left.setSize(200, 50);
        left.addActionListener((ActionEvent e) -> {
            try {
                currentMoveDirection = MoveDirection.LEFT;

                if (moveIsPossible(currentMoveDirection)) {
                    moveTurtle(currentMoveDirection);
                    checkGameState();
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException exc) {

                Toolkit.getDefaultToolkit().beep();
                exc.getMessage();
            }
        });

        right = new MoveButton("src/images/right.jpg");
        right.setSize(200, 50);
        right.addActionListener((ActionEvent e) -> {
            try {
                currentMoveDirection = MoveDirection.RIGHT;

                if (moveIsPossible(currentMoveDirection)) {
                    moveTurtle(currentMoveDirection);
                    checkGameState();
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException exc) {
                Toolkit.getDefaultToolkit().beep();
            }
        });

        additionalPanel.add(new Label("score: "));
        container.add(additionalPanel, BorderLayout.SOUTH);
        newGame = new JButton("New Game");
        newGame.addActionListener((ActionEvent e) -> {
            makeCurrentWindowUnvisible();
            new GameBoardFrame(1).setVisible(true);
            score = 0;
            scoreTextField.setText(Integer.toString(score));
        });
        additionalPanel.setBackground(Color.BLACK);
        additionalPanel.setSize(850, 150);
        additionalPanel.add(scoreTextField, BorderLayout.WEST);
        additionalPanel.add(newGame, BorderLayout.CENTER);
        additionalPanel.add(buttonsPanel, BorderLayout.EAST);
        buttonsPanel.setSize(400, 150);
        buttonsPanel.setBackground(Color.BLACK);
        buttonsPanel.add(right, BorderLayout.EAST);
        buttonsPanel.add(left, BorderLayout.WEST);
        buttonsPanel.add(up, BorderLayout.NORTH);
        buttonsPanel.add(down, BorderLayout.SOUTH);

    }

    // the method checks if desired move is possible (if there is no rocks or game board borders on the path)
    private boolean moveIsPossible(MoveDirection moveDirection) {
        Turtle tempTurtle = new Turtle(this.turtle.getX(), this.turtle.getY());
        tempTurtle.move(moveDirection);
        return (!rocksList.contains(gameBoard[tempTurtle.getX()][tempTurtle.getY()])) && tempTurtle.getX() >= 0 && tempTurtle.getY() >= 0;
    }

    private void moveTurtle(MoveDirection moveDirection) {
        // current turtle's board field becomes empty
        this.gameBoard[turtle.getX()][turtle.getY()].setIcon(new ImageIcon("src/images/grass.jpg"));
        this.gameBoard[turtle.getX()][turtle.getY()].setStatus("empty");
        // the score changes depending on what did turtle eat
        this.score += this.scoreChangeAfterMove(moveDirection);
        this.scoreTextField.setText(Integer.toString(score));
        // turtle moves to desired position
        this.turtle.move(moveDirection);
        synchronizeTurtlePosition();
    }

    // method calculates change of the score after move (+10 if turtle eats bug or -15 i turtle eats poisoned bug)
    private int scoreChangeAfterMove(MoveDirection moveDirection) {
        Turtle tempTurtle = new Turtle(this.turtle.getX(), this.turtle.getY());
        tempTurtle.move(moveDirection);
        switch (this.gameBoard[tempTurtle.getX()][tempTurtle.getY()].getStatus()) {
            case "BugTreat":
                return +10;
            case "poison":
                return -15;
            default:
                return 0;
        }
    }

    // class for a board field extends JLabel 
    // holds String status (could be 'empty'/'poison'/'BugTreat')
    private class BoardField extends JLabel {

        private String status;

        // constructor creates empty board field with default icon of a grass 
        public BoardField() {
            this.setSize(50, 50);
            this.setIcon(new ImageIcon("src/images/grass.jpg"));

            this.status = "empty";
        }

        public void setBoardField(String fileName) {
            this.setIcon(new ImageIcon(fileName));
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    class MoveButton extends JButton {

        public MoveButton(String icon) {
            this.setIcon(new ImageIcon(icon));

        }
    }

    // class managing appearance of 'bugs' on the board
    class BugTreat extends java.util.TimerTask {

        private final Random r = new Random();

        @Override
        // methond overrided from TimerTask class 
        // method chooses random BoardField from the list of emty areas and then places there random 'bug' from the list of bugs
        public void run() {
            BoardField randomEmptyArea = emptyAreasList.get(r.nextInt(emptyAreasList.size() - 1));
            int randomBug = r.nextInt(bugsList.size());
            try {
                randomEmptyArea.setIcon(new ImageIcon(bugsList.get(randomBug)));
            } catch (java.lang.NullPointerException ex) {
                ex.getMessage();
            }
            if (randomBug == 7) {
                randomEmptyArea.setStatus("poison");
            } else {
                randomEmptyArea.setStatus("BugTreat");
            }
        }
    }

    private void makeCurrentWindowUnvisible() {
        this.setVisible(false);
    }

    // method checks if the player picked up enough of bugs to jump to the next level of difficulty; if score is bigger than 100, method opens new window with next level board
    private void checkGameState() {
        if (this.lvlOfDifficulty < maxLvlOfDifficulty) {
            if (score >= 100) {
                makeCurrentWindowUnvisible();
                JOptionPane.showMessageDialog(rootPane, "Good job! Let's try next level");
                new GameBoardFrame(this.lvlOfDifficulty + 1).setVisible(true);
            }
        } else if (score >= 100) {
            JOptionPane.showMessageDialog(rootPane, "Good job! You've done it. New game window will open in a second :)");
            newGame.doClick();
        }
    }

    private void createBoard(int lvlOfDifficulty) {
        this.gameBoard = new BoardField[15][15];
        this.rocksList = new LinkedList<>();

        for (int i = 0; i < 15; i++) {
            for (int n = 0; n < 15; n++) {
                gameBoard[i][n] = new BoardField();
            }
        }
        turtle = new Turtle(0, 0);
        synchronizeTurtlePosition();
        //TODO new algorithm for rocks appearance

        int maxNofRocks = 10;
        switch (lvlOfDifficulty) {
            case 1:
                maxNofRocks += 10;
                placeRocks(maxNofRocks);
                break;
            case 2:
                maxNofRocks += 10;
                placeRocks(maxNofRocks);

                break;

            case 3:
                maxNofRocks += 10;
                placeRocks(maxNofRocks);
                break;
            case 4:
                maxNofRocks += 10;
                placeRocks(maxNofRocks);
                break;
            case 5:
                maxNofRocks += 10;
                placeRocks(maxNofRocks);
                break;

            default:
                break;
        }

    }

    private void createEmptyAreasList(BoardField[][] board) {
        emptyAreasList = new HashMap<>();
        int n = 0;
        for (BoardField[] board1 : board) {
            for (int j = 0; j < board.length; j++) {
                if (!this.rocksList.contains(board1[j])) {
                    n++;
                    emptyAreasList.put(n, board1[j]);
                }
            }
        }
    }

    
    private void synchronizeTurtlePosition() {
        gameBoard[turtle.getX()][turtle.getY()].setIcon(new ImageIcon("src/images/little-turtle.jpg"));
    }

    /*
     * Method places rocks icons on the board at random localisation, and then adds those board fields to rocksList.
     * @param int maxNofRocks: how many rocks should be placed on the board
    */
    private void placeRocks(int maxNofRocks) {
        Random r = new Random();
        int tempX = 0;
        int tempY = 0;
        for (int i = 0; i <= maxNofRocks; i++) {
            tempX = r.nextInt(14);
            tempY = r.nextInt(14);
            //check if we can place rock in position gameBoard[tempX][tempY]
            if (!this.rocksList.contains(gameBoard[tempX][tempY]) && tempX > 0 && tempY > 0) {
                gameBoard[tempX][tempY].setIcon(new ImageIcon("src/images/rock1.jpg"));
                this.rocksList.add(gameBoard[tempX][tempY]);
            } else { // if one of generated pseudo random values is 0 or combination of them points to a board field, which is already in the rocksList, this case will be ignored, and maxNofRocks will be incremented by 1 to compensate ignored case and place as many rocks as should be placed
                
                maxNofRocks++;
            }
        }
    }

    private void createBugList() {
        this.bugsList = new HashMap<>();
        bugsList.put(0, "src/images/beetle.jpg");
        bugsList.put(1, "src/images/beetle1.jpg");
        bugsList.put(2, "src/images/bug.jpg");
        bugsList.put(3, "src/images/fly.jpg");
        bugsList.put(4, "src/images/green-bug.jpg");
        bugsList.put(5, "src/images/beetle1.jpg");
        bugsList.put(6, "src/images/mint-bug.jpg");
        bugsList.put(7, "src/images/red-bug.jpg");
    }

    public static void main(String[] args) {
        new GameBoardFrame(1).setVisible(true);

    }
    
    // declaring variables of the class
    
    private JPanel boardPanel;
    private BoardField[][] gameBoard;

    private MoveDirection currentMoveDirection;
    public Timer timer;
    private int score = 0;
    private JButton newGame;
    private final JPanel additionalPanel = new JPanel();
    private final JPanel buttonsPanel = new JPanel(new BorderLayout());
    private HashMap<Integer, BoardField> emptyAreasList;
    private LinkedList<BoardField> rocksList;
    private Turtle turtle;
    
    private TextField scoreTextField;
    private HashMap<Integer, String> bugsList;
    private MoveButton right, left, up, down;
    int lvlOfDifficulty;
    static final int maxLvlOfDifficulty = 5;

}
