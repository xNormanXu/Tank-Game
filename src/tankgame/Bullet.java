package tankgame;

import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class Bullet implements Runnable {
    private int x;
    private int y;
    private int speed = 15;
    //private String direction;
    private boolean live = true;
    private BufferedImage img;
    private double angle;
    private int vx;
    private int vy;
   private boolean bulletPressed;
    public Bullet(int x, int y,int vx,int vy, double angle) {
        this.x = x;
        this.y = y;
        this.vx =vx;
        this.vy =vy;
        this.angle = angle;
    }

    public int getX() { return x; }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() { return y; }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() { return speed; }

    public void setSpeed(int speed) { this.speed = speed; }




    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public boolean getLive() { return live; }

    public void setLive(boolean live) { this.live = live; }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public boolean isBulletPressed() {
        return bulletPressed;
    }

    public void setBulletPressed(boolean bulletPressed) {
        this.bulletPressed = bulletPressed;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

                vx = (int) Math.round(6 * Math.cos(Math.toRadians(angle)));
                vy = (int) Math.round(6 * Math.sin(Math.toRadians(angle)));
                x += vx;
                y += vy;

            if(x < 0 || x > 800 || y < 0 || y > 800)
                live = false;
        }
    }



}
