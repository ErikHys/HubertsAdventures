package utils.ANN;

import java.util.Arrays;

public class Softmax implements IActivations{

    @Override
    @Deprecated
    public double forward(double a) {
        return 0;
    }

    @Override
    public double[] forward(double[] a) {
        double sum = Arrays.stream(a).map(Math::exp).sum();
        return Arrays.stream(a).map(j -> Math.exp(j)/sum).toArray();
    }
    @Override
    @Deprecated
    public double derivedA(double a) {
        return 0;
    }

    @Override
    public double[] derivedA(double[] a) {
        /**
         * WRONG AS FUCK TODO
         */
        double m = Arrays.stream(a).max().getAsDouble();
        a = Arrays.stream(a).map(i -> i-m).toArray();
        double sumSquared = Math.pow(Arrays.stream(a).map(Math::exp).sum(), 2);
        double[] result = new double[a.length];
        double[] f = forward(a);
        double[] finalA = a;
        Arrays.setAll(result, i -> f[i] - Math.exp(2* finalA[i])/sumSquared);
        return result;
    }

    public static void main(String[] args) {
        double[] s = new double[]{0.2, 0.1, 0.7};
        Softmax softmax = new Softmax();
        s = softmax.derivedA(s);
        for (double d: s) {
            System.out.println(d);
        }
    }
}
