package FactoryToHQ;

import utils.Location;
import java.util.ArrayList;

public class SimulateRun{


    public static ArrayList<Location> runRandomSimulation(Hubert hubert, double epsilon){
        int steps = 0;
        ArrayList<Location> path = new ArrayList<>();
        while (steps < 10000 || (hubert.getX() == 27 && hubert.getY() == 30)){
            path.add(hubert.epsilonGreedyMove(epsilon));
            steps++;
        }
        return path;
    }

    public static ArrayList<Location> runSmartSimulation(Hubert hubert, double epsilon, int n, double alpha){
//        double alpha = 0.9;
        hubert.setAlpha(alpha);
        ArrayList<Location> path = null;
        for (int ep = 0; ep < n; ep++) {
            path = new ArrayList<>();
            int steps = 0;
            while (!hubert.finished(27, 30) && steps < 10000000){
                path.add(hubert.epsilonGreedyMove(epsilon));
                steps++;
            }
            if(ep % (n/5) == 0) {
                System.out.println("Alpha: " + alpha + " " + ep + "/" + n);
            }
            if(ep == n-1)System.out.println("Cost: " + hubert.getCost() + " length: " + path.size());
            hubert.reset(2, 2);

        }
        return path;
    }

    public static void runManualInput(Hubert hubert){
        hubert.setAlpha(0.7);
        for (int ep = 0; ep < 10000000; ep++) {
            int steps = 0;
            while (!(hubert.getX() == 27 && hubert.getY() == 30) && steps < 100000000){
                int move = 1;
                if (steps == 4) move = 0;
                hubert.manualMove(move);
                steps++;
            }
            if(ep % 10 == 0) {
                System.out.println("Cost and length:");
                System.out.println(hubert.getCost());

                System.out.println(steps + "\n");
            }
            hubert.reset(23, 31);

        }
    }

    public static double getAvgNRuns(int runs, Hubert preTrainedHubert, double epsilon){
        double avg = 0;
        double avg_cost = 0;
        for (int ep = 0; ep < runs; ep++) {
            int steps = 0;
            while (!(preTrainedHubert.getX() == 6 && preTrainedHubert.getY() == 17)){
                preTrainedHubert.epsilonGreedyMove(epsilon);
                steps++;
            }
            avg += steps;
            avg_cost += preTrainedHubert.getCost();
        }
        return avg_cost/runs;
    }

}
