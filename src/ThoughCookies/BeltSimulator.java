package ThoughCookies;

import java.util.Random;

public class BeltSimulator {
    private Random random;
    private Cookie cookie;
    private Hubert hubert;


    public BeltSimulator(Hubert hubert){
        random = new Random();
        this.hubert = hubert;
        this.cookie = new Cookie(10.0*random.nextDouble());
    }

    public void step(){
        double lastPos = hubert.getPosition();
        hubert.doAction(random.nextInt(3)-1);
        cookie.ageCookie();
        hubert.addCost(cookie.getAge() >= 5 ? -0.5 : (Math.min(lastPos, hubert.getPosition()) <= cookie.getPosition() &&
                Math.max(lastPos, hubert.getPosition()) >= cookie.getPosition()) ? hubert.getVelocity() <= 4 ? 1 : -1 : 0);
        cookie = cookie.getAge() >= 5 ? new Cookie(10.0*random.nextDouble()): cookie;

    }

    public static void main(String[] args) {
        Hubert hubert = new Hubert(5.0);
        BeltSimulator beltSimulator = new BeltSimulator(hubert);

        for (int i = 0; i < 100000000; i++) {
            beltSimulator.step();
        }
        System.out.println(5*hubert.getCost()/100000000);

    }
}
