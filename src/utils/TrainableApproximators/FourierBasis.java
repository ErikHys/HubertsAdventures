package utils.TrainableApproximators;

import java.util.Arrays;
import java.util.Random;

public class FourierBasis {
    double[] weights;
    double alpha;

    public FourierBasis(int weights, double alpha){
        Random random = new Random();
        this.weights = random.ints(weights, -10, 10).mapToDouble(i -> (double) i).toArray();
        this.alpha = alpha;
    }

    public double predict(double[] state){
        return Math.cos(Math.PI * vectorMul(state, weights));
    }

    public void updateWeights(double actual, double predicted, double[] state){
        double alphaI = getAlpha();
        double[] newWeights = new double[weights.length];
        Arrays.setAll(newWeights, i-> weights[i] + alphaI*(actual - predicted)*
                (Math.PI *(-state[i])*Math.sin(Math.PI * vectorMul(state, weights))));
        weights = newWeights;

    }

    public double[] getGradient(double[] state){
        double alphaI = getAlpha();
        double[] newWeights = new double[weights.length];
        Arrays.setAll(newWeights, i-> (Math.PI *(-state[i])*Math.sin(Math.PI * vectorMul(state, weights))));
        return newWeights;
    }

    public double getAlpha(){
        double[] result = new double[weights.length];
        Arrays.setAll(result, j -> Math.pow(weights[j], 2));
        double ai = alpha/Math.sqrt(Arrays.stream(result).sum());
        return ai != 0 ? ai : alpha;
    }

    public double vectorMul(double[] a, double[] b){
        double[] result = new double[a.length];
        Arrays.setAll(result, i -> a[i] * b[i]);
        return Arrays.stream(result).sum();
    }
}
