package utils.ANN;

public interface IActivations {

    double forward(double a);
    double[] forward(double[] a);

    double derivedA(double a);
    double[] derivedA(double[] a);

}
