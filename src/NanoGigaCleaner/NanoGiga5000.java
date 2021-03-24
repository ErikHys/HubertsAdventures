package NanoGigaCleaner;

import utils.Pair;
import utils.Vector2D;

public class NanoGiga5000 implements ISofaClubObject {

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

    public Vector2D getVectorVelocity() {
        return vectorVelocity;
    }

    public boolean collisions(ISofaClubObject[] circles){
        // Out of bounds
        if(vector.x() >= 13 || vector.x() < 0 || vector.y() >= 13 || vector.y() < 0) return true;

        // Crash with circle
        for (ISofaClubObject circle: circles){
            if (collision(circle)) return true;
        }
        return false;
    }

    public boolean collision(ISofaClubObject circle) {
        Vector2D vectorPosSub = this.vector.sub(circle.getVector());
        return vectorPosSub.mul(vectorPosSub) <= 1;
    }

    public Pair<Double, Vector2D> whenCollide(ISofaClubObject circle){
        double a = vectorVelocity.mul(vectorVelocity);
        Vector2D vectorPosSub = this.vector.sub(circle.getVector());
        double b = vectorVelocity.mul(vectorPosSub);
        double d = b * b - a * vectorPosSub.mul(vectorPosSub);
        // Set time to  negative value to know if they collide
        double time = -1.0;
        if (d >= 0){
            time = (-b - Math.sqrt(d))/a;
        }
        // -1 for radius of circle
        time = time-circle.getRadius();
        Vector2D t = vector.add(vectorVelocity.mul(time));
        return new Pair<Double, Vector2D>(time, vector.add(vectorVelocity.mul(time)));
    }



    public void changeDir(double deg){
        vectorVelocity = vectorVelocity.rotate(deg);
    }
}
