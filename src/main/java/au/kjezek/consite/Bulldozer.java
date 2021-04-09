package au.kjezek.consite;

import java.util.function.Consumer;

/**
 * Simulation actor - the bulldozer
 */
public class Bulldozer {

    /** Current position. */
    private int row;

    /** Current position. */
    private int col;

    /** Direction. */
    private int direction;

    /** Create with default bulldozer position. */
    public Bulldozer() {
        this(0, -1, 1);
    }

    /**
     * Create bulldozer at certain positions.
     * @param row row of the map
     * @param col column of the map
     * @param direction directions are from 0 to 3 for NORTH, EAST, SOUTH, WEST
     */
    public Bulldozer(final int row, final int col, final int direction) {
        this.row = row;
        this.col = col;
        this.direction = direction;
    }

    /** Direction represented as a function, its execution will move the dozer in the direction. */
    private final Consumer<Integer> NORTH = step -> row = row - step;
    private final Consumer<Integer> SOUTH = step -> row = row + step;
    private final Consumer<Integer> WEST = step -> col = col - step;
    private final Consumer<Integer> EAST = step -> col = col + step;

    private final Consumer<Integer>[] ROTATION = new Consumer[] {NORTH, EAST, SOUTH, WEST};

    /**
     * Rotate the bulldozer left.
     */
    public void rotateLeft() {
        if (direction == 0) {
            direction = ROTATION.length - 1;
        } else {
            direction -= 1;
        }
    }

    /**
     * Rotate the bulldozer right.
     */
    public void rotateRight() {
        direction = (direction + 1) % ROTATION.length;
    }

    /**
     * Move the bulldozer by one step according to current direction.
     */
    public void step() {
        ROTATION[direction].accept(1);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
