package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadAndWrite {

    /**
     * Read a 2 dim grid from file, of ints
     * @param fileName
     * @return 2 dim int grid
     * @throws FileNotFoundException    Check if you specified directory
     */
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

    /**
     * Write a path to a txt file
     * @param path Path of locations containing x, y
     * @param name Name of path
     * @throws IOException
     */
    public static void writePath(ArrayList<Location> path, String name) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(name));
        for (Location l: path){
            writer.write("paths/" + l.x + " " + l.y + "\n");
        }
        writer.close();
    }
}
