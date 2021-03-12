package FactoryToHQ;

import FactoryToHQ.Hubert;
import FactoryToHQ.SimulateRun;
import utils.Location;
import utils.ReadAndWrite;

import java.io.*;
import java.util.ArrayList;

public class Week5And6 {


    public static void main(String[] args) throws IOException, InterruptedException {

        int[][] neighborhood = ReadAndWrite.getGrid("Inputs/the_neighborhood.txt");


        int[] steps = new int[]{1, 2, 4, 5, 6};
        double[] aplhas = new double[]{0.3, 0.1, 0.05};
        double[] epsilons = new double[]{0.2, 0.1, 0.05, 0.01};
        ArrayList<Thread> threads = new ArrayList<>();
        for (int step: steps) {
            for (double alpha: aplhas) {
                for (double epsilon: epsilons){
                    Thread t = new Thread(() ->{
                        Hubert hubert = new Hubert(2, 2, neighborhood[0].length, neighborhood.length, neighborhood, step);
                        ArrayList<Location> path = SimulateRun.runSmartSimulation(hubert, epsilon, 250000, alpha);
                        try {
                            ReadAndWrite.writePath(path, "smart_path_with_rules_nstep" + step + "_e" + epsilon + "_a" + alpha +".txt");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("NStep: " + step + " alpha: " + alpha + " epsilon: " + epsilon);});
                    t.start();
                    threads.add(t);

                }
            }
        }
        for(Thread t: threads){
            t.join();
        }
    }
}
