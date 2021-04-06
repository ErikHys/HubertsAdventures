package utils.TrainableApproximators;

public interface IApproximator {

    public double predict(double[] s);
    public void updateWeights(double actual, double predicted, double[] features);
    public void updateWeights(double delta, double[] features);

}
