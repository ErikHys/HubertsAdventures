package utils.ANN;

import java.util.Arrays;

public class CrossEntropy implements ILoss{

    @Override
    @Deprecated
    public double loss(double actual, double predicted) {
        return 0;
    }

    @Override
    public double derivedLoss(double actual, double predicted) {
        double test = -actual*(1/predicted);
        return test;
    }

    @Override
    public double loss(double[] actual, double[] predicted) {
        double[] result = new double[actual.length];
        Arrays.setAll(result, i -> actual[i]*Math.log(predicted[i]));
        return -Arrays.stream(result).sum();
    }

    @Override
    @Deprecated
    public double derivedLoss(double[] actual, double[] predicted) {
        return 0;
    }
}
