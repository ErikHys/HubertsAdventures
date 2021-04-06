package Tests;

import utils.TrainableApproximators.FourierBasis;
import utils.TrainableApproximators.LinearCombination;
import utils.TrainableApproximators.PolynomialLinearCombination;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class LinearMethodsTests {
    //These are only testing for overfitting on one case, but I plan to implement more complex tests

    @Test
    public void updateWeightsTest(){
        LinearCombination linearCombination = new LinearCombination(5, 0.001);
        Random random = new Random();
        random.setSeed(27);

        double[] testFeatures = new double[5];
        Arrays.setAll(testFeatures, i -> random.nextDouble());

        double actual = random.nextDouble();
        System.out.println("\nTarget: " + actual + "\n");


        do {
            System.out.println("Sum of weight[i] * feature[i]: " + linearCombination.predict(testFeatures) + "\n");
            linearCombination.updateWeights(actual, linearCombination.predict(testFeatures), testFeatures);
            System.out.println("Sum of weight[i] * feature[i]: " + linearCombination.predict(testFeatures) + "\n");
        } while (!(Math.abs(actual - linearCombination.predict(testFeatures)) < 0.0000001));
        assertEquals(linearCombination.predict(testFeatures), actual, 0.001);
    }

    @Test
    public void updatePloyWeightsTest(){
        PolynomialLinearCombination linearCombination = new PolynomialLinearCombination(4, 0.001);
        Random random = new Random();
        random.setSeed(31);
        int nSamples = 13;
        double[][] testFeatures = new double[nSamples][4];
        for (int i = 0; i < nSamples; i++) {
            testFeatures[i] = random.doubles(4).toArray();
        }


        double[] actual = random.doubles(nSamples).toArray();
        System.out.println("\nTarget: " + actual[7] + "\n");

        do {
            for (int i = 0; i < nSamples; i++) {
                linearCombination.updateWeights(actual[i], linearCombination.sumFeatureWeights(testFeatures[i]), testFeatures[i]);

            }

        } while (!(Math.abs(actual[7] - linearCombination.sumFeatureWeights(testFeatures[7])) < 0.0001));
        assertEquals(linearCombination.sumFeatureWeights(testFeatures[7]), actual[7], 0.0001);
        System.out.println("Sum of weight[i] * feature[i]: " + linearCombination.sumFeatureWeights(testFeatures[7]) + "\n");

    }


    @Test
    public void fourierBasisTest(){
        FourierBasis fb = new FourierBasis(3, 0.01);
        Random random = new Random();
        random.setSeed(31);
        double[] testFeatures = random.doubles(3).toArray();
        double actual = -0.5;
        do {
            fb.updateWeights(actual, fb.predict(testFeatures), testFeatures);
        }while (!(Math.abs(actual - fb.predict(testFeatures)) < 0.0001));
        assertEquals(fb.predict(testFeatures), actual, 0.0001);
        System.out.println("Sum of weight[i] * feature[i]: " + fb.predict(testFeatures) + "\n");
    }
}
