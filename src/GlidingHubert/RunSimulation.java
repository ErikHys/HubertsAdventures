package GlidingHubert;

import utils.Location;
import java.util.ArrayList;

public class RunSimulation {


    /**
     * Run a simulation for Hubert
     * @param hubert Hubert that is being trained
     * @param epsilon epsilon for epsilon greedy
     * @param n amount of episodes run
     * @param dynaQSteps amount of dynaQsteps
     * @return The last path run
     */
    public static ArrayList<Location> runSmartSimulation(Hubert hubert, double epsilon, int n, int dynaQSteps, double lam, double alpha){
        ArrayList<Location> path = null;
        for (int ep = 0; ep < n; ep++) {
            path = new ArrayList<>();
            int steps = 0;
            while (!hubert.finished(54, 14) && steps < 100000){
                path.add(hubert.dynaQ(epsilon, dynaQSteps));
                steps++;
            }

//            System.out.println("DynaQSteps: " + dynaQSteps + " " + " length: " + path.size() + " " + (ep+1) + "/" + n);

            if(ep == n-1)System.out.println("Length: " + path.size() + " DynaQSteps: " + dynaQSteps +
                    " epsilon: " + epsilon + " lambda: " + lam + " alpha: " + alpha + " length: " + path.size() + " " + (ep+1) + "/" + n);
            hubert.reset(5, 13);

        }
        return path;
    }

}
