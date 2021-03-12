package GlidingHubert;

import GlidingHubert.Hubert;
import utils.Location;
import utils.ReadAndWrite;
import utils.Sars;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Week7 {

    public static void main(String[] args) throws IOException, InterruptedException {

        int[][] skyline = ReadAndWrite.getGrid("Inputs/skyline.txt");
        int[][] hWind = ReadAndWrite.getGrid("Inputs/horizontal_wind.txt");
        int[][] vWind = ReadAndWrite.getGrid("Inputs/vertical_wind.txt");
        ArrayList<Sars<Location, LeftRight, Integer>> poweredFlight = ReadAndWrite.readSarsPath("Inputs/powered_flight.txt");
        ArrayList<Thread> threads = new ArrayList<>();
        double[] lambdas = new double[]{0.9, 0.7, 0.5, 0.3};
        double[] alphas = new double[]{0.9, 0.7, 0.5, 0.3};
        double[] epsilons = new double[]{0.2, 0.1, 0.05};
        int[] dynaQSteps = new int[]{100, 50, 25, 10 ,5};
        for (double lam: lambdas){
            for (double alpha: alphas){
                for (double epsilon: epsilons){
                    for (int step: dynaQSteps){
                        Thread t = new Thread(() -> {
                            Hubert hubert = new Hubert(5, 13, skyline[0].length, skyline.length, skyline, vWind, hWind, lam, alpha);
                            hubert.modelFormerPath(poweredFlight);
                            ArrayList<Location> path = null;
                            path = RunSimulation.runSmartSimulation(hubert, epsilon, 10, step, lam, alpha);
                            if (path.size() < 50) {
                                try {
                                    ReadAndWrite.writePath(path, "con-dor_path_l" + lam + "a" + alpha + "e" + epsilon +"s" + step + ".txt");
                                    ReadAndWrite.writeModel(hubert.getActionValue());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        t.start();
                        threads.add(t);
                    }
                }
            }
        }

        for (Thread t: threads){
            t.join();
        }

    }
}
