package Turttle;

public class Turttle {
    private int x, y, xMax, yMax;
    private int direction;
    public final int NORTH = 0;
    public final int SOUTH = 2;
    public final int EAST = 1;
    public final int WEST = 3;

    Turttle(int x , int y, int direction, int xMax, int yMax){
        this.x = x;
        this.y = y;
        this.xMax = xMax;
        this.yMax = yMax;
        switch (direction){
            case 0:
                this.direction = NORTH;
                break;
            case 1:
                this.direction = EAST;
                break;
            case 2:
                this.direction = SOUTH;
                break;
            case 3:
                this.direction = WEST;
        }
    }

    public int getX(){
        return this.x;
    }

    public int getY() {
        return y;
    }


    public void setDirection (int a){
        this.direction = a;
    }

    public void go() {
        if (this.getX() < xMax && this.getY() < yMax && this.getY() >= 0 && this.getX() >= 0) {
            switch (this.direction) {
                case NORTH:
                    this.x += 1;
                    break;
                case EAST:
                    this.y -= 1;
                    break;
                case SOUTH:
                    this.x -= 1;
                    break;
                case WEST:
                    this.y += 1;
                    break;
                default:
                    System.out.println("Błąd");
            }
        }
    }

}
