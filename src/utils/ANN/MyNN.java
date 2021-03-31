package utils.ANN;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MyNN {
    private final Dense[] layers;
    private final IActivations activation;
    private final int nrOfLayers;
    private final int[][] dims;
    private final IActivations lastActivation;
    private double[][] z;
    private double[][] a;
    private final double learningRate;

    public MyNN(int nrOfLayers, int[][] dims, IActivations activation, double learningRate){
        layers = new Dense[nrOfLayers];
        for (int i = 0; i < layers.length; i++) {
            layers[i] = new Dense(dims[i][0], dims[i][1]);
        }
        this.activation = activation;
        this.lastActivation = activation;
        this.dims = dims;
        this.nrOfLayers = nrOfLayers;
        this.learningRate = learningRate;
        z = new double[nrOfLayers][];
        a = new double[nrOfLayers+1][];
    }

    public MyNN(int nrOfLayers, int[][] dims, IActivations activation, double learningRate, IActivations lastActivation){
        layers = new Dense[nrOfLayers];
        for (int i = 0; i < layers.length; i++) {
            layers[i] = new Dense(dims[i][0], dims[i][1]);
        }
        this.activation = activation;
        this.dims = dims;
        this.lastActivation = lastActivation;
        this.nrOfLayers = nrOfLayers;
        this.learningRate = learningRate;
        z = new double[nrOfLayers][];
        a = new double[nrOfLayers+1][];
    }

    public double[] forward(double[] features){
        a[0] = features;
        for (int i = 0; i < nrOfLayers-1; i++) {
            z[i] = layers[i].forward(a[i]);
            a[i+1] = activation.forward(z[i]);
        }
        z[nrOfLayers-1] = layers[nrOfLayers-1].forward(a[nrOfLayers-1]);
        a[nrOfLayers] = lastActivation.forward(z[nrOfLayers-1]);
        return a[nrOfLayers];
    }

    public void backward(double actual, double predicted, MSE loss){
        double[][] deltas = new double[nrOfLayers][];
        deltas[nrOfLayers-1] = Arrays.stream(z[nrOfLayers-1])
                .map(i -> lastActivation.derivedA(i)*loss.derivedLoss(actual, predicted)).toArray();
        backwardsUtil(deltas);
    }

    public void backward(double[] actual, double[] predicted, ILoss loss){
        double[][] deltas = new double[nrOfLayers][];
        deltas[nrOfLayers-1] = new double[layers[nrOfLayers-1].getOut()];
        Arrays.setAll(deltas[nrOfLayers-1], i -> lastActivation.derivedA(z[nrOfLayers-1])[i]*loss.derivedLoss(actual[i], predicted[i]));
        backwardsUtil(deltas);
    }

    private void backwardsUtil(double[][] deltas) {
        layers[nrOfLayers-1].updateWeightGrad(updateLayerGrad(layers[nrOfLayers-1].getOut()
                , layers[nrOfLayers-1].getIn(), deltas[nrOfLayers-1], a[nrOfLayers-1]));
        layers[nrOfLayers-1].updateBias(deltas[nrOfLayers-1]);
        for (int l = nrOfLayers-2; l >= 0; l--) {
            double[] allSumDeltaWeights = new double[layers[l].getOut()];
            for (int i = 0; i < layers[l].getOut(); i++) {
                double sumDeltaWeights = 0;
                for (int k = 0; k < layers[l+1].getOut(); k++) {
                    sumDeltaWeights += layers[l+1].getWeights()[k][i] * deltas[l+1][k];
                }
                allSumDeltaWeights[i] = sumDeltaWeights;
            }
            int finalL = l;
            deltas[l] = new double[layers[l].getOut()];
            Arrays.setAll(deltas[l], i -> activation.derivedA(z[finalL][i])*allSumDeltaWeights[i]);
            layers[l].updateBias(deltas[l]);
            layers[l].updateWeightGrad(updateLayerGrad(layers[l].getOut(), layers[l].getIn(), deltas[l], a[l]));
        }
    }

    private double[][] updateLayerGrad(int out, int in, double[] deltas, double[] a){
        double[][] newGrads = new double[out][in];
        for (int i = 0; i < out; i++) {
            for (int j = 0; j < in; j++) {
                newGrads[i][j] = deltas[i] * a[j];
            }
        }
        return newGrads;
    }


    public void step() {
        for(Dense layer: layers){
            layer.step(learningRate);
        }
    }

    public double[] softmax(double[] pred){
        double sum = 0;
        for (double v : pred) {
            sum += Math.exp(Math.min(Double.MAX_EXPONENT, v));
        }
        for (int i = 0; i < pred.length; i++) {
            pred[i] = Math.exp(pred[i])/ sum;
        }
        return pred;
    }


    public static void main(String[] args) {
        int[][] dims = new int[][]{{3, 1}, {1, 2}, {2, 1}};
        MSE mse = new MSE();
        MyNN myNN = new MyNN(3, dims, new ReLu(), 0.01);
        double[] f = new double[]{1, 2, 1};
        double predicted = myNN.forward(f)[0];
        double loss = mse.loss(4, predicted);
        double predictedPrime = predicted;
        for (int i = 0; i < 50; i++) {
            myNN.backward(4, predictedPrime, mse);
            myNN.step();
            predictedPrime = myNN.forward(f)[0];
            double lossPrime = mse.loss(4, predictedPrime);
            System.out.println("Epoch: " + i + " Target: 4\nPredicted: " + predicted + "\nLoss: " + loss + "\nPredicted': "
                    + predictedPrime + "\nLoss': " + lossPrime);
        }


    }

}
