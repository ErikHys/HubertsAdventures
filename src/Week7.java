import GlidingHubert.Hubert;
import utils.ReadAndWrite;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Week7 {

    public static void main(String[] args) throws FileNotFoundException {

        int[][] skyline = ReadAndWrite.getGrid("Inputs/skyline.txt");
        int[][] hWind = ReadAndWrite.getGrid("Inputs/horizontal_wind.txt");
        int[][] vWind = ReadAndWrite.getGrid("Inputs/vertical_wind.txt");

        ArrayList<Thread> threads = new ArrayList<>();

        Hubert hubert = new Hubert(5, 13, skyline[0].length, skyline.length, skyline, vWind, hWind);

    }
}
