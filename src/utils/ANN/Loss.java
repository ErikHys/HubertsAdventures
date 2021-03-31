package utils.ANN;

public interface Loss {

    double loss(double actual, double predicted);
    double derivedLoss(double actual, double predicted);
}
