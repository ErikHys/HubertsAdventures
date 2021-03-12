package utils;

import java.util.Random;

public class Hubert {


    protected final Random random;
    protected final int[][] grid;
    protected final int height;
    protected final int width;
    protected int y;
    protected int x;

    /**
     * Initialize a standard Hubert with a start position, and a grid
     * @param startX Start x coordinate
     * @param startY Start y coordinate
     * @param width Width of grid
     * @param height Height of grid
     * @param grid Grid that Hubert can move around
     */
    public Hubert(int startX, int startY, int width, int height, int[][] grid){
        x = startX;
        y = startY;
        this.width = width;
        this.height = height;
        random = new Random();
        this.grid = grid;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Reset function, only moves Hubert to specified coordinates
     * @param startX start X
     * @param startY start Y
     */
    public void reset(int startX, int startY){
        x = startX;
        y = startY;
    }

    /**
     * Check if Hubert has arrived at final location
     * @param x end x
     * @param y end y
     * @return True if Hubert is at specified location
     */
    public boolean finished(int x, int y) {
        return this.x == x && this.y == y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
