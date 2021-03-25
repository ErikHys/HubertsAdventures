package NanoGigaCleaner;

import utils.Vector2D;

public class Circle  implements ISofaClubObject {

    private Vector2D vector;
    private static final double VELOCITY = 0.0;
    private double RADIUS = 1.0;

    public Circle(Vector2D v){
        vector = v;
    }

    public Circle(Vector2D v, double radius){
        vector = v;
        RADIUS = radius;
    }

    @Override
    public void setVector(Vector2D u) {
        vector = u;
    }

    @Override
    public Vector2D getVector() {
        return vector;
    }

    @Override
    public double getVelocity() {
        return VELOCITY;
    }

    @Override
    public double getRadius() {
        return RADIUS;
    }

}
