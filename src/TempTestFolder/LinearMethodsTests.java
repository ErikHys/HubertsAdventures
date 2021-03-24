package TempTestFolder;

import NanoGigaCleaner.LinearCombination;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class LinearMethodsTests {

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
//            System.out.println("Sum of weight[i] * feature[i]: " + linearCombination.sumFeatureWeights(testFeatures) + "\n");
            linearCombination.updateWeights(actual, linearCombination.sumFeatureWeights(testFeatures), testFeatures);
//            System.out.println("Sum of weight[i] * feature[i]: " + linearCombination.sumFeatureWeights(testFeatures) + "\n");
        } while (!(Math.abs(actual - linearCombination.sumFeatureWeights(testFeatures)) < 0.0000001));
        assertEquals(linearCombination.sumFeatureWeights(testFeatures), actual, 0.001);
    }
}
