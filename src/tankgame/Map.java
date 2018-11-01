package tankgame;

import java.util.Vector;

public class Map {
    private Power power;
    private Vector<Power> powers = new Vector<>();
    private Wall wall;
    private static Vector<Wall> walls = new Vector<>();



    public Map (){
        this.generateWall();
        this.generatePower();
    }

    public Vector<Power> getPowers() { return powers; }

    public void setPowers(Vector<Power> powers) { this.powers = powers; }

    public static Vector<Wall> getWalls() { return walls; }

    public void setWalls(Vector<Wall> walls) { this.walls = walls; }






    public void generateWall() {
        //Tank t;
        for(int i = 0; i < 7; i++) {
            // up
            wall = new Wall(i * 50 + 100,100);
            wall.setBreakable(true);
            //if(wall.getX() >=  )

            walls.add(wall);


            // left
            wall = new Wall(200, i * 100 + 100);
            wall.setBreakable(true);
            walls.add(wall);

            // right
            wall = new Wall(500, i * 50 + 100);
            wall.setBreakable(true);
            walls.add(wall);
        }

        // up
        for(int i = 0; i < 16; i++) {
            wall = new Wall(i * 50, 0);
            walls.add(wall);
        }
        // down
        for(int i = 0; i < 16; i++) {
            wall = new Wall(i * 50, 750);
            walls.add(wall);
        }
        // left
        for(int i = 0; i < 14; i++) {
            wall = new Wall(0, (i +1) * 50);
            walls.add(wall);
        }
        // right
        for(int i = 0; i < 14; i++) {
            wall = new Wall(750, (i + 1) * 50);
            walls.add(wall);
        }
    }

    public void generatePower() {
        power = new Power(100,400);
        powers.add(power);
        power = new Power(600,400);
        powers.add(power);
    }
}
