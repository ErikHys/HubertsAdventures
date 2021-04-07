package utils.TrainableApproximators;

import java.util.Arrays;
import java.util.Random;

public class FourierBasis implements IApproximator{
    private final int order;
    private final int f;
    private double[] weights;
    int[][] c;
    double alpha;

    public FourierBasis(int k, double alpha){
        Random random = new Random();
        order = 3;
        this.alpha = alpha;
        this.f = (int) Math.pow(order+1, k);
        this.c = new  int[f][k];
        int idx = 0;
        for (int m = 0; m <= order; m++) {
            for (int i = 0; i <= order; i++) {
                for (int j = 0; j <= order; j++) {
                    for (int l = 0; l <= order; l++) {
                        c[idx] = new int[]{m, i, j, l};
                        idx++;
                    }
                }
            }
        }
        this.weights = random.doubles(f).toArray();
    }

    @Override
    public double predict(double[] state){
        for (int i = 0; i < state.length-1; i++){
            state[i] = state[i]/10;
        }
        state[3] /= 5;
        double[] x = Arrays.stream(c).map(i -> Math.cos(Math.PI * vectorMul(state, i))).mapToDouble(Double::doubleValue).toArray();
        return vectorMul(x, weights);
    }

    @Override
    public void updateWeights(double actual, double predicted, double[] state){
        double[] x = Arrays.stream(c).map(i -> Math.cos(Math.PI * vectorMul(state, i))).mapToDouble(Double::doubleValue).toArray();
        double[] newWeights = new double[weights.length];
        Arrays.setAll(newWeights, i -> weights[i] + getAlpha(i)*(actual-predicted)*x[i]);
        weights = newWeights;
    }

    @Override
    public void updateWeights(double delta, double[] state){
        double[] x = Arrays.stream(c).map(i -> Math.cos(Math.PI * vectorMul(state, i))).mapToDouble(Double::doubleValue).toArray();
        double[] newWeights = new double[weights.length];
        Arrays.setAll(newWeights, i -> weights[i] + getAlpha(i)*(delta)*x[i]);
        weights = newWeights;

    }

    public double getAlpha(int i){
        double[] result = new double[c[i].length];
        Arrays.setAll(result, j -> Math.pow(c[i][j], 2));
        double mSqrt = Math.sqrt(Arrays.stream(result).sum()) == 0 ? 1 : Math.sqrt(Arrays.stream(result).sum());
        double ai = alpha/ mSqrt;
        return ai != 0 ? ai : alpha;
    }

    public double vectorMul(double[] a, int[] b){
        double[] result = new double[a.length];
        Arrays.setAll(result, i -> a[i] * b[i]);
        return Arrays.stream(result).sum();
    }

    public double vectorMul(double[] a, double[] b){
        double[] result = new double[a.length];
        Arrays.setAll(result, i -> a[i] * b[i]);
        return Arrays.stream(result).sum();
    }


    public static void main(String[] args) {
        FourierBasis fb = new FourierBasis(3, 0.1);
        System.out.println(fb.predict(new double[]{0.0, 0.0, 0.0}));
        System.out.println(fb.predict(new double[]{0.5, 0.5, 0.5}));
        System.out.println(fb.predict(new double[]{1.0, 1.0, 1.0}));
    }

}
