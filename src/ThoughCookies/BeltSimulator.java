package ThoughCookies;

import utils.TrainableApproximators.FourierBasis;

import java.util.Random;

public class BeltSimulator {
    private final FourierBasis fb;
    private final double beta;
    private double avgR;
    private Random random;
    private Cookie cookie;
    private Hubert hubert;


    public BeltSimulator(Hubert hubert){
        random = new Random();
        this.hubert = hubert;
        this.cookie = new Cookie(10.0*random.nextDouble());
        this.fb = new FourierBasis(4, 0.01);
        this.beta = 0.01;
        this.avgR = 0;
    }

    public void randomStep(){
        double lastPos = hubert.getPosition();
        hubert.doAction(random.nextInt(3)-1);
        baseStep(lastPos);

    }

    /**
     * TODO fix to nstep
     * @param action
     * @return
     */
    public int fourierBasisStep(int action){
        double[] oldState = new double[]{hubert.getPosition(), hubert.getVelocity(), cookie.getPosition(), action};
        double lastPos = hubert.getPosition();
        double currentCost = hubert.getCost();
        hubert.doAction(action);
        baseStep(lastPos);
        double newCost = hubert.getCost();
        double r = newCost-currentCost;
        int actionPrime = getFourierStep();
        double delta = r - avgR + fb.predict(new double[]{hubert.getPosition(), hubert.getVelocity(), cookie.getPosition(), actionPrime})
                - fb.predict(oldState);
        avgR += beta*delta;
        fb.updateWeights(delta, oldState);
        return actionPrime;
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

        int action = 0;
        for (int i = 0; i < 1000000000; i++) {

             action = beltSimulator.fourierBasisStep(action);

        }
        System.out.println(beltSimulator.avgR);

    }
}
