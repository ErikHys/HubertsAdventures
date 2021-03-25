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


    public boolean collision(ISofaClubObject circle) {
        Vector2D vectorPosSub = this.vector.sub(circle.getVector());
        return vectorPosSub.mul(vectorPosSub) <= 1.1;
    }


    public double getTotalTime() {
        return totalTime;
    }

    public Pair<Double, Vector2D> whenCollide(ISofaClubObject circle){

        double a = vectorVelocity.mul(vectorVelocity);
        Vector2D vectorPosSub = this.vector.sub(circle.getVector());
        double b = vectorVelocity.mul(vectorPosSub);
        double d = b * b - a * (vectorPosSub.mul(vectorPosSub) - Math.pow(circle.getRadius(), 2.0));
        // Set time to  negative value to know if they collide
        double time = -1.0;
        if (d >= 0){
            double t0 = (-b - Math.sqrt(d));
            double t1 = (-b + Math.sqrt(d));

            time = (-b - Math.sqrt(d)) >= 0 ? (-b - Math.sqrt(d)) : -1;
            if (((-b - Math.sqrt(d)) > (-b + Math.sqrt(d)) && (-b + Math.sqrt(d)) > 0) || ((-b - Math.sqrt(d)) < 0 && (-b + Math.sqrt(d)) > 0)){
                time = (-b + Math.sqrt(d));
            }
            time = time > 0.00001 ? time - 0.000001 : time;
        }

        return new Pair<Double, Vector2D>(time, vector.add(vectorVelocity.mul(time)));
    }


    public Pair<Double, Pair<Vector2D, Vector2D>> doRandomAction(){
        ArrayList<Pair<Double, Vector2D>> futureCollisions = new ArrayList<>();
        double time = 0;
        if(collision(dock)) return new Pair<>(totalTime, new Pair<>(new Vector2D(11, 11), vectorVelocity));
        insideCircle();
        for (ISofaClubObject circle: clubObjects){
            Pair<Double, Vector2D> collisionTimeLocation = whenCollide(circle);
            if (collisionTimeLocation.getA() >= 0){
                futureCollisions.add(collisionTimeLocation);
            }
        }
        futureCollisions.add(hitWall());
        futureCollisions.sort(Comparator.comparingDouble(Pair::getA));
        Pair<Double, Vector2D> rewardState = futureCollisions.get(0);


        vector = rewardState.getB();
        time = rewardState.getA();

        double action = getValidDir();
        changeDir(action);
        time += 1;

        totalTime += time;
        return new Pair<>(totalTime, new Pair<Vector2D, Vector2D>(vector, vectorVelocity));
    }

    private void insideCircle(){
        //Move just outside circle
        for(ISofaClubObject clubObject: clubObjects){
            if(collision(clubObject)){
                vector = clubObject.getVector().add(vector.sub(clubObject.getVector()).mul(clubObject.getRadius()*1.1));
            }
        }

    }
    private double getValidDir() {
        return random.nextDouble() * 2 * Math.PI;
    }

    public void changeDir(double rad){
        vectorVelocity = vectorVelocity.rotate(rad);
    }


    private Pair<Double, Vector2D> hitWall() {
        double timeXU = (13 - vector.x())/vectorVelocity.x() > 0 ? Math.abs((13 - vector.x())/vectorVelocity.x()) : 13*13;
        double timeYU = (13 - vector.y())/vectorVelocity.y() > 0 ? Math.abs((13 - vector.y())/vectorVelocity.y()) : 13*13;
        double timeXL = (0 - vector.x())/vectorVelocity.x() > 0 ? Math.abs((0 - vector.x())/vectorVelocity.x()) : 13*13;
        double timeYL = (0 - vector.y())/vectorVelocity.y() > 0 ? Math.abs((0 - vector.y())/vectorVelocity.y()) : 13*13;
        double timeU = Math.min(timeXU, timeYU);
        double timeL = Math.min(timeXL, timeYL);
        double time = Math.min(timeU, timeL) - 0.000001;
        Vector2D v = vector.add(vectorVelocity.mul(time));
        return new Pair<>(time, v);
    }
}
