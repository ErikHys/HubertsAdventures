package FactoryToHQ;

import utils.Location;

import java.util.ArrayList;
import java.util.Random;

public class Hubert {
    private int[][] neighborhood;
    private ChargingStations chargingStations;
    private int[][] dir_values;
    private final int nStep;
    private double alpha;
    private double[][][][] action_value;
    private int x;
    private int y;
    private int width;
    private int height;
    private Random random;
    private int charge;
    private int step;
    private int cost;
    private int lastX, lastY;
    private ArrayList<Location> lastMoves;
    private ArrayList<Integer> lastRewards;
    private ArrayList<Integer> lastCharges;



    public Hubert(int startX, int startY, int width, int height, int[][] neighborhood){
        nStep = 1;
        setUp(startX, startY, width, height, neighborhood);
    }

    public Hubert(int startX, int startY, int width, int height, int[][] neighborhood, int nStep){
        this.nStep = nStep;
        setUp(startX, startY, width, height, neighborhood);

    }

    private void setUp(int startX, int startY, int width, int height, int[][] neighborhood){
        x = startX;
        y = startY;
        charge = 10;
        step = 5;
        this.width = width;
        this.height = height;
        random = new Random();
        this.neighborhood = neighborhood;
        this.chargingStations = new ChargingStations();
        this.action_value = new double[height][width][11][4];
        for (int y = 0; y < action_value.length; y++) {
            for (int x = 0; x < action_value[y].length; x++) {
                for (int c = 0; c < action_value[y][x].length; c++) {
                    for (int a = 0; a < action_value[y][x][c].length; a++) {
                        if (x != 27 || y != 30){
                            action_value[y][x][c][a] = -1;
                        }else {
                            action_value[y][x][c][a] = 0;
                        }
                    }
                }
            }
        }
        dir_values = new int[][]{{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
        this.alpha = 1.0;
        lastMoves = new ArrayList<>();
        lastRewards = new ArrayList<>();
        lastCharges = new ArrayList<>();
    }

    public Location move(int locX, int locY, int move){
        lastX = x;
        lastY = y;
        x = locX;
        y = locY;
        Location newLoc = new Location(lastX, lastY, move);
        lastMoves.add(newLoc);
        lastRewards.add(cost);
        lastCharges.add(charge);
        while (lastMoves.size() > nStep){
            lastMoves.remove(0);
            lastRewards.remove(0);
            lastCharges.remove(0);
        }
        return new Location(x, y, move);
    }

    private boolean validLocation(int locX, int locY) {
        return locX < width && locX >= 0 && locY < height && locY >= 0 && neighborhood[locY][locX] != 0;
    }

    public Location epsilonGreedyMove(double epsilon){

        int r = chargingStation();
        this.cost += r;
        double p = random.nextDouble();
        Location location;

        if (p <= epsilon){
            location = randomMove();

        }else{
            location = getMaxMove();
        }

        if (nStep == 1){
            updateQSA();

        }
        else {
            if(lastMoves.size() == nStep){
                updateQSA();
            }

        }
        return location;
    }

    public Location manualMove(int move){
        Location location = move(x+dir_values[move][0], y+dir_values[move][1], move);
        updateQSA(location);
        return location;
    }

    private double updateQSA(Location location) {
        double qSA = action_value[lastY][lastX][charge][location.move];
        double qSPrimeAPrime = action_value[y][x][charge][maxMove()];
        action_value[lastY][lastX][charge][location.move]
                += alpha*(cost + qSPrimeAPrime - qSA);
        return alpha*(cost + qSPrimeAPrime - qSA);
    }

    private double updateQSA() {
        double qSA = action_value[lastMoves.get(0).y][lastMoves.get(0).x][lastCharges.get(0)][lastMoves.get(0).move];
        double qSPrimeAPrime = action_value[y][x][charge][maxMove()];
        double g = 0;
        for (Integer lastReward : lastRewards) {
            g += lastReward;
        }
        double x = alpha*(g + qSPrimeAPrime - qSA);
        action_value[lastMoves.get(0).y][lastMoves.get(0).x][lastCharges.get(0)][lastMoves.get(0).move]
                += x;
        lastMoves.remove(0);
        lastRewards.remove(0);
        lastCharges.remove(0);
        return x;
    }

    private Location getMaxMove() {
        double max = Double.NEGATIVE_INFINITY;
        ArrayList<Integer> maxValues = new ArrayList<>();
        ArrayList<Integer> validMoves = getValidMoves();
        for(int move: validMoves){
            if(action_value[y][x][charge][move] == max){
                maxValues.add(move);
            }else if(action_value[y][x][charge][move] > max){
                maxValues = new ArrayList<>();
                maxValues.add(move);
                max = action_value[y][x][charge][move];
            }
        }
        if(maxValues.size() == 0)maxValues =validMoves;
        return getRandomLocation(maxValues);

    }

    private int maxMove() {
        double max = Double.NEGATIVE_INFINITY;
        ArrayList<Integer> maxValues = new ArrayList<>();
        ArrayList<Integer> validMoves = getValidMoves();

        for (int move : validMoves) {
            if (action_value[y][x][charge][move] == max) {
                maxValues.add(move);
            } else if (action_value[y][x][charge][move] > max) {
                maxValues = new ArrayList<>();
                maxValues.add(move);
                max = action_value[y][x][charge][move];
            }
        }
        if(maxValues.size() == 0)maxValues =validMoves;
        return maxValues.get(random.nextInt(maxValues.size()));
    }

    private Location getRandomLocation(ArrayList<Integer> maxValues) {
        int idx = random.nextInt(maxValues.size());
        step--;
        Location nextLocation = new Location(x+dir_values[maxValues.get(idx)][0], y+dir_values[maxValues.get(idx)][1]);
        if(outOfPower(nextLocation.x, nextLocation.y,maxValues.get(idx))) {
            return move(21, 0, maxValues.get(idx));
        }


        return move(nextLocation.x, nextLocation.y, maxValues.get(idx));
    }

    private ArrayList<Integer> getValidMoves() {
        ArrayList<Integer> validMoves = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if(validLocation(x+dir_values[i][0], y+dir_values[i][1])){
                validMoves.add(i);
            }

        }
        return validMoves;
    }

    private int chargingStation() {
        int cost = chargingStations.getCost(new Location(x, y), charge);
        if (cost != 0){
            charge = 10;
            if (cost > 0){
                return -cost;

            }
        }
        return 0;
    }

    private Location randomMove() {
        ArrayList<Integer> validMoves = getValidMoves();
        return getRandomLocation(validMoves);
    }

    private boolean outOfPower(int newX, int newY, int thisMove){
        if (step == 0){
            step = 5;
            charge--;
        }
        //ARRRGH
        return charge <= 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCost() {
        return cost;
    }

    public void reset(int startX, int startY){
        x = startX;
        y = startY;
        cost = 0;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public boolean finished(int x, int y) {
        if (this.x == x && this.y == y){
            if(nStep > 1) finishUpdate(x, y);
            return true;
        }
        return false;
    }

    private void finishUpdate(int x,int y) {
        while (lastMoves.size() > 0 && lastRewards.size() > 0){
            if(lastMoves.get(0).x != x || lastMoves.get(0).y != y) updateQSA();
        }
    }
}
