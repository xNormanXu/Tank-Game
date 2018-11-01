package tankgame;

public class Power {
    private int x;
    private int y;
    private int height = 30;
    private int width = 30;
    private boolean live = true;

    public Power(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean getLive() { return live; }

    public void setLive(boolean live) { this.live = live; }
}
