package NanoGigaCleaner;

import utils.Vector2D;

public class NanoGiga5000 implements SofaClubObject{

    private Vector2D vector;
    private Vector2D vectorVelocity;
    private static final double VELOCITY = 1.0;
    private static final double RADIUS = 0.0;

    public NanoGiga5000(Vector2D v, Vector2D vectorVelocity){
        vector = v;
        this.vectorVelocity = vectorVelocity;
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

    public boolean collisions(Circle[] circles){
        for (Circle circle: circles){
            if (collision(circle)) return true;
        }
        return false;
    }

    public boolean collision(Circle circle) {
        Vector2D vectorPosSub = this.vector.sub(circle.getVector());
        System.out.println(vectorPosSub.mul(vectorPosSub));
        return vectorPosSub.mul(vectorPosSub) <= 1;
    }

    public double whenCollide(Circle circle){
        double a = vectorVelocity.mul(vectorVelocity);
        Vector2D vectorPosSub = this.vector.sub(circle.getVector());
        double b = vectorVelocity.mul(vectorPosSub);
        double d = b * b - a * vectorPosSub.mul(vectorPosSub);
        double time = Double.MAX_VALUE;
        if (d >= 0){
            time = (-b - Math.sqrt(d))/a;
        }
        return time;
    }

    public void changeDir(double deg){
        //TODO

    }
}
