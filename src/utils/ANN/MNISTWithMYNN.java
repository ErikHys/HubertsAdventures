package utils.ANN;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class MNISTWithMYNN {

    public static void main(String[] args) throws FileNotFoundException {
        int[][] dims = new int[][]{{784, 512}, {512, 256}, {256, 128}, {128, 64}, {64, 3}};
        MyNN myNN = new MyNN(5, dims, new Sigmoid(), 0.1);
        CrossEntropy crossEntropy = new CrossEntropy();
        double[][] imgs = readX();
        double[][] labels = readY();
        for (int e = 0; e < 50; e++) {
            for (int i = 0; i < 64; i++) {
                double[] pred = myNN.forward(imgs[i]);
                myNN.backward(labels[i], pred, crossEntropy);
                myNN.step();
                pred = myNN.forward(imgs[6903 + i]);
                myNN.backward(labels[6903 + i], pred, crossEntropy);
                myNN.step();
                pred = myNN.forward(imgs[14780 + i]);
                myNN.backward(labels[14780 + i], pred, crossEntropy);
                myNN.step();
                if((i+1) % 32 == 0){
                    double loss = crossEntropy.loss(labels[14780 + i], pred);
                    System.out.println("Epoch: " + e  + "\nLoss: " + loss);
                    System.out.println(Arrays.toString(labels[14780 + i]) + " " + Arrays.toString(pred));

                }
            }
        }

    }


    public static double[][] readX() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("Inputs/MNIST/handwritten_digits_images.csv"));
        int i = 0;
        double[][] imgs = new double[21770][784];
        while (sc.hasNext() && i < 21770){
            String[] img = sc.nextLine().split(",");
            imgs[i] = Arrays.stream(img).mapToDouble(Double::parseDouble).map(f -> f/255).toArray();
            i++;
        }
        return imgs;
    }

    public static double[][] readY() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("Inputs/MNIST/handwritten_digits_labels.csv"));
        int i = 0;
        double[][] y = new double[21770][3];
        while (sc.hasNext() && i < 21770){
            int l = sc.nextInt();
            for (int j = 0; j < 3; j++) {
                y[i][j] = j == l ? 1 : -1;
            }

            i++;
        }
        return y;
    }
}
