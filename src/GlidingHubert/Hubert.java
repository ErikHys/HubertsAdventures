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

    private Location move(int newX, int newY){
        x = newX;
        y = newY;
        return new Location(x, y);
    }

    private Location doAction(LeftRight action){
        switch (action){
            case LEFT -> velocity.setvAirHor(-3);
            case RIGHT -> velocity.setvAirHor(3);
        }
        addWind();
        return doValidMove();
    }

    private Location doValidMove() {
        int newX = getX() + velocity.getHor();
        int newY = getY() + velocity.getVert();
        if(checkPath(newX, newY)){
            return move(newX, newY);
        }else {
            return move(13, 5);
        }
    }

    private boolean checkPath(int newX, int newY) {
        //TODO
        return true;
    }

    private void addWind() {
        velocity.setvWindHor(windHor[getY()][getX()]);
        velocity.setvWindVert(windVert[getY()][getX()]);
    }

}
