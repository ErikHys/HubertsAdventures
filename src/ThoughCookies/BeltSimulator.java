package ThoughCookies;

import utils.TrainableApproximators.FourierBasis;

import java.util.Random;

public class BeltSimulator {
    private final FourierBasis fb;
    private final double beta;
    private Random random;
    private Cookie cookie;
    private Hubert hubert;


    public BeltSimulator(Hubert hubert){
        random = new Random();
        this.hubert = hubert;
        this.cookie = new Cookie(10.0*random.nextDouble());
        this.fb = new FourierBasis(4, 0.01);
        this.beta = 0.01;
    }

    public void randomStep(){
        double lastPos = hubert.getPosition();
        hubert.doAction(random.nextInt(3)-1);
        baseStep(lastPos);

    }

    public double fourierBasisStep(){
        double lastPos = hubert.getPosition();
        double currentCost = hubert.getCost();
        int action = getFourierStep();
        hubert.doAction(action);
        baseStep(lastPos);
        double newCost = hubert.getCost();
        return newCost-currentCost;


    }

    private void baseStep(double lastPos) {
        cookie.ageCookie();
        hubert.addCost(cookie.getAge() >= 5 ? -0.5 : (Math.min(lastPos, hubert.getPosition()) <= cookie.getPosition() &&
                Math.max(lastPos, hubert.getPosition()) >= cookie.getPosition()) ? hubert.getVelocity() <= 4 ? 1 : -1 : 0);
        cookie = cookie.getAge() >= 5 ? new Cookie(10.0*random.nextDouble()): cookie;
    }

    private int getFourierStep() {
        double[] state = new double[]{hubert.getPosition(), hubert.getVelocity(), cookie.getPosition(), 0.0};
        int moveIdx = -1;
        double moveValue = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < 3; i++) {
            double value = fb.predict(state);
            if(moveValue < value){
                moveIdx = i;
                moveValue = value;
            }
            state[3] += 0.5;
        }
        return moveIdx >= 0 ? moveIdx-1 : 0;
    }

    public static void main(String[] args) {
        Hubert hubert = new Hubert(5.0);
        BeltSimulator beltSimulator = new BeltSimulator(hubert);

        for (int i = 0; i < 100000000; i++) {
            double r = beltSimulator.fourierBasisStep();

        }
        System.out.println(5*hubert.getCost()/100000000);

    }
}
