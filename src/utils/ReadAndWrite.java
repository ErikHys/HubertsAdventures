package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadAndWrite {

    public static int[][] getGrid(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        ArrayList<String[]> input = new ArrayList<>();
        while (sc.hasNext()){
            input.add(sc.nextLine().split("\t"));
        }
        String[] fll = input.get(0);
        int[][] grid = new int[input.size()][fll.length];

        for (int i = 0; i < grid[0].length; i++) {
            grid[0][i] = Integer.parseInt(fll[i]);
        }
        for (int i = 1; i < grid.length; i++) {
            fll = input.get(i);
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = Integer.parseInt(fll[j]);
            }
        }
        return grid;
    }

    public static void writePath(ArrayList<Location> path, String name) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(name));
        for (Location l: path){
            writer.write(l.x + " " + l.y + "\n");
        }
        writer.close();
    }
}
