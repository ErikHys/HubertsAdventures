package utils;

import GlidingHubert.LeftRight;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        BufferedWriter writer = new BufferedWriter(new FileWriter("paths/" + name));
        for (Location l: path){
            writer.write(l.x + " " + l.y + "\n");
        }
        writer.close();
    }

    /**
     * Write a path to a txt file, bad implementation because I didn't implement inheritance for vector and location
     */
    public static void writePath(Vector2D[] path, String name) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("paths/" + name));
        for (Vector2D l: path){
            writer.write(l.x() + " " + l.y() + "\n");
        }
        writer.close();
    }
    public static ArrayList<Sars<Location, LeftRight, Integer>> readSarsPath(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        ArrayList<Sars<Location, LeftRight, Integer>> txtPath = new ArrayList<>();
        while (sc.hasNext()){
            String[] rawInput = sc.nextLine().split("\t");
            Location s = new Location(Integer.parseInt(rawInput[1])-1, Integer.parseInt(rawInput[0])-1);
            LeftRight a = Integer.parseInt(rawInput[2]) == 2 ? LeftRight.RIGHT : LeftRight.LEFT;
            int r = Integer.parseInt(rawInput[3]);
            Location sPrime = new Location(Integer.parseInt(rawInput[5])-1, Integer.parseInt(rawInput[4])-1);
            txtPath.add(new Sars<Location, LeftRight, Integer>(s, a, r, sPrime));
        }
        return txtPath;
    }

    public static void writeModel(double[][][] model) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("paths/model.txt"));
        for (double[][] rows: model){
            for (double[] cols: rows){
                writer.write((cols[0] + cols[1]) + " ");
            }
            writer.write("\n");
        }
        writer.close();

    }

    public static void writeWeights(double[][] weights) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("paths/TDweights.txt"));
        Collections.reverse(Arrays.asList(weights));

        for (double[] rows: weights){
            for (double cols: rows){
                writer.write((-cols) + " ");
            }
            writer.write("\n");
        }
        writer.close();
    }
}
