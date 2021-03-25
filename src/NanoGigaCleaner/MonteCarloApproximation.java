package NanoGigaCleaner;

import utils.Pair;
import utils.Vector2D;

import java.io.IOException;
import java.util.ArrayList;

public class MonteCarloApproximation {

    // FUN FUN FUN :)
    public static void main(String[] args) throws IOException {
        ISofaClubObject[] clubObjects = clubObjectsSetUp();

        ArrayList<Pair<Double, Pair<Vector2D, Vector2D>>> path = null;
        int i = 0;
        double avg = 0;
        PolynomialLinearCombination w = new PolynomialLinearCombination(4, 0.0001);
        for (int j = 0; j < 100000; j++) {
            path = new ArrayList<>();
            NanoGiga5000 nanoGiga5000 = new NanoGiga5000(new Vector2D(2, 2), new Vector2D(1, 0), clubObjects);
            while (i < 100){
                Pair<Double, Pair<Vector2D, Vector2D>> currentRewardState = nanoGiga5000.doRandomAction();
                path.add(currentRewardState);
                i++;
                if (currentRewardState.getB().getA().x() == 11 && currentRewardState.getB().getA().y() == 11) break;
            }
            avg += nanoGiga5000.getTotalTime()/(double)100000;
            for (int e = path.size()-1; e >= 0; e--) {
                if(path.size() == 100) break;
                double[] features = new double[4];
                features[0] = path.get(e).getB().getA().x();
                features[2] = path.get(e).getB().getA().y();
                features[1] = path.get(e).getB().getB().x();
                features[3] = path.get(e).getB().getB().y();
                w.updateWeights(nanoGiga5000.getTotalTime() - path.get(e).getA(), w.sumFeatureWeights(features),features);
            }
            i = 0;
        }
        System.out.println(avg);



    }

    public static ISofaClubObject[] clubObjectsSetUp(){
        double r = 100;
        ISofaClubObject[] circles = new ISofaClubObject[11];
        circles[0] = new Dock(new Vector2D(11, 11));
        circles[1] = new Circle(new Vector2D(5, 5));
        circles[2] = new Circle(new Vector2D(4, 5));
        circles[3] = new Circle(new Vector2D(6, 5));
        circles[4] = new Circle(new Vector2D(6, 2));
        circles[5] = new Circle(new Vector2D(2, 9));
        circles[6] = new Circle(new Vector2D(6, 12));
        circles[7] = new Circle(new Vector2D(8, 10));
        circles[8] = new Circle(new Vector2D(11, 2));
        circles[9] = new Circle(new Vector2D(11, 3));
        circles[10] = new Circle(new Vector2D(11, 7));
        return circles;
    }
}
