package NanoGigaCleaner;

import utils.Vector2D;

public class Dock implements ISofaClubObject {
    private Vector2D vector;
    private static final double VELOCITY = 0.0;
    private static final double RADIUS = 1.0;

    public Dock(Vector2D v){
        vector = v;
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
