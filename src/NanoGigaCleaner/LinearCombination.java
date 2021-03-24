package NanoGigaCleaner;

import java.util.Arrays;
import java.util.Random;

public class LinearCombination {

    double alpha;
    double[] weights;

    public LinearCombination(int nWeights, double learningRate){
        Random random = new Random();
        weights = random.doubles(nWeights).toArray();
        alpha = learningRate;
    }

    public double sumFeatureWeights(double[] features){
        double[] result = new double[features.length];
        Arrays.setAll(result, i -> weights[i] * features[i]);
        return Arrays.stream(result).sum();
    }

    public void updateWeights(double actual, double predicted, double[] features){
        Arrays.setAll(weights, i -> weights[i] + alpha*(actual-predicted)*features[i]);
    }
}
