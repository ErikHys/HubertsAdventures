package utils.TrainableApproximators;

import java.util.Arrays;
import java.util.Random;

public class LinearCombination implements IApproximator<double[], Double>{

    double alpha;
    double[] weights;

    public LinearCombination(int nWeights, double learningRate){
        Random random = new Random();
        weights = random.doubles(nWeights).toArray();
        alpha = learningRate;
    }


    @Override
    public Double predict(double[] s) {
        double[] result = new double[s.length];
        Arrays.setAll(result, i -> weights[i] * s[i]);
        return Arrays.stream(result).sum();
    }

    @Override
    public void updateWeights(Double actual, Double predicted, double[] features) {
        Arrays.setAll(weights, i -> weights[i] + alpha*(actual-predicted)*features[i]);
    }
}