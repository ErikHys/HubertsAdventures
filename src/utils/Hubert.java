package utils;

import java.util.Random;

public class Hubert {


    protected final Random random;
    protected final int[][] grid;
    protected final int height;
    protected int y;
    protected final int width;
    protected int x;

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

    public void reset(int startX, int startY){
        x = startX;
        y = startY;
    }

    public boolean finished(int x, int y) {
        return this.x == x && this.y == y;
    }
}
