package utils.TrainableApproximators;

import java.util.Arrays;
import java.util.Random;

public class LinearCombination implements IApproximator{

    double alpha;
    double[] weights;

    public LinearCombination(int nWeights, double learningRate){
        Random random = new Random();
        weights = random.doubles(nWeights).toArray();
        alpha = learningRate;
    }


    @Override
    public double predict(double[] s) {
        double[] result = new double[s.length];
        Arrays.setAll(result, i -> weights[i] * s[i]);
        return Arrays.stream(result).sum();
    }

    @Override
    public void updateWeights(double actual, double predicted, double[] features) {
        Arrays.setAll(weights, i -> weights[i] + alpha*(actual-predicted)*features[i]);
    }

    @Override
    public void updateWeights(double delta, double[] features) {
        Arrays.setAll(weights, i -> weights[i] + alpha*(delta)*features[i]);
    }
}
