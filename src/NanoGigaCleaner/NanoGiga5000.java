package NanoGigaCleaner;

import utils.Pair;
import utils.Vector2D;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class NanoGiga5000 implements ISofaClubObject {

    private ISofaClubObject dock = null;
    private Vector2D vector;
    private Vector2D vectorVelocity;
    private static final double VELOCITY = 1.0;
    private static final double RADIUS = 0.0;
    private double totalTime = 0;
    private final Random random;
    private ISofaClubObject[] clubObjects = null;

    public NanoGiga5000(Vector2D v, Vector2D vectorVelocity, ISofaClubObject[] objects){
        vector = v;
        this.vectorVelocity = vectorVelocity;
        random = new Random();
        clubObjects = objects;
        dock = objects[0];
    }
    public NanoGiga5000(Vector2D v, Vector2D vectorVelocity){
        vector = v;
        this.vectorVelocity = vectorVelocity;
        random = new Random();
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

    public Pair<Double, Vector2D> doRandomAction(){
        ArrayList<Pair<Double, Vector2D>> futureCollisions = new ArrayList<>();
        double time = 0;

        for (ISofaClubObject circle: clubObjects){
            Pair<Double, Vector2D> collisionTimeLocation = whenCollide(circle);
            if (collisionTimeLocation.getA() >= 0){
                futureCollisions.add(collisionTimeLocation);
            }
        }
        Pair<Double, Vector2D> rewardState;
        if(futureCollisions.size() > 0){
            futureCollisions.sort(Comparator.comparingDouble(Pair::getA));
            rewardState = futureCollisions.get(0);
        }else {
            rewardState = hitWall();

        }
        //TODO FIX TO propose move

        if(rewardState.getB().x() >= 0 && rewardState.getB().x() <= 13 && rewardState.getB().y() <= 13 && rewardState.getB().y() >= 0){
            vector = rewardState.getB();
            time = rewardState.getA();
        }
        double action = getValidDir();
        changeDir(action);
        time += 1;
        if(collisions(clubObjects)){
            if(collision(dock)) return new Pair<>(totalTime, new Vector2D(11, 11));
            totalTime += time;
            return new Pair<>(time, vector);
        }
        totalTime += time;
        return new Pair<>(time, vector);
    }

    private double getValidDir() {
        return random.nextDouble() * 2 * Math.PI;
    }

    private Pair<Double, Vector2D> hitWall() {
        //TODO IMPLEMENT NICER
        double timeXU = Math.abs((13 - vector.x())/vectorVelocity.x()) > 0 ? Math.abs((13 - vector.x())/vectorVelocity.x()) : Double.MAX_VALUE;
        double timeYU = Math.abs((13 - vector.y())/vectorVelocity.y()) > 0 ? Math.abs((13 - vector.y())/vectorVelocity.y()) : Double.MAX_VALUE;
        double timeXL = Math.abs((0 - vector.x())/vectorVelocity.x()) > 0 ? Math.abs((0 - vector.x())/vectorVelocity.x()) : Double.MAX_VALUE;
        double timeYL = Math.abs((0 - vector.y())/vectorVelocity.y()) > 0 ? Math.abs((0 - vector.y())/vectorVelocity.y()) : Double.MAX_VALUE;
        double timeU = Math.min(timeXU, timeYU);
        double timeL = Math.min(timeXL, timeYL);
        double time = Math.min(timeU, timeL);
        Vector2D v = vector.add(vectorVelocity.mul(time));
        return new Pair<>(time, v);
    }


    public void changeDir(double rad){
        //Change dir not hit wall
        vectorVelocity = vectorVelocity.rotate(rad);
    }
}
