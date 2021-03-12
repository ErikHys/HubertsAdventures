package GlidingHubert;

import utils.Location;
import utils.Pair;
import utils.ReadAndWrite;

import javax.swing.*;
import java.util.ArrayList;

public class Hubert extends utils.Hubert {

    private final Velocity velocity;
    private final int[][] windVert;
    private final int[][] windHor;
    private final int[][][] actionValue;
    private final Pair<Integer, Location>[][][] model;
    private final ArrayList<Pair<LeftRight, Location>> visitedStateActions;
    private final double alpha;
    private final double lam;

    public Hubert(int startX, int startY, int width, int height, int[][] grid, int[][] windVert, int[][] windHor, double lambda, double alpha) {
        super(startX, startY, width, height, grid);
        this.velocity = new Velocity(0, 0, 0);
        this.windVert = windVert;
        this.windHor = windHor;
        this.alpha = alpha;
        this.lam = lambda;
        actionValue = new int[height][width][2];
        model = new Pair[height][width][2];
        visitedStateActions = new ArrayList<Pair<LeftRight,Location>>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int a = 0; a < 2; a++) {
                    if(y == 14 && x == 54) {
                        actionValue[y][x][a] = 1;
                    }
                    else {
                        actionValue[y][x][a] = 0;
                    }
                }
            }
        }

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
     * DynaQ algorithm for Hubert to do
     * @param epsilon Epsilon for epsilon greedy choosing action
     * @param n n updates per step
     * @return Current location of hubert after step
     */
    public Location dynaQ(double epsilon, int n){
        // oldState <- current state
        // action <- epsilon greedy on state actionValue
        // Save newState and reward
        Location oldState = new Location(x, y);
        LeftRight action = getMaxAction(oldState);
        Location newState = doAction(action);
        int reward = (x == 54 && y == 15) ? 1 : 0;

        // Add state action combination to a list of visited combinations
        if(model[oldState.y][oldState.x][action.getValue()] == null){
            visitedStateActions.add(new Pair<LeftRight, Location>(action, newState));
        }

        // Update the Q
        updateQSA(oldState, action, reward, newState);
        //Update model
        model[oldState.y][oldState.x][action.getValue()] = new Pair<Integer, Location>(reward, newState);

        // n updates using model
        for (int i = 0; i < n; i++) {
            Pair<LeftRight, Location> randomActionState = visitedStateActions.get(random.nextInt(visitedStateActions.size()));
            Location s = randomActionState.getB();
            LeftRight a = randomActionState.getA();
            Pair<Integer, Location> rewardState = model[s.y][s.x][a.getValue()];
            int r = rewardState.getA();
            Location sPrime = rewardState.getB();
            updateQSA(s, a, r, sPrime);
        }

        return newState;
    }

    /**
     * Updates avtionValue according to Q(S, A) <- Q(S, A) + alpha*(R + lambda*maxQ(S', a) - Q(S, A))
     * @param oldState S in the above formula
     * @param action A in the above formula
     * @param reward R in the above formula
     * @param newState S' in the above formula
     */
    private void updateQSA(Location oldState, LeftRight action, int reward, Location newState) {
        actionValue[oldState.y][oldState.y][action.getValue()] += alpha*(reward +
                lam*actionValue[newState.y][newState.x][getMaxAction(newState).getValue()]
                - actionValue[oldState.y][oldState.y][action.getValue()]);
    }

    /**
     *
     * @param state Sate s, location x, y
     * @return max a, the best action gives State s
     */
    private LeftRight getMaxAction(Location state) {
        return actionValue[state.y][state.x][0] > actionValue[state.y][state.x][1] ? LeftRight.LEFT : LeftRight.RIGHT;
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
            if( i < 0 || j < 0 || i >= height || j >= width || grid[i][j] == 0) return false;
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
