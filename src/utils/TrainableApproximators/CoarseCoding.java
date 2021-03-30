package utils.TrainableApproximators;

import NanoGigaCleaner.Circle;
import NanoGigaCleaner.ISofaClubObject;
import utils.Pair;
import utils.Vector2D;

import java.util.Arrays;
import java.util.Random;

public class CoarseCoding{


    private final Circle[][] circles;
    private final double[][] weights;
    private final int height;
    private final int width;
    private final int nrOfWeightsPerTile;
    private final double alpha;

    public CoarseCoding(int height, int width, int nrOfWeightsPerTile, double learningRate){
        this.height = height;
        this.width = width;
        this.nrOfWeightsPerTile = nrOfWeightsPerTile;
        circles = new Circle[height*nrOfWeightsPerTile][width*nrOfWeightsPerTile];
        for (int i = 0; i < circles.length; i++) {
            for (int j = 0; j < circles[i].length; j++) {
                circles[i][j] = new Circle(new Vector2D(1/(double)nrOfWeightsPerTile * j, 1/(double)nrOfWeightsPerTile * i), 1/(double)nrOfWeightsPerTile);
            }
        }
        Random random = new Random();
        weights = new double[height*nrOfWeightsPerTile][width*nrOfWeightsPerTile];
        for (int i = 0; i < circles.length; i++) {
            double[] row = new double[weights[i].length];
            Arrays.setAll(row, j -> 0);
            weights[i] = row;
        }

        alpha = learningRate;
    }

    public Pair<Double, int[][]> predict(Vector2D s) {
        int[][] circleFeatures = new int[height*nrOfWeightsPerTile][width*nrOfWeightsPerTile];
        for (int i = 0; i < circleFeatures.length; i++) {
            int finalI = i;
            Arrays.setAll(circleFeatures[i], j -> collision(circles[finalI][j], s));
        }
        double[][] result = new double[height*nrOfWeightsPerTile][width*nrOfWeightsPerTile];
        for (int i = 0; i < circleFeatures.length; i++) {
            int finalI = i;
            Arrays.setAll(result[i], j -> circleFeatures[finalI][j] * weights[finalI][j]);
        }

        return new Pair<>(Arrays.stream(result).mapToDouble(arr -> arr[0]).sum(), circleFeatures);
    }

    public void updateWeights(Double actual, Double predicted, int[][] features) {
        for (int i = 0; i < features.length; i++) {
            int finalI = i;
            Arrays.setAll(weights[i], j -> weights[finalI][j] + alpha*(actual-predicted)*features[finalI][j]);
        }
    }

    public int collision(ISofaClubObject circle, Vector2D vector) {
        Vector2D vectorPosSub = vector.sub(circle.getVector());
        return vectorPosSub.mul(vectorPosSub) <= Math.pow(1/(double)nrOfWeightsPerTile, 2) ? 1 : 0;
    }

    public double[][] getWeights() {
        return weights;
    }
}
