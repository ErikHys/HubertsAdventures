package utils.ANN;

public interface ILoss {

    double loss(double actual, double predicted);
    double derivedLoss(double actual, double predicted);
    double loss(double[] actual, double[] predicted);
    double derivedLoss(double[] actual, double[] predicted);
}
