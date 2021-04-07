package utils.TrainableApproximators;

import java.util.Arrays;
import java.util.Random;

public class FourierBasis implements IApproximator{
    private final int order;
    double[][] cWeights;
    double alpha;

    public FourierBasis(int cWeights, double alpha){
        Random random = new Random(27);
        order = 1;
        this.alpha = alpha;
        this.cWeights = new double[(int) Math.pow(order+1, cWeights)][cWeights];
        Arrays.setAll(this.cWeights, j -> random.ints(cWeights, 0, order+1).mapToDouble(i -> (double) i).toArray());


    }

    @Override
    public double predict(double[] state){
        return Arrays.stream(cWeights).map(i -> Math.cos(Math.PI * vectorMul(state, i))).mapToDouble(Double::doubleValue).sum();
    }

    @Override
    public void updateWeights(double actual, double predicted, double[] state){
//        double alphaI = getAlpha();
//        double[] newcWeights = new double[cWeights.length];
//        Arrays.setAll(newcWeights, i-> cWeights[i] + alphaI*(actual - predicted)*
//                (Math.PI *(-state[i])*Math.sin(Math.PI * vectorMul(state, cWeights))));
//        cWeights = newcWeights;

    }

    @Override
    public void updateWeights(double delta, double[] state){
//        double alphaI = getAlpha();
//        double[] newcWeights = new double[cWeights.length];
//        Arrays.setAll(newcWeights, i-> cWeights[i] + alphaI*delta*
//                (Math.PI *(-state[i])*Math.sin(Math.PI * vectorMul(state, cWeights))));
//        cWeights = newcWeights;

    }

    public double[] getGradient(double[] state){
//        double alphaI = getAlpha();
//        double[] newcWeights = new double[cWeights.length];
//        Arrays.setAll(newcWeights, i-> (Math.PI *(-state[i])*Math.sin(Math.PI * vectorMul(state, cWeights))));
//        return newcWeights;
        return null;
    }

    public double getAlpha(int i){
        double[] result = new double[cWeights[i].length];
        Arrays.setAll(result, j -> Math.pow(cWeights[i][j], 2));
        double ai = alpha/Math.sqrt(Arrays.stream(result).sum());
        return ai != 0 ? ai : alpha;
    }

    public double vectorMul(double[] a, double[] b){
        double[] result = new double[a.length];
        Arrays.setAll(result, i -> a[i] * b[i]);
        return Arrays.stream(result).sum();
    }

    public double[][] getcWeights() {
        return cWeights;
    }

    public static void main(String[] args) {
        FourierBasis fb = new FourierBasis(2, 0.1);
        System.out.println(fb.predict(new double[]{1, 0}));
    }
}
