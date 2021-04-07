package ThoughCookies;

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

    public double getCost() {
        return cost;
    }

    public void reset(){
        cost = 0;
        position = 5.0;
        velocity = 0;
    }

    public double nextPos(int i) {
        return position > 10 || position < 0 ?  position < 0 ? 0 : 10 : position + nextVel(i)*timeStep;
    }

    public double nextVel(int i) {
        return velocity + (i*5.0 + (- Math.abs(velocity)*velocity * 0.05)) * timeStep;
    }
}
