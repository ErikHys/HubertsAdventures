package utils.ANN;

import java.util.Arrays;
import java.util.Random;

public class Dense {

    double[][] weights;
    double[] bias;
    double[] biasGrads;
    double[][] grads;
    double[] lastPass;
    int in;
    int out;

    public Dense(int in, int out){
        this.in = in;
        this.out = out;
        Random random = new Random(27);
        weights = new double[out][in];
        grads = new double[out][in];
        bias = new double[out];
        Arrays.setAll(weights, i-> random.doubles(in, -0.5, 0.5).toArray());
        bias = random.doubles(out, -0.25, 0.25).toArray();
        Arrays.setAll(grads, i-> random.doubles(in).toArray());
        biasGrads = random.doubles(out).toArray();
    }

    public double[] forward(double[] features){
        double[] result = new double[out];
        Arrays.setAll(result, i -> dotProductOfTwoVectors(weights[i], features, bias[i]));
        lastPass = result;
        return result;
    }

    private double dotProductOfTwoVectors(double[] weightsI, double[] features, double biasI){
        double[] result = new double[in];
        Arrays.setAll(result, i -> weightsI[i]*features[i]);
        return Arrays.stream(result).sum()+biasI;
    }

    public void updateWeightGrad(double[][] grads){
        this.grads = grads;
    }

    public void updateBias(double[] biasGrads){
        this.biasGrads = biasGrads;
    }

    public void step(double alpha){
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = weights[i][j] - alpha*grads[i][j];

            }
        }
        for (int i = 0; i < bias.length; i++) {
            bias[i] = bias[i] - alpha*biasGrads[i];
        }
    }

    public double[][] getWeights() {
        return weights;
    }

    public int getIn() {
        return in;
    }

    public int getOut() {
        return out;
    }
}
