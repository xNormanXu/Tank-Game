package tankgame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public class Panel extends JPanel implements KeyListener, Runnable, Observer {

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private Tank tank1;
    private Tank tank2;
    private Map map;
    private Vector<Bomb> bombs = new Vector<>();
    private Image image1;
    private Image image2;
    private Image image3;
    private Image image4;
    private Image image5;

    public Panel() {
        tank1 = new Tank(100, 600,0,0,0);
        tank2 = new Tank(625, 600,0,0,0);

        map = new Map();

        image1 = new ImageIcon("resource\\image\\bomb_1.png").getImage();
        image2 = new ImageIcon("resource\\image\\bomb_2.png").getImage();
        image3 = new ImageIcon("resource\\image\\bomb_3.png").getImage();
        image4 = new ImageIcon("resource\\image\\bomb_4.png").getImage();
        image5 = new ImageIcon("resource\\image\\bomb_5.png").getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage world = new BufferedImage(900,900,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) g;

        g.fillRect(0, 0, 800, 800);
        Graphics buffer = world.createGraphics();
        this.drawMap(buffer);
        g2.drawImage(world,0,0,null);

        BufferedImage mini = world.getSubimage(0,0,900,900);

        if(this.tank1.getX() - 100 < 0)
            this.x1 = 0;
        if(this.tank1.getX() - 100 > 450)
            this.x1 = 450;
        if(this.tank1.getX() - 100 > 0 && this.tank1.getX() - 100 < 450)
            this.x1 = this.tank1.getX() - 100;
        if(this.tank1.getY() - 100 < 0)
            this.y1 = 0;
        if(this.tank1.getY() - 100 > 200)
            this.y1 = 200;
        if(this.tank1.getY() - 100 > 0 && this.tank1.getY() - 100 < 200)
            this.y1 = this.tank1.getY() - 100;
        BufferedImage leftSplit = world.getSubimage(this.x1, this.y1,450,700);

        if(this.tank2.getX() - 100 < 0)
            this.x2 = 0;
        if(this.tank2.getX() - 100 > 450)
            this.x2 = 450;
        if(this.tank2.getX() - 100 > 0 && this.tank2.getX() - 100 < 450)
            this.x2 = this.tank2.getX() - 100;
        if(this.tank2.getY() - 100 < 0)
            this.y2 = 0;
        if(this.tank2.getY() - 100 > 200)
            this.y2 = 200;
        if(this.tank2.getY() - 100 > 0 && this.tank2.getY() - 100 < 200)
            this.y2 = this.tank2.getY() - 100;
        BufferedImage rightSplit = world.getSubimage(this.x2, this.y2,450,700);

        g2.drawImage(leftSplit,0,0,null);
        g2.drawImage(rightSplit,450,0,null);

        g2.scale(.2,.2);
        g2.drawImage(mini,1650,2200,null);

        g2.dispose();
    }

    public void drawMap(Graphics g) {
        this.drawWall(g);
        this.drawPower(g);

        System.out.println(tank2.getLife());
        System.out.println(tank2.getLive());

        this.drawTank(g);
        this.drawTankLifeBar(tank1.getX(), tank1.getY(), tank1, g);
        tank1.update(null,tank1);

        this.drawTank(g);
        this.drawTankLifeBar(tank2.getX(), tank2.getY(), tank2, g);
        tank2.update(null,tank2);

        // check collision for breakable wall.
        //tank1.checkWall(map.getWalls());
        //tank2.checkWall(map.getWalls());

        this.drawBullet(g);
        this.drawBulletUp(g);
        this.drawBomb(g);
    }

    public void drawPower(Graphics g) {
        for(int i = 0; i < map.getPowers().size(); i++) {
            Power power = map.getPowers().get(i);

            if(power.getLive()) {
                g.setColor(Color.pink);
                g.fill3DRect(power.getX(), power.getY(), power.getWidth(), power.getHeight(), true);
            }
        }
    }

    public void drawWall(Graphics g) {
        for(int i = 0; i < map.getWalls().size(); i++) {
            Wall wall = map.getWalls().get(i);

            if(wall.getLive()) {
                if(wall.getBreakable()) {
                    g.setColor(Color.cyan);
                    g.fill3DRect(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight(), true);
                }
                else {
                    g.setColor(Color.gray);
                    g.fill3DRect(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight(), true);
                }
            }
        }
    }

    public void drawBullet(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        for(int i = 0; i < tank1.getBb().size(); i++) {
            Bullet bullet = tank1.getBb().get(i);

            if(bullet.getLive()) {
                try {
                    BufferedImage bul = ImageIO.read(new File("resource\\image\\Shell.gif"));
                    bullet.setImg(bul);
                    g2.drawImage(bul,bullet.getX(),bullet.getY(),null);
                    g2.drawImage(bul, 0,0, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
                tank1.getBb().remove(bullet);
        }

        for(int i = 0; i < tank2.getBb().size(); i++) {
            Bullet bullet = tank2.getBb().get(i);

            if(bullet.getLive()) {
                try {
                    BufferedImage bul = ImageIO.read(new File("resource\\image\\Shell.gif"));
                    bullet.setImg(bul);
                    g2.drawImage(bul,bullet.getX(),bullet.getY(),null);
                    g2.drawImage(bul, 0,0, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
                tank2.getBb().remove(bullet);
        }
    }

    public void drawBulletUp(Graphics g) {
        Graphics2D g2 =  (Graphics2D) g;

        for(int i = 0; i < tank1.getBbu().size(); i++) {
            BulletUp bulletUp = tank1.getBbu().get(i);

            if(bulletUp.getLive()) {
                try {
                    BufferedImage bul = ImageIO.read(new File("resource\\image\\Weapon.gif"));
                    bulletUp.setImg(bul);
                    g2.drawImage(bul,bulletUp.getX(),bulletUp.getY(),null);
                    g2.drawImage(bul, 0,0, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
                tank1.getBbu().remove(bulletUp);
        }

        for(int i = 0; i < tank2.getBbu().size(); i++) {
            BulletUp bulletUp = tank2.getBbu().get(i);

            if(bulletUp.getLive()) {
                try {
                    BufferedImage bul = ImageIO.read(new File("resource\\image\\Pickup.gif"));
                    bulletUp.setImg(bul);
                    g2.drawImage(bul, bulletUp.getX(), bulletUp.getY(), null);
                    g2.drawImage(bul, 0, 0, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
                tank2.getBbu().remove(bulletUp);
        }
    }

    public void drawBomb(Graphics g) {
        for(int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);

            // image: 128x128, tank: 40x40
            if(bomb.getLife() == 5)
                g.drawImage(image1, bomb.getX() - 44, bomb.getY() - 44, this);
            if(bomb.getLife() == 4)
                g.drawImage(image2, bomb.getX() - 44, bomb.getY() - 44, this);
            if(bomb.getLife() == 3)
                g.drawImage(image3, bomb.getX() - 44, bomb.getY() - 44, this);
            if(bomb.getLife() == 2)
                g.drawImage(image4, bomb.getX() - 44, bomb.getY() - 44, this);
            if(bomb.getLife() == 1)
                g.drawImage(image5, bomb.getX() - 44, bomb.getY() - 44, this);

            bomb.lifeMinus();
            if(bomb.getLife() == 0)
                bombs.remove(i);
        }
    }

    public void drawTank(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if(tank1.getLive() && tank1.getLife() != 0) {
            try{
                BufferedImage img = ImageIO.read(new File("resource\\image\\Tank1.gif"));
                tank1.setImg(img);
                AffineTransform rotation = AffineTransform.getTranslateInstance(tank1.getX(), tank1.getY());
                rotation.rotate(Math.toRadians(tank1.getAngle()), img.getWidth() / 2, img.getHeight() / 2);
                System.out.println(tank1.toString());
                System.out.println(img.getWidth()/2 +"," +img.getHeight()/2);
                g2.drawImage(img, rotation, null);
            }catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }

        if(tank2.getLive() && tank2.getLife() != 0) {
            try{
                BufferedImage img = ImageIO.read(new File("resource\\image\\Tank2.gif"));
                tank2.setImg(img);
                AffineTransform rotation = AffineTransform.getTranslateInstance(tank2.getX(), tank2.getY());
                rotation.rotate(Math.toRadians(tank2.getAngle()), img.getWidth() / 2, img.getHeight() / 2);
                g2.drawImage(img, rotation, null);
            }catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public void drawTankLifeBar(int x, int y, Tank tank, Graphics g) {
        g.setColor(Color.red);

        if(tank.getLife() == 6) {
            g.fill3DRect(x, y + 80, 10, 10, false);
            g.fill3DRect(x + 10, y + 80, 10, 10, false);
            g.fill3DRect(x + 20, y + 80, 10, 10, false);
            g.fill3DRect(x + 30, y + 80, 10, 10, false);
            g.fill3DRect(x + 40, y + 80, 10, 10, false);
            g.fill3DRect(x + 50, y + 80, 10, 10, false);
        }
        if(tank.getLife() == 5) {
            g.fill3DRect(x, y + 80, 10, 10, false);
            g.fill3DRect(x + 10, y + 80, 10, 10, false);
            g.fill3DRect(x + 20, y + 80, 10, 10, false);
            g.fill3DRect(x + 30, y + 80, 10, 10, false);
            g.fill3DRect(x + 40, y + 80, 10, 10, false);
        }
        if(tank.getLife() == 4) {
            g.fill3DRect(x, y + 80, 10, 10, false);
            g.fill3DRect(x + 10, y + 80, 10, 10, false);
            g.fill3DRect(x + 20, y + 80, 10, 10, false);
            g.fill3DRect(x + 30, y + 80, 10, 10, false);

        }
        if(tank.getLife() == 3) {
            g.fill3DRect(x, y + 80, 10, 10, false);
            g.fill3DRect(x + 10, y + 80, 10, 10, false);
            g.fill3DRect(x + 20, y + 80, 10, 10, false);
        }
        if(tank.getLife() == 2) {
            g.fill3DRect(x, y + 80, 10, 10, false);
            g.fill3DRect(x + 10, y + 80, 10, 10, false);
        }
        if(tank.getLife() == 1) {
            g.fill3DRect(x, y + 80, 10, 10, false);
        }
    }

    public void hitTank(Bullet bullet, Tank tank) {
        if(bullet.getX() > tank.getX() && bullet.getX() < tank.getX() + 30 && bullet.getY() > tank.getY() && bullet.getY() < tank.getY() + 30) {
            bullet.setLive(false);

            if(tank.getLife() != 0)
                tank.setLife(tank.getLife() - 1);

            Bomb bomb = new Bomb(tank.getX(), tank.getY());
            bombs.add(bomb);
        }
    }

    public void hitTank(BulletUp bulletUp, Tank tank) {
        if(bulletUp.getX() > tank.getX() - 20 && bulletUp.getX() < tank.getX() + 30 && bulletUp.getY() > tank.getY() - 20 && bulletUp.getY() < tank.getY() + 30) {
            bulletUp.setLive(false);

            if(tank.getLife() == 0)
                tank.setLive(false);
            else
                tank.setLife(tank.getLife() - 2);

            Bomb bomb = new Bomb(tank.getX(), tank.getY());
            bombs.add(bomb);
        }
    }

    public void hitWall(Bullet bullet) {
        for(int i = 0; i < map.getWalls().size(); i++) {
            Wall wall = map.getWalls().get(i);

            if(wall.getBreakable()) {
                if(bullet.getX() > wall.getX() && bullet.getX() < wall.getX() + 40 && bullet.getY() > wall.getY() && bullet.getY() < wall.getY() + 40) {
                    bullet.setLive(false);
                    wall.setLive(false);
                    map.getWalls().remove(i);
                    Bomb bomb = new Bomb(wall.getX(), wall.getY());
                    bombs.add(bomb);
                }
            }
            if(!wall.getBreakable()) {
                if(bullet.getX() > wall.getX() && bullet.getX() < wall.getX() + 40 && bullet.getY() > wall.getY() && bullet.getY() < wall.getY() + 40)
                    bullet.setLive(false);
            }
        }
    }

    public void hitWall(BulletUp bulletUp) {
        for(int i = 0; i < map.getWalls().size(); i++) {
            Wall wall = map.getWalls().get(i);

            if(wall.getBreakable()) {
                if(bulletUp.getX() > wall.getX() && bulletUp.getX() < wall.getX() + 40 && bulletUp.getY() > wall.getY() && bulletUp.getY() < wall.getY() + 40) {
                    bulletUp.setLive(false);
                    wall.setLive(false);
                    map.getWalls().remove(i);
                    Bomb bomb = new Bomb(wall.getX(), wall.getY());
                    bombs.add(bomb);
                }
            }

            if(!wall.getBreakable()) {
                if(bulletUp.getX() > wall.getX() && bulletUp.getX() < wall.getX() + 40 && bulletUp.getY() > wall.getY() && bulletUp.getY() < wall.getY() + 40)
                    bulletUp.setLive(false);
            }
        }
    }

    public void meetPower(Power power, Tank tank) {
        if(Math.abs(tank.getX() - power.getX()) < 30 && Math.abs(tank.getY() - power.getY()) < 30) {
            power.setLive(false);
            map.getPowers().remove(power);
            tank.setPowerup(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_W)
            tank1.toggleUpPressed();
        if(e.getKeyCode()==KeyEvent.VK_S)
            tank1.toggleDownPressed();
        if(e.getKeyCode()==KeyEvent.VK_A)
            tank1.toggleLeftPressed();
        if(e.getKeyCode()==KeyEvent.VK_D)
            tank1.toggleRightPressed();
        if(e.getKeyCode()==KeyEvent.VK_F) {
            if(tank1.getPowerup() && tank1.getBbu().size() <= 100)
                tank1.shotBulletUp();
            if(!tank1.getPowerup() && tank1.getBb().size() <= 100) {
                tank1.issPressed();
                tank1.shotBullet();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_UP)
            tank2.toggleUpPressed();
        if(e.getKeyCode()==KeyEvent.VK_DOWN)
            tank2.toggleDownPressed();
        if(e.getKeyCode()==KeyEvent.VK_LEFT)
            tank2.toggleLeftPressed();
        if(e.getKeyCode()==KeyEvent.VK_RIGHT)
            tank2.toggleRightPressed();
        if(e.getKeyCode()==KeyEvent.VK_J) {
            if(tank2.getPowerup() && tank2.getBbu().size() <= 100)
                tank2.shotBulletUp();
            if(!tank2.getPowerup() && tank2.getBb().size() <= 100) {
                tank2.issPressed();
                tank2.shotBullet();
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W)
            tank1.unToggleUpPressed();
        if (e.getKeyCode() == KeyEvent.VK_S)
            tank1.unToggleDownPressed();
        if (e.getKeyCode() == KeyEvent.VK_A)
            tank1.unToggleLeftPressed();
        if (e.getKeyCode() == KeyEvent.VK_D)
            tank1.unToggleRightPressed();
        if (e.getKeyCode() == KeyEvent.VK_UP)
            tank2.unToggleUpPressed();
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            tank2.unToggleDownPressed();
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            tank2.unToggleLeftPressed();
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            tank2.unToggleRightPressed();
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(int i = 0; i < tank1.getBb().size(); i++) {
                Bullet bullet = tank1.getBb().get(i);

                if(bullet.getLive()) {
                    if(tank2.getLive()) {
                        this.hitTank(bullet, tank2);
                        this.hitWall(bullet);
                    }
                }
            }
            for(int i = 0; i < tank2.getBbu().size(); i++) {
                BulletUp bulletUp = tank2.getBbu().get(i);

                if(bulletUp.getLive()) {
                    if(tank1.getLive()) {
                        this.hitTank(bulletUp, tank1);
                        this.hitWall(bulletUp);
                    }
                }
            }
            for(int i = 0; i < tank1.getBbu().size(); i++) {
                BulletUp bulletUp = tank1.getBbu().get(i);

                if(bulletUp.getLive()) {
                    if(tank2.getLive()) {
                        this.hitTank(bulletUp, tank2);
                        this.hitWall(bulletUp);
                    }
                }
            }
            for(int i = 0; i < tank2.getBb().size(); i++) {
                Bullet bullet = tank2.getBb().get(i);

                if(bullet.getLive()) {
                    if(tank1.getLive()) {
                        this.hitTank(bullet, tank1);
                        this.hitWall(bullet);
                    }
                }
            }
            if(!map.getPowers().isEmpty()) {
                for(int i = 0; i < map.getPowers().size(); i++) {
                    Power power = map.getPowers().get(i);

                    if(power.getLive()) {
                        if(tank1.getLive())
                            this.meetPower(power, tank1);
                        if(tank2.getLive())
                            this.meetPower(power, tank2);
                    }
                }
            }
            repaint();
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
