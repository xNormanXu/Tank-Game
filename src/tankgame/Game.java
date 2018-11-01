package tankgame;

import javax.swing.*;

public class Game extends JFrame {
    private Panel  panel = new Panel();

    public void start() {
        Thread thread = new Thread(panel);
        thread.start();
        this.add(panel);
        this.addKeyListener(panel);
        this.setTitle("Tank Game");
        this.setSize(806, 625);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        this.setVisible(true);

        new Thread(new Repaint());
    }

    private class Repaint implements Runnable {
        @Override
        public void run() {
            while(true) {
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String [] args) {
        new Game().start();
  }
}

