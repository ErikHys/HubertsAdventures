package utils.ANN;

public class MSE {


    public double loss(double actual, double predicted){
        return Math.pow(actual-predicted, 2)/2;
    }

    public double derivedLoss(double actual, double predicted){
        return -(actual-predicted);
    }
}
