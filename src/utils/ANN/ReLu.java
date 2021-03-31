package utils.ANN;

import java.util.Arrays;

public class ReLu implements IActivations {

    @Override
    public double forward(double in){
        return Math.max(0, in);
    }

    @Override
    public double[] forward(double[] a) {
        return Arrays.stream(a).map(this::forward).toArray();
    }

    @Override
    public double derivedA(double a) {
        return a > 0 ? 1 : 0;
    }

    @Override
    public double[] derivedA(double[] a) {
        return Arrays.stream(a).map(this::derivedA).toArray();
    }
}
