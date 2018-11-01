package tankgame;

import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public class Tank implements Observer{
    private int x;
    private int y;
    private int vx;
    private int vy;
    private int speed = 2;
    private final int r = 6;
    private double angle;
    private int life = 6;
    private boolean live = true;
    private boolean powerup = false;
    private Bullet bullet = null;
    private Vector<Bullet> bb = new Vector<>();
    private BulletUp bulletUp = null;
    private Vector<BulletUp> bbu = new Vector<>();
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean sPressed;
    private BufferedImage img;

    public Tank(int x, int y,int vx,int vy, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.vx = vx;
        this.vy =vy;
    }

    public int getX() { return x; }

    public void setX(int x) { this.x = x; }

    public int getY() { return y; }

    public void setY(int y) { this.y = y; }

    public double getAngle() { return angle; }

    public void setAngle(double angle) { this.angle = angle; }

    public int getSpeed() { return speed; }

    public void setSpeed(int speed) { this.speed = speed; }

    public int getLife() { return life; }

    public void setLife(int life) { this.life = life; }

    public boolean getLive() { return live; }

    public void setLive(boolean live) { this.live = live; }

    public boolean getPowerup() { return powerup; }

    public void setPowerup(boolean powerup) { this.powerup = powerup; }

    public Bullet getBullet() { return bullet; }

    public void setBullet(Bullet bullet) { this.bullet = bullet; }

    public BulletUp getBulletUp() { return bulletUp; }

    public void setBulletUp(BulletUp bulletUp) { this.bulletUp = bulletUp; }

    public Vector<Bullet> getBb() { return bb; }

    public void setBb(Vector<Bullet> bb) { this.bb = bb; }

    public Vector<BulletUp> getBbu() { return bbu; }

    public void setBbu(Vector<BulletUp> bbu) { this.bbu = bbu; }

    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public boolean issPressed() {
        return this.sPressed =true;
    }

    public void moveUp() {
        vx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    public void moveDown() {
        vx = (int) Math.round(r * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(r * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
    }

    private void rotateLeft() {
        angle -= 10;
        if(angle < 0)
            angle += 360;
    }

    private void rotateRight() {
        angle += 10;
        if(angle > 359)
            angle -= 360;
    }

    public void update(Observable o, Object o1) {
        if(this.UpPressed)
            this.moveUp();
        if(this.DownPressed)
            this.moveDown();
        if(this.LeftPressed)
            this.rotateLeft();
        if(this.RightPressed)
            this.rotateRight();
    }

    public void checkBorder() {
        //left border
        if (x < 60)
            x = 60;
        //right border
        if (x >= 680)
            x = 680;
        //up border
        if (y < 60)
            y = 60;
        //down border
        if (y >= 680)
            y = 680;
        //breakable up
    }

    // collision for breakable wall: in progress
    public void checkWall(Vector <Wall> walls) {
        for(int i = 0; i < walls.size(); i++) {
            if(walls.get(i).getBreakable()) {
                // left up
                if(walls.get(i).getX() > this.x && walls.get(i).getX() < this.x + 32 && walls.get(i).getY() > this.y && walls.get(i).getY() < this.y + 32) {
                    this.x -= (this.x + 32 - walls.get(i).getX());
                    this.y -= (this.y + 32 - walls.get(i).getY());
                }
                // up middle
                if(this.x > walls.get(i).getX() && this.x < walls.get(i).getX() + 50 && this.y > walls.get(i).getY() - 32) {
                    this.y -= (this.y + 32 - walls.get(i).getY());
                }
                // right up
                if(walls.get(i).getX() + 50 > this.x && walls.get(i).getX() + 50 < this.x + 32 && walls.get(i).getY() > this.y && walls.get(i).getY() < this.y + 32) {
                    this.x += (walls.get(i).getX() + 50 - this.x);
                    this.y -= (this.y + 32 - walls.get(i).getY());
                }

                // left middle
                if(this.y > walls.get(i).getY() && this.y < walls.get(i).getY() + 50 && this.x > walls.get(i).getX() - 32) {
                    this.x -= (this.x + 32 - walls.get(i).getX());
                }
                // right middle
                if(this.y > walls.get(i).getY() && this.y < walls.get(i).getY() + 50 && this.x < walls.get(i).getX() + 50) {
                    this.x += (walls.get(i).getX() + 50 - this.x);
                }

                // left down
                if(walls.get(i).getX() > this.x && walls.get(i).getX() < this.x + 32 && walls.get(i).getY() + 50 > this.y && walls.get(i).getY() + 50 < this.y + 32) {
                    this.x -= (this.x + 32 - walls.get(i).getX());
                    this.y += (walls.get(i).getY() + 50 - this.y);
                }
                // down middle
                if(this.x > walls.get(i).getX() && this.x < walls.get(i).getX() + 50 && this.y < walls.get(i).getY() + 50) {
                    this.y += (walls.get(i).getY() + 50 - this.y);
                }
                // right down
                if(walls.get(i).getX() + 50 > this.x && walls.get(i).getX() + 50 < this.x + 32) {
                    this.x += (walls.get(i).getX() + 50 - this.x);
                    this.y += (walls.get(i).getY() + 50 - this.y);
                }
            }
        }
    }

    // collision for tank: In progress
    /*public void tankCollision(Tank tank) {
        if(tank.getX() - x < 32 && tank.getY() - y < 32) {
           this.x -= 5;
           this.y -= 5;
        }
        if(x < tank.getX() + 50 && y < tank.getY() - 50) {
            this.x -= 5;
            this.y -= 5;
        }
    }*/

    public void shotBullet() {
        for(int i = bb.size() - 1; i >= 0; i--) {
            if (!bb.get(i).getLive())
                bb.remove(i);
        }

        if (bb.size() < 100) {
                if(this.issPressed()) {

                    bullet = new Bullet(getX() + 16, getY() + 16, this.vx, this.vy, this.angle);
                    bb.add(bullet);
                }
                bb.add(bullet);
            }

        Thread thread = new Thread(bullet);
        thread.start();
    }

    public void shotBulletUp() {
        for(int i = bbu.size() - 1; i >= 0; i--) {
            if (!bbu.get(i).getLive())
                bbu.remove(i);
        }

        if(bbu.size() < 100) {
            if(this.issPressed()) {
                bulletUp = new BulletUp(getX(), getY(), this.vx, this.vy, this.angle);
                bulletUp.setLive(true);
                bbu.add(bulletUp);
            }
            bbu.add(bulletUp);
        }

        Thread thread = new Thread(bulletUp);
        thread.start();
    }

    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }
}

