package ZOlw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

public class ZolwPlansza extends JFrame {
    int poziomTrudnosci;
    public ZolwPlansza(int poziomTrudnosci){
        super("Żołw");
        this.poziomTrudnosci = poziomTrudnosci;
        initComponents(poziomTrudnosci);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("turtle-icon.png"));
        this.setSize(850,980);
        this.setResizable(false);
        this.setDefaultCloseOperation(3);
    }
    private void initComponents(int poziomTrudnosci){Container kontener = this.getContentPane();
        stworzPlansze(poziomTrudnosci);
        stworzListePolPustych(planszaDoGry);
        stworzListeZuczkow();
        planszaPanel = new JPanel(new GridLayout(planszaDoGry.length, planszaDoGry.length));

        timer = new Timer();
        timer.schedule( new Nagroda(), new Date(),4000);
        timerTextField = new TextField();
        timerTextField.setText(timer.toString());
        timerTextField.setEnabled(false);
        wynikTextField = new TextField();
        wynikTextField.setEnabled(false);
        wynikTextField.setText(Integer.toString(wynik));


        kontener.add(planszaPanel);
        for (int i = planszaDoGry.length - 1; i >= 0; i--){
            for (int j = planszaDoGry.length - 1; j >= 0; j--){
                planszaPanel.add(planszaDoGry[i][j]);
            }
        }
        gora = new PrzyciskRuchu("gora.jpg");
        gora.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    z.setKurs(z.NORTH);
                    if (!listaKamykow.contains(planszaDoGry[z.getX() + 1][z.getY()]) && (z.getX() + 1) < planszaDoGry.length) {
                        planszaDoGry[z.getX()][z.getY()].setIcon(new ImageIcon("grass.jpg"));
                        planszaDoGry[z.getX()][z.getY()].setStatus("puste");
                        if (planszaDoGry[z.getX() + 1][z.getY()].getStatus().equals("nagroda")){
                            wynik += 10;
                            wynikTextField.setText(Integer.toString(wynik));
                            planszaDoGry[z.getX() + 1][z.getY()].setStatus("puste");
                        }
                        else if (planszaDoGry[z.getX() + 1][z.getY()].getStatus().equals("trucizna")){
                            wynik -= 15;
                            wynikTextField.setText(Integer.toString(wynik));
                            planszaDoGry[z.getX() + 1][z.getY()].setStatus("puste");
                        }
                        z.idz();
                        polozZolwia();
                        sprawdzStanGry();
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
        dol = new PrzyciskRuchu("dol.jpg");
        dol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    z.setKurs(z.SOUTH);
                    if ((!listaKamykow.contains(planszaDoGry[z.getX() - 1][z.getY()]) && (z.getX() - 1) >= 0) || z.getX() == 0) {
                        planszaDoGry[z.getX()][z.getY()].setIcon(new ImageIcon("grass.jpg"));
                        planszaDoGry[z.getX()][z.getY()].setStatus("puste");
                        if (planszaDoGry[z.getX() - 1][z.getY()].getStatus().equals("nagroda")){
                            wynik += 10;
                            wynikTextField.setText(Integer.toString(wynik));
                            planszaDoGry[z.getX() + 1][z.getY()].setStatus("puste");
                        }
                        else if (planszaDoGry[z.getX() - 1][z.getY()].getStatus().equals("trucizna")){
                            wynik -= 15;
                            wynikTextField.setText(Integer.toString(wynik));
                            planszaDoGry[z.getX() - 1][z.getY()].setStatus("puste");
                            Toolkit.getDefaultToolkit().beep();
                        }
                        z.idz();
                        polozZolwia();
                        sprawdzStanGry();
                    } else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                }
                catch (java.lang.ArrayIndexOutOfBoundsException exc){
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        lewo =  new PrzyciskRuchu("lewo.jpg");
        lewo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    z.setKurs(z.WEST);
                    if (!listaKamykow.contains(planszaDoGry[z.getX()][z.getY() + 1]) && (z.getY() + 1) < planszaDoGry.length) {
                        planszaDoGry[z.getX()][z.getY()].setIcon(new ImageIcon("grass.jpg"));
                        planszaDoGry[z.getX()][z.getY()].setStatus("puste");
                        if (planszaDoGry[z.getX()][z.getY() + 1].getStatus().equals("nagroda")){
                            wynik += 10;
                            wynikTextField.setText(Integer.toString(wynik));
                            planszaDoGry[z.getX()][z.getY() + 1].setStatus("puste");
                        }
                        else if (planszaDoGry[z.getX()][z.getY() + 1].getStatus().equals("trucizna")){
                            wynik -= 15;
                            wynikTextField.setText(Integer.toString(wynik));
                            planszaDoGry[z.getX()][z.getY() + 1].setStatus("puste");
                        }
                        z.idz();
                        polozZolwia();
                        sprawdzStanGry();
                    } else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                }
                catch (java.lang.ArrayIndexOutOfBoundsException exc){
                        Toolkit.getDefaultToolkit().beep();
                    }

            }
        });

        prawo = new PrzyciskRuchu("prawo.jpg");
        prawo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    z.setKurs(z.EAST);
                    if ((!listaKamykow.contains(planszaDoGry[z.getX()][z.getY() - 1]) && (z.getY() - 1) >= 0) || z.getY() == 0) {
                        planszaDoGry[z.getX()][z.getY()].setIcon(new ImageIcon("grass.jpg"));
                        planszaDoGry[z.getX()][z.getY()].setStatus("puste");
                        if (planszaDoGry[z.getX()][z.getY() - 1].getStatus().equals("nagroda")){
                            wynik += 10;
                            wynikTextField.setText(Integer.toString(wynik));
                            planszaDoGry[z.getX()][z.getY() - 1].setStatus("puste");
                        }
                        else if (planszaDoGry[z.getX()][z.getY() - 1].getStatus().equals("trucizna")){
                            wynik -= 15;
                            wynikTextField.setText(Integer.toString(wynik));
                            planszaDoGry[z.getX()][z.getY() - 1].setStatus("puste");
                        }
                        z.idz();
                        polozZolwia();
                        sprawdzStanGry();

                    } else {
                        Toolkit.getDefaultToolkit().beep();
                    }
                }
                catch (java.lang.ArrayIndexOutOfBoundsException exc){
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        dodatkowyPanel.add(new Label("Wynik: "));
        kontener.add(dodatkowyPanel, BorderLayout.SOUTH);
        nowaGra = new JButton("Nowa Gra");
        nowaGra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ZolwPlansza(1).setVisible(true);
                wynik = 0;
                wynikTextField.setText(Integer.toString(wynik));
            }
        });
        dodatkowyPanel.setBackground(Color.CYAN);
        dodatkowyPanel.setSize(850, 80);
        dodatkowyPanel.add(wynikTextField);
        dodatkowyPanel.add(nowaGra);
        dodatkowyPanel.add(panelDlaPrzyciskow);
        panelDlaPrzyciskow.setSize(70, 50);
        panelDlaPrzyciskow.setBackground(Color.BLACK);
        panelDlaPrzyciskow.add(prawo, BorderLayout.EAST);
        panelDlaPrzyciskow.add(lewo, BorderLayout.WEST);
        panelDlaPrzyciskow.add(gora, BorderLayout.NORTH);
        panelDlaPrzyciskow.add(dol, BorderLayout.SOUTH);

       }

  private class PolePlanszy extends JLabel {
     private String status;
     public PolePlanszy(){
            this.setSize(50, 50);
            this.setIcon(new ImageIcon("grass.jpg"));
            this.setToolTipText("");
            this.status = "puste";
     }
     public void setPolePlanszy(String nazwaPliku){
         this.setSize(50, 50);
         this.setIcon(new ImageIcon(nazwaPliku));
     }

      public void setStatus(String status) {
          this.status = status;
      }

      public String getStatus() {
          return status;
      }
  }

    class PrzyciskRuchu extends JButton {
     public PrzyciskRuchu(String ikonka) {
         this.setIcon(new ImageIcon(ikonka));

     }
  }

  class Nagroda extends java.util.TimerTask{

     private Random r = new Random();
     @Override
     public void run() {
         PolePlanszy poleRandomowe = listaPolPustych.get(r.nextInt(listaPolPustych.size()-1));
         int zukRandomowy = r.nextInt(listaZuczkow.size());
         poleRandomowe.setIcon(new ImageIcon(listaZuczkow.get(zukRandomowy)));
         if(zukRandomowy == 7){
             poleRandomowe.setStatus("trucizna");
         }
         else
             poleRandomowe.setStatus("nagroda");
     }
  }

  private void sprawdzStanGry(){
        if (this.poziomTrudnosci < 3) {
            if (wynik == 100) {
                this.setVisible(false);
                new ZolwPlansza(this.poziomTrudnosci + 1).setVisible(true);
            }
        }
        else if (wynik >= 100){
            JOptionPane.showMessageDialog(rootPane, "Wygrałeś. Zagraj pomownie");
            nowaGra.doClick();
        }
  }

  private void stworzPlansze(int poziomTrudnosci) {
     planszaDoGry = new PolePlanszy[15][15];
     listaKamykow = new LinkedList<>();

     for (int i = 0; i < 15; i ++){
         for (int n = 0; n < 15; n ++){
             planszaDoGry[i][n] = new PolePlanszy();
         }
     }
     z = new Zolw(0, 0, 0, planszaDoGry.length, planszaDoGry.length);
     planszaDoGry[z.getX()][z.getY()].setIcon(new ImageIcon("little-turtle.jpg"));
     switch (poziomTrudnosci) {
         case 1:
             for (int y = 8; y < 13; y++){
                 int x = 4;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);
             }
             for (int y = 0; y < 4; y++){
                 int x = 1;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock2.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);
             }
             for (int x = 7; x < 12; x++){
                 int y = 5;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);
             }
             break;
         case 2:
             for (int y = 8; y < 13; y++){
                 int x = 4;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);
             }
             for (int y = 0; y < 4; y++){
                 int x = 1;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock2.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);

             }
             for (int x = 7; x < 12; x++){
                 int y = 5;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);
             }
             for (int x = 7; x < 11; x++){
                 int y = 7;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock2.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);
             }
             for (int y = 7; y < 11; y++){
                 int x = 10;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock2.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);
             }
             for (int x = 0; x < 3; x++){
                 int y = 10;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);
             }
             for (int y = 1; y < 8; y++){
                 int x = 12;
                 planszaDoGry[y][x].setIcon(new ImageIcon("rock2.jpg"));
                 listaKamykow.add(planszaDoGry[y][x]);
             }

             break;


         case 3:
             for (int y = 8; y < 13; y++){
                 int x = 4;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);
             }
             for (int y = 0; y < 4; y++){
                 int x = 1;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock2.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);
             }
             for (int x = 7; x < 12; x++){
                 int y = 5;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);
             }
             for (int x = 7; x < 11; x++){
                 int y = 7;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock2.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);
             }
             for (int y = 7; y < 11; y++){
                 int x = 10;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock2.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);
             }
             for (int x = 0; x < 3; x++){
                 int y = 10;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);
             }
             for (int y = 10; y < 15; y++){
                 int x = 12;
                 planszaDoGry[y][x].setIcon(new ImageIcon("rock2.jpg"));
                 listaKamykow.add(planszaDoGry[y][x]);
             }
             for (int x = 3; x < 6; x++){
                 int y = 4;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock2.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);
             }
             for (int x = 0; x < 4; x++){
                 int y = 6;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock2.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);
             }
             for (int y = 12; y < 15; y++){
                 int x = 6;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);
             }
             for (int y = 1; y < 7; y++){
                 int x = 13;
                 planszaDoGry[x][y].setIcon(new ImageIcon("rock1.jpg"));
                 listaKamykow.add(planszaDoGry[x][y]);
             }
             break;

         default:
                 break;
     }

    }

  private void stworzListePolPustych(PolePlanszy[][] plansza){
     listaPolPustych = new HashMap<>();
     int n = 0;
     for (int i = 0; i < plansza.length; i++){
         for (int j = 0; j < plansza.length; j++){
             if (!listaKamykow.contains(plansza[i][j])){
                 n++;
                 listaPolPustych.put(n,plansza[i][j]);
             }
         }
     }
  }

  private void polozZolwia(){
     planszaDoGry[z.getX()][z.getY()].setIcon(new ImageIcon("little-turtle.jpg"));
   }
   private void stworzListeZuczkow() {
     listaZuczkow = new HashMap<>();
     listaZuczkow.put(0, "beetle.jpg");
     listaZuczkow.put(1, "beetle1.jpg");
     listaZuczkow.put(2, "bug.jpg");
     listaZuczkow.put(3, "fly.jpg");
     listaZuczkow.put(4, "green-bug.jpg");
     listaZuczkow.put(5, "beetle1.jpg");
     listaZuczkow.put(6, "mint-bug.jpg");
     listaZuczkow.put(7, "red-bug.jpg");
    }


    public static void main(String[] args){
     new ZolwPlansza(3).setVisible(true);

    }
    private JPanel planszaPanel ;
    private PolePlanszy[][] planszaDoGry;

    public Timer timer;
    private int wynik = 0;
    private JButton nowaGra;
    private JPanel dodatkowyPanel = new JPanel();
    private JPanel panelDlaPrzyciskow = new JPanel(new BorderLayout());
    private HashMap <Integer, PolePlanszy> listaPolPustych ;
    private LinkedList<PolePlanszy> listaKamykow;
    private Zolw z;
    private TextField timerTextField, wynikTextField;
    private HashMap <Integer, String> listaZuczkow;
    private JButton prawo, lewo, gora, dol;

}
