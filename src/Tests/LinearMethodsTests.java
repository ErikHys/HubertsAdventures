package Tests;

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
        PolynomialLinearCombination linearCombination = new PolynomialLinearCombination(4, 0.1);
        Random random = new Random();
        random.setSeed(31);
        double[] testFeatures = new double[4];
        Arrays.setAll(testFeatures, i -> random.nextDouble());

        double actual = random.nextDouble();
        System.out.println("\nTarget: " + actual + "\n");

        do {
            linearCombination.updateWeights(actual, linearCombination.sumFeatureWeights(testFeatures), testFeatures);

        } while (!(Math.abs(actual - linearCombination.sumFeatureWeights(testFeatures)) < 0.0001));
        assertEquals(linearCombination.sumFeatureWeights(testFeatures), actual, 0.0001);
        System.out.println("Sum of weight[i] * feature[i]: " + linearCombination.sumFeatureWeights(testFeatures) + "\n");

    }
}
