package utils.ANN;

public class MSE implements ILoss{


    @Override
    public double loss(double actual, double predicted){
        return Math.pow(actual-predicted, 2)/2;
    }

    @Override
    public double derivedLoss(double actual, double predicted){
        return -(actual-predicted);
    }

    @Override
    @Deprecated
    public double loss(double[] actual, double[] predicted) {
        return 0;
    }

    @Override
    @Deprecated
    public double derivedLoss(double[] actual, double[] predicted) {
        return 0;
    }
}
