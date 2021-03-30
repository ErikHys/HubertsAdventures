package ThoughCookies;

import java.util.Random;

public class Hubert {

    private final double timeStep;
    private double position;
    private double velocity;
    private double rocketAcceleration;
    private double frictionAcceleration;
    private double cost;

    public Hubert(double position){
        this.position = position;
        velocity = 0;
        rocketAcceleration = 0;
        frictionAcceleration = 0;
        cost = 0;
        timeStep = 0.2;
    }

    public double doAction(int action){
        rocketAcceleration = action*5.0;
        frictionAcceleration = - Math.abs(velocity)*velocity * 0.05;
        velocity += (rocketAcceleration+frictionAcceleration)*timeStep;
        position += velocity*timeStep;
        if(position > 10 || position < 0){
            addCost(- Math.pow(getVelocity(), 2.0) * 0.1);
            position = position < 0 ? 0 : 10;
            velocity = 0;
        }
        return position;
    }

    public double getPosition() {
        return position;
    }

    public double getVelocity(){
        return velocity;
    }

    public void addCost(double kr){
        cost += kr;
    }

    public static void main(String[] args) {
//        Hubert hubert = new Hubert(0);
//        while (hubert.getPosition() < 10){
//            System.out.println(hubert.doAction(1));
//        }
//        while (hubert.getPosition() > 0){
//            System.out.println(hubert.doAction(-1));
//        }
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            System.out.println(random.nextInt(3));
        }


    }

    public double getCost() {
        return cost;
    }
}
