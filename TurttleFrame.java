package Turttle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

public class TurttleFrame extends JFrame {

    
    public TurttleFrame(int lvlOfDifficulty){
        super("Turttle");
        this.lvlOfDifficulty = lvlOfDifficulty;
        initComponents(lvlOfDifficulty);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("turtle-icon.png"));
        this.setSize(850,980);
        this.setResizable(false);
        this.setDefaultCloseOperation(3);
    }
    private void initComponents(int lvlOfDifficulty)
    {
        Container container = this.getContentPane();
        createBoard(lvlOfDifficulty);
        creatEmptyAreasList(gameBoard);
        createBugList();
        boardPanel = new JPanel(new GridLayout(gameBoard.length, gameBoard.length));

        timer = new Timer();
        timer.schedule( new RewardAppearance(), new Date(),4000);
        timerTextField = new TextField();
        timerTextField.setText(timer.toString());
        timerTextField.setEnabled(false);
        resultTextField = new TextField();
        resultTextField.setEnabled(false);
        resultTextField.setText(Integer.toString(result));


        container.add(boardPanel);
        for (int i = gameBoard.length - 1; i >= 0; i--){
            for (int j = gameBoard.length - 1; j >= 0; j--){
                boardPanel.add(gameBoard[i][j]);
            }
        }
        up = new MoveButton("up.jpg");
        up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    turttle.setDirection(turttle.NORTH);
                    if (!rocksList.contains(gameBoard[turttle.getX() + 1][turttle.getY()]) && (turttle.getX() + 1) < gameBoard.length) {
                        gameBoard[turttle.getX()][turttle.getY()].setIcon(new ImageIcon("grass.jpg"));
                        gameBoard[turttle.getX()][turttle.getY()].setStatus("puste");
                        if (gameBoard[turttle.getX() + 1][turttle.getY()].getStatus().equals("RewardAppearance")){
                            result += 10;
                            resultTextField.setText(Integer.toString(result));
                            gameBoard[turttle.getX() + 1][turttle.getY()].setStatus("puste");
                        }
                        else if (gameBoard[turttle.getX() + 1][turttle.getY()].getStatus().equals("poison")){
                            result -= 15;
                            resultTextField.setText(Integer.toString(result));
                            gameBoard[turttle.getX() + 1][turttle.getY()].setStatus("puste");
                        }
                        turttle.go();
                        putTurttle();
                        checkGameState();
                    }
                    else {
                    Toolkit.getDefaultToolkit().beep();
                    }
                }
                catch (java.lang.ArrayIndexOutOfBoundsException exc){
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        down = new MoveButton("down.jpg");
        down.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    turttle.setDirection(turttle.SOUTH);
                    if ((!rocksList.contains(gameBoard[turttle.getX() - 1][turttle.getY()]) && (turttle.getX() - 1) >= 0) || turttle.getX() == 0) {
                        gameBoard[turttle.getX()][turttle.getY()].setIcon(new ImageIcon("grass.jpg"));
                        gameBoard[turttle.getX()][turttle.getY()].setStatus("puste");
                        if (gameBoard[turttle.getX() - 1][turttle.getY()].getStatus().equals("RewardAppearance")){
                            result += 10;
                            resultTextField.setText(Integer.toString(result));
                            gameBoard[turttle.getX() + 1][turttle.getY()].setStatus("puste");
                        }
                        else if (gameBoard[turttle.getX() - 1][turttle.getY()].getStatus().equals("poison")){
                            result -= 15;
                            resultTextField.setText(Integer.toString(result));
                            gameBoard[turttle.getX() - 1][turttle.getY()].setStatus("puste");
                            Toolkit.getDefaultToolkit().beep();
                        }
                        turttle.go();
                        putTurttle();
                        checkGameState();
                    } else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                }
                catch (java.lang.ArrayIndexOutOfBoundsException exc){
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        left =  new MoveButton("left.jpg");
        left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    turttle.setDirection(turttle.WEST);
                    if (!rocksList.contains(gameBoard[turttle.getX()][turttle.getY() + 1]) && (turttle.getY() + 1) < gameBoard.length) {
                        gameBoard[turttle.getX()][turttle.getY()].setIcon(new ImageIcon("grass.jpg"));
                        gameBoard[turttle.getX()][turttle.getY()].setStatus("puste");
                        if (gameBoard[turttle.getX()][turttle.getY() + 1].getStatus().equals("RewardAppearance")){
                            result += 10;
                            resultTextField.setText(Integer.toString(result));
                            gameBoard[turttle.getX()][turttle.getY() + 1].setStatus("puste");
                        }
                        else if (gameBoard[turttle.getX()][turttle.getY() + 1].getStatus().equals("poison")){
                            result -= 15;
                            resultTextField.setText(Integer.toString(result));
                            gameBoard[turttle.getX()][turttle.getY() + 1].setStatus("puste");
                        }
                        turttle.go();
                        putTurttle();
                        checkGameState();
                    } else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                }
                catch (java.lang.ArrayIndexOutOfBoundsException exc){
                        Toolkit.getDefaultToolkit().beep();
                    }

            }
        });

        right = new MoveButton("right.jpg");
        right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    turttle.setDirection(turttle.EAST);
                    if ((!rocksList.contains(gameBoard[turttle.getX()][turttle.getY() - 1]) && (turttle.getY() - 1) >= 0) || turttle.getY() == 0) {
                        gameBoard[turttle.getX()][turttle.getY()].setIcon(new ImageIcon("grass.jpg"));
                        gameBoard[turttle.getX()][turttle.getY()].setStatus("puste");
                        if (gameBoard[turttle.getX()][turttle.getY() - 1].getStatus().equals("RewardAppearance")){
                            result += 10;
                            resultTextField.setText(Integer.toString(result));
                            gameBoard[turttle.getX()][turttle.getY() - 1].setStatus("puste");
                        }
                        else if (gameBoard[turttle.getX()][turttle.getY() - 1].getStatus().equals("poison")){
                            result -= 15;
                            resultTextField.setText(Integer.toString(result));
                            gameBoard[turttle.getX()][turttle.getY() - 1].setStatus("puste");
                        }
                        turttle.go();
                        putTurttle();
                        checkGameState();

                    } else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                }
                catch (java.lang.ArrayIndexOutOfBoundsException exc){
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        additionalPanel.add(new Label("result: "));
        container.add(additionalPanel, BorderLayout.SOUTH);
        newGame = new JButton("Nowa Gra");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TurttleFrame(1).setVisible(true);
                result = 0;
                resultTextField.setText(Integer.toString(result));
            }
        });
        additionalPanel.setBackground(Color.CYAN);
        additionalPanel.setSize(850, 80);
        additionalPanel.add(resultTextField);
        additionalPanel.add(newGame);
        additionalPanel.add(buttonsPanel);
        buttonsPanel.setSize(70, 50);
        buttonsPanel.setBackground(Color.BLACK);
        buttonsPanel.add(right, BorderLayout.EAST);
        buttonsPanel.add(left, BorderLayout.WEST);
        buttonsPanel.add(up, BorderLayout.NORTH);
        buttonsPanel.add(down, BorderLayout.SOUTH);

       }

  private class BoardField extends JLabel {
     private String status;
     public BoardField(){
            this.setSize(50, 50);
            this.setIcon(new ImageIcon("grass.jpg"));
            this.setToolTipText("");
            this.status = "puste";
     }
     public void setBoardField(String fileName){
         this.setSize(50, 50);
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

  class RewardAppearance extends java.util.TimerTask{

     private Random r = new Random();
     @Override
     public void run() {
         BoardField randomEmptyArea = emptyAreasList.get(r.nextInt(emptyAreasList.size()-1));
         int randomBug = r.nextInt(bugsList.size());
         randomEmptyArea.setIcon(new ImageIcon(bugsList.get(randomBug)));
         if(randomBug == 7){
             randomEmptyArea.setStatus("poison");
         }
         else
             randomEmptyArea.setStatus("RewardAppearance");
     }
  }

  private void checkGameState(){
        if (this.lvlOfDifficulty < 3) {
            if (result == 100) {
                this.setVisible(false);
                new TurttleFrame(this.lvlOfDifficulty + 1).setVisible(true);
            }
        }
        else if (result >= 100){
            JOptionPane.showMessageDialog(rootPane, "Wygrałeś. Zagraj pomownie");
            newGame.doClick();
        }
  }

  private void createBoard(int lvlOfDifficulty) {
     gameBoard = new BoardField[15][15];
     rocksList = new LinkedList<>();

     for (int i = 0; i < 15; i ++){
         for (int n = 0; n < 15; n ++){
             gameBoard[i][n] = new BoardField();
         }
     }
     turttle = new Turttle(0, 0, 0, gameBoard.length, gameBoard.length);
     gameBoard[turttle.getX()][turttle.getY()].setIcon(new ImageIcon("little-turtle.jpg"));
     switch (lvlOfDifficulty) {
         case 1:
             for (int y = 8; y < 13; y++){
                 int x = 4;
                 gameBoard[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 rocksList.add(gameBoard[x][y]);
             }
             for (int y = 0; y < 4; y++){
                 int x = 1;
                 gameBoard[x][y].setIcon(new ImageIcon("rock2.jpg"));
                 rocksList.add(gameBoard[x][y]);
             }
             for (int x = 7; x < 12; x++){
                 int y = 5;
                 gameBoard[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 rocksList.add(gameBoard[x][y]);
             }
             break;
         case 2:
             for (int y = 8; y < 13; y++){
                 int x = 4;
                 gameBoard[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 rocksList.add(gameBoard[x][y]);
             }
             for (int y = 0; y < 4; y++){
                 int x = 1;
                 gameBoard[x][y].setIcon(new ImageIcon("rock2.jpg"));
                 rocksList.add(gameBoard[x][y]);

             }
             for (int x = 7; x < 12; x++){
                 int y = 5;
                 gameBoard[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 rocksList.add(gameBoard[x][y]);
             }
             for (int x = 7; x < 11; x++){
                 int y = 7;
                 gameBoard[x][y].setIcon(new ImageIcon("rock2.jpg"));
                 rocksList.add(gameBoard[x][y]);
             }
             for (int y = 7; y < 11; y++){
                 int x = 10;
                 gameBoard[x][y].setIcon(new ImageIcon("rock2.jpg"));
                 rocksList.add(gameBoard[x][y]);
             }
             for (int x = 0; x < 3; x++){
                 int y = 10;
                 gameBoard[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 rocksList.add(gameBoard[x][y]);
             }
             for (int y = 1; y < 8; y++){
                 int x = 12;
                 gameBoard[y][x].setIcon(new ImageIcon("rock2.jpg"));
                 rocksList.add(gameBoard[y][x]);
             }

             break;


         case 3:
             for (int y = 8; y < 13; y++){
                 int x = 4;
                 gameBoard[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 rocksList.add(gameBoard[x][y]);
             }
             for (int y = 0; y < 4; y++){
                 int x = 1;
                 gameBoard[x][y].setIcon(new ImageIcon("rock2.jpg"));
                 rocksList.add(gameBoard[x][y]);
             }
             for (int x = 7; x < 12; x++){
                 int y = 5;
                 gameBoard[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 rocksList.add(gameBoard[x][y]);
             }
             for (int x = 7; x < 11; x++){
                 int y = 7;
                 gameBoard[x][y].setIcon(new ImageIcon("rock2.jpg"));
                 rocksList.add(gameBoard[x][y]);
             }
             for (int y = 7; y < 11; y++){
                 int x = 10;
                 gameBoard[x][y].setIcon(new ImageIcon("rock2.jpg"));
                 rocksList.add(gameBoard[x][y]);
             }
             for (int x = 0; x < 3; x++){
                 int y = 10;
                 gameBoard[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 rocksList.add(gameBoard[x][y]);
             }
             for (int y = 10; y < 15; y++){
                 int x = 12;
                 gameBoard[y][x].setIcon(new ImageIcon("rock2.jpg"));
                 rocksList.add(gameBoard[y][x]);
             }
             for (int x = 3; x < 6; x++){
                 int y = 4;
                 gameBoard[x][y].setIcon(new ImageIcon("rock2.jpg"));
                 rocksList.add(gameBoard[x][y]);
             }
             for (int x = 0; x < 4; x++){
                 int y = 6;
                 gameBoard[x][y].setIcon(new ImageIcon("rock2.jpg"));
                 rocksList.add(gameBoard[x][y]);
             }
             for (int y = 12; y < 15; y++){
                 int x = 6;
                 gameBoard[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 rocksList.add(gameBoard[x][y]);
             }
             for (int y = 1; y < 7; y++){
                 int x = 13;
                 gameBoard[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 rocksList.add(gameBoard[x][y]);
             }
             break;

         default:
                 break;
     }

    }

  private void creatEmptyAreasList(BoardField[][] board){
     emptyAreasList = new HashMap<>();
     int n = 0;
     for (int i = 0; i < board.length; i++){
         for (int j = 0; j < board.length; j++){
             if (!rocksList.contains(board[i][j])){
                 n++;
                 emptyAreasList.put(n,board[i][j]);
             }
         }
     }
  }

  private void putTurttle(){
     gameBoard[turttle.getX()][turttle.getY()].setIcon(new ImageIcon("little-turtle.jpg"));
   }
   private void createBugList() {
     bugsList = new HashMap<>();
     bugsList.put(0, "beetle.jpg");
     bugsList.put(1, "beetle1.jpg");
     bugsList.put(2, "bug.jpg");
     bugsList.put(3, "fly.jpg");
     bugsList.put(4, "green-bug.jpg");
     bugsList.put(5, "beetle1.jpg");
     bugsList.put(6, "mint-bug.jpg");
     bugsList.put(7, "red-bug.jpg");
    }


    public static void main(String[] args){
     new TurttleFrame(1).setVisible(true);

    }
    private JPanel boardPanel ;
    private BoardField[][] gameBoard;

    public Timer timer;
    private int result = 0;
    private JButton newGame;
    private JPanel additionalPanel = new JPanel();
    private JPanel buttonsPanel = new JPanel(new BorderLayout());
    private HashMap <Integer, BoardField> emptyAreasList ;
    private LinkedList<BoardField> rocksList;
    private Turttle turttle;
    private TextField timerTextField, resultTextField;
    private HashMap <Integer, String> bugsList;
    private MoveButton right, left, up, down;
    int lvlOfDifficulty;

}
