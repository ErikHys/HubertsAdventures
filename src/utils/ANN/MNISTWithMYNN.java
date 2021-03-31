package utils.ANN;

public class MNISTWithMYNN {

    public static void main(String[] args) {
        int[][] dims = new int[][]{{784, 512}, {512, 256}, {256, 128}, {128, 64}, {64, 32}, {32, 10}};
        MyNN myNN = new MyNN(6, dims, new ReLu(), 0.01);
    }
}
