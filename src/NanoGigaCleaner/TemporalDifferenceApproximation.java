package NanoGigaCleaner;

import utils.Pair;
import utils.ReadAndWrite;
import utils.TrainableApproximators.CoarseCoding;
import utils.Vector2D;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TemporalDifferenceApproximation {


    public static void main(String[] args) throws IOException {
        ISofaClubObject[] clubObjects = clubObjectsSetUp();

        ArrayList<Pair<Double, Pair<Vector2D, Vector2D>>> path = null;
        int i = 0;
        double avg = 0;
        double gamma = 0.7;
        CoarseCoding w = new CoarseCoding(13, 13, 8, 0.1);
        for (int j = 0; j < 100000; j++) {
            path = new ArrayList<>();
            NanoGiga5000 nanoGiga5000 = new NanoGiga5000(new Vector2D(2, 2), new Vector2D(1, 0), clubObjects);
            while (i < 100){
                Vector2D prevState = nanoGiga5000.getVector();
                Pair<Double, Pair<Vector2D, Vector2D>> currentRewardState = nanoGiga5000.doRandomAction();
                path.add(currentRewardState);
                i++;
                if (currentRewardState.getB().getA().x() == 11 && currentRewardState.getB().getA().y() == 11) break;
                Vector2D features = currentRewardState.getB().getA();
                Pair<Double, int[][]> predictedPair = w.predict(features);
                Pair<Double, int[][]> predictedLastPair = w.predict(prevState);
                w.updateWeights(nanoGiga5000.getTotalTime() + gamma*predictedPair.getA(), predictedLastPair.getA(), predictedLastPair.getB());
                if (currentRewardState.getB().getA().x() == 11 && currentRewardState.getB().getA().y() == 11) break;

            }
            if(j < 5){
                Vector2D[] p = new Vector2D[path.size()];
                ArrayList<Pair<Double, Pair<Vector2D, Vector2D>>> finalPath = path;
                Arrays.setAll(p, f -> finalPath.get(f).getB().getA());
                ReadAndWrite.writePath(p, "NanoGigaVIZTD" + j + ".txt");
            }
            avg += nanoGiga5000.getTotalTime()/(double)100000;

            i = 0;
        }
        System.out.println(avg);
        ReadAndWrite.writeWeights(w.getWeights());




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
