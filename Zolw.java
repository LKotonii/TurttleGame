package ZOlw;

public class Zolw {
    private int x, y, xMax, yMax;
    private int kurs;
    public final int NORTH = 0;
    public final int SOUTH = 2;
    public final int EAST = 1;
    public final int WEST = 3;

    Zolw(int x , int y, int kurs, int xMax, int yMax){
        this.x = x;
        this.y = y;
        this.xMax = xMax;
        this.yMax = yMax;
        switch (kurs){
            case 0:
                this.kurs = NORTH;
                break;
            case 1:
                this.kurs = EAST;
                break;
            case 2:
                this.kurs = SOUTH;
                break;
            case 3:
                this.kurs = WEST;
        }
    }

    public int getX(){
        return this.x;
    }

    public int getY() {
        return y;
    }


    public void setKurs (int a){
        this.kurs = a;
    }

    public void idz() {
        if (this.getX() < xMax && this.getY() < yMax && this.getY() >= 0 && this.getX() >= 0) {
            switch (this.kurs) {
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
