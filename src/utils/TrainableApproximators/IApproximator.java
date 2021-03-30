package utils.TrainableApproximators;

public interface IApproximator<In, Out> {

    public Out predict(In s);
    public void updateWeights(Out actual, Out predicted, In features);

}
