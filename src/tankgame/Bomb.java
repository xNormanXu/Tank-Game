package tankgame;

public class Bomb {
    private int x;
    private int y;
    private int life = 5;
    private boolean live = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }

    public void setX(int x) { this.x = x; }

    public int getY() { return y; }

    public void setY() { this.y = y; }

    public int getLife() { return life; }

    public void lifeMinus() {
        if(life == 0)
            live = false;
        else
            life--;
    }
}
