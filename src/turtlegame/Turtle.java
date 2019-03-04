
package turtlegame;

/**
 *
 * @author rats
 */

// class to control turtle on the game board
public class Turtle {

    // coordinates on the board
    private int x, y;


    
    Turtle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // getters for coordinates of the turtle
    public int getX() {
        return this.x;
    }

    public int getY() {
        return y;
    }

    // method changes the position of the turtle according to MoveDirection 
    // @ param moveDirection
    public void move(MoveDirection moveDirection) {

        switch (moveDirection) {
            case UP:
                this.x += 1;
                break;
            case DOWN:
                this.x -= 1;
                break;
            case LEFT:
                this.y += 1;
                break;
            case RIGHT:
                this.y -= 1;
                break;
        }
    }
}
