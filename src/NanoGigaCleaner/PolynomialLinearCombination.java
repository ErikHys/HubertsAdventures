package NanoGigaCleaner;

import java.util.Arrays;
import java.util.Random;

public class PolynomialLinearCombination {

    double alpha;
    double[] weights;

    public PolynomialLinearCombination(int nWeights, double learningRate){
        //Only implemented for 4 weights yet...
        Random random = new Random();
        weights = random.doubles(nWeights * 4L).toArray();
        alpha = learningRate;
    }

    public double sumFeatureWeights(double[] features){
        double[] result = new double[weights.length];
        int powIdxF = 0;
        int powIdxS = 0;
        for (int i = 0; i < weights.length / 2; i++) {

            result[i] = Math.pow(features[0], powIdxF) * Math.pow(features[1], powIdxS) * weights[i];
            result[weights.length/2 + i] = Math.pow(features[2], powIdxF) * Math.pow(features[3], powIdxS) * weights[weights.length/2 + i];
            powIdxF = powIdxF > 1 ? 1 : powIdxF+1;
            powIdxS = powIdxS > 1 ? 1 : i == 0 ? 0 : powIdxS+1;
        }
        return Arrays.stream(result).sum();
    }

    public void updateWeights(double actual, double predicted, double[] features){
        int powIdxF = 0;
        int powIdxS = 0;
        for (int i = 0; i < weights.length / 2; i++) {
            weights[i] = weights[i] + alpha*(actual-predicted) * powIdxF*Math.pow(features[0], powIdxF-1) * powIdxS*Math.pow(features[1], powIdxS-1);
            weights[weights.length/2 + i] = weights[weights.length/2 + i] + alpha*(actual-predicted) * powIdxF*Math.pow(features[2], powIdxF) * powIdxS*Math.pow(features[3], powIdxS);
            powIdxF = powIdxF > 1 ? 1 : powIdxF+1;
            powIdxS = powIdxS > 1 ? 1 : i == 0 ? 0 : powIdxS+1;
        }

    }
}
