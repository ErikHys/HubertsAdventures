package ThoughCookies;

public class Cookie {
    private double age;
    private double position;
    private double timeStep;

    public Cookie(double position){
        this.position = position;
        age = 0;
        timeStep = 0.2;
    }

    public void ageCookie(){
        age += timeStep;
    }

    public double getAge() {
        return age;
    }

    public double getPosition() {
        return position;
    }

    public double getTimeStep() {
        return timeStep;
    }
}
