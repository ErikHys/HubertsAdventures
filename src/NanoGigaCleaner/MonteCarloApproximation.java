package NanoGigaCleaner;

import utils.Pair;
import utils.ReadAndWrite;
import utils.Vector2D;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MonteCarloApproximation {

    // FUN FUN FUN :)
    public static void main(String[] args) throws IOException {
        ISofaClubObject[] clubObjects = clubObjectsSetUp();
        NanoGiga5000 nanoGiga5000 = new NanoGiga5000(new Vector2D(2, 2), new Vector2D(1, 0), clubObjects);
        ArrayList<Pair<Double, Vector2D>> path = new ArrayList<>();
        int i = 0;
        while (i < 100000){
            Pair<Double, Vector2D> currentRewardState = nanoGiga5000.doRandomAction();
            if (currentRewardState == null) break;
            path.add(currentRewardState);
            i++;
        }
        System.out.println(path.size());
        Vector2D[] vectorPath = new Vector2D[path.size()];
        Arrays.setAll(vectorPath, a -> path.get(a).getB());
        ReadAndWrite.writePath(vectorPath, "../paths/NanoGigaVIZ.txt");

    }

    public static ISofaClubObject[] clubObjectsSetUp(){
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
