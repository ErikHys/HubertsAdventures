package utils;

/**
 * Container for a x and y, and possibility for a action value.
 */
public class Location implements Comparable{
    public int x, y;
    public int move;
    public Location(int locX, int locY, int move){
        x = locX;
        y = locY;
        this.move = move;
    }

    public Location(int locX, int locY){
        x = locX;
        y = locY;
        this.move = -1;
    }

    @Override
    public int compareTo(Object o) {
        Location location = (Location) o;
        if(location.x == x && location.y == y) return 0;
        return 1;
    }
}
