package utils.ANN;

import java.util.Arrays;

public class Sigmoid implements IActivations{
    @Override
    public double forward(double a) {
        return 1/(1+Math.exp(-a));
    }

    @Override
    public double[] forward(double[] a) {
        return Arrays.stream(a).map(this::forward).toArray();
    }

    @Override
    public double derivedA(double a) {
        double test = Math.exp(-a)/Math.pow((1+Math.exp(-a)), 2);
        return test;
    }

    @Override
    public double[] derivedA(double[] a) {
        return Arrays.stream(a).map(this::derivedA).toArray();
    }
}
