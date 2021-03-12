package GlidingHubert;

import utils.Location;

public class Hubert extends utils.Hubert {

    private final Velocity velocity;
    private final int[][] windVert;
    private final int[][] windHor;

    public Hubert(int startX, int startY, int width, int height, int[][] grid, int[][] windVert, int[][] windHor) {
        super(startX, startY, width, height, grid);
        this.velocity = new Velocity(0, 0, 0);
        this.windVert = windVert;
        this.windHor = windHor;
    }

    /**
     * Moves Hubert to  new location
     * @param newX new x location
     * @param newY new Y location
     * @return Location of Hubert
     */
    private Location move(int newX, int newY){
        x = newX;
        y = newY;
        return new Location(x, y);
    }

    /**
     * Hubert goes left or right based on params, adds wind.
     * @param action Left or Right
     * @return New location of Hubert
     */
    private Location doAction(LeftRight action){
        switch (action){
            case LEFT -> velocity.setvAirHor(-3);
            case RIGHT -> velocity.setvAirHor(3);
        }
        addWind();
        return doValidMove();
    }

    /**
     * Hubert does a valid move based on current velocity
     * @return Hubert's new location
     */
    private Location doValidMove() {
        int newX = getX() + velocity.getHor();
        int newY = getY() + velocity.getVert();
        if(checkPath(newX, newY)){
            return move(newX, newY);
        }else {
            return move(13, 5);
        }
    }

    /**
     * Checks if path is valid, fails if hubert crashes or goes outside map
     * @param newJ new X coordinate
     * @param newI new Y coordinate
     * @return true if path is valid, else false
     */
    public boolean checkPath(int newJ, int newI) {
        int i = y;
        int j = x;
        int deltaI = Math.abs(newI - i);
        int deltaJ = Math.abs(newJ - j);
        int n = deltaJ + deltaI + 1;
        int bigDeltaI = newI > i ? 1 : -1;
        int bigDeltaJ = newJ > j ? 1 : -1;
        int epsilon = deltaI - deltaJ;
        deltaI *= 2;
        deltaJ *= 2;
        while (n > 0){
            if(grid[i][j] == 0 || i >= height || j >= width || i < 0 || j < 0) return false;
            if (epsilon > 0){
                i += bigDeltaI;
                epsilon -= deltaJ;
                n--;
            }else if (epsilon < 0){
                j += bigDeltaJ;
                epsilon += deltaI;
                n--;
            }else {
                i += bigDeltaI;
                j += bigDeltaJ;
                epsilon += deltaI - deltaJ;
                n -= 2;
            }
        }
        return true;
    }

    /**
     * Adds wind to Hubert's velocity
     */
    private void addWind() {
        velocity.setvWindHor(windHor[getY()][getX()]);
        velocity.setvWindVert(windVert[getY()][getX()]);
    }

}
