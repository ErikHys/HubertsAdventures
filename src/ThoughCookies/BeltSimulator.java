package ThoughCookies;

import utils.TrainableApproximators.FourierBasis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BeltSimulator {
    private final FourierBasis fb;
    private final double beta;
    private final double epsilon;
    private final ArrayList<Double> rewards;
    private final ArrayList<double[]> states;
    private final int nStep;
    private double avgR;
    private Random random;
    private Cookie cookie;
    private Hubert hubert;


    public BeltSimulator(Hubert hubert, double beta, double lr, double epsilon, int nStep){
        random = new Random();
        this.hubert = hubert;
        this.cookie = new Cookie(10.0*random.nextDouble());
        this.fb = new FourierBasis(4, lr);
        this.beta = beta;
        this.avgR = 0;
        this.epsilon = epsilon;
        this.nStep = nStep;
        this.rewards = new ArrayList<>();
        this.states = new ArrayList<>();
        rewards.add(hubert.getCost());
        double[] oldState = new double[]{hubert.getPosition(), hubert.getVelocity(), cookie.getPosition(), 0.0};
        states.add(oldState);

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
    public int fourierBasisOneStep(int action){
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

    public int fourierBasisNStep(int action){
        double[] oldState = new double[]{hubert.getPosition(), hubert.getVelocity(), cookie.getPosition(), action};
        double lastPos = hubert.getPosition();
        double currentCost = hubert.getCost();
        hubert.doAction(action);
        baseStep(lastPos);
        double newCost = hubert.getCost();
        double r = newCost-currentCost;

        int actionPrime = getFourierStep();
        double[] newState = new double[]{hubert.getPosition(), hubert.getVelocity(), cookie.getPosition(), actionPrime};
        rewards.add(r);
        states.add(newState);
        if(rewards.size()-nStep >= 0){
            double[] result = new double[rewards.size()];
            double lastPred = fb.predict(states.get(states.size()-1));
            double firstPred = fb.predict(states.get(0));
            Arrays.setAll(result, i -> rewards.get(i)-avgR + lastPred - firstPred);
            double delta = Arrays.stream(result).sum();
            avgR += beta*delta;
            fb.updateWeights(delta, oldState);
            rewards.remove(0);
            states.remove(0);
        }

        return actionPrime;
    }

    private void baseStep(double lastPos) {
        cookie.ageCookie();
        hubert.addCost(cookie.getAge() >= 5 ? -0.5 : (Math.min(lastPos, hubert.getPosition()) <= cookie.getPosition() &&
                Math.max(lastPos, hubert.getPosition()) >= cookie.getPosition()) ? hubert.getVelocity() <= 4 ? 1 : -1 : 0);
        cookie = cookie.getAge() >= 5 ? new Cookie(10.0*random.nextDouble()): cookie;
    }

    private int getFourierStep() {
        double p = random.nextDouble();
        int moveIdx = -2;
        if(p < epsilon){
            moveIdx = random.nextInt(3);
        }else {
            double[] state = new double[]{hubert.getPosition(), hubert.getVelocity(), cookie.getPosition(), 0.0};
            double moveValue = Double.NEGATIVE_INFINITY;
            for (int i = -1; i < 2; i++) {
                double value = fb.predict(state);
                if (moveValue < value) {
                    moveIdx = i;
                    moveValue = value;
                }
                state[3] += 1;
            }
        }
        return moveIdx >= -1 ? moveIdx : 0;
    }

    public FourierBasis getFb() {
        return fb;
    }

    public static void main(String[] args) {
        Hubert hubert = new Hubert(5.0);
        BeltSimulator beltSimulator = new BeltSimulator(hubert, 0.01, 0.1, 0.01, 5);

        int action = 0;
        for (int i = 0; i < 10000000; i++) {

             action = beltSimulator.fourierBasisOneStep(action);

        }

        System.out.println(beltSimulator.avgR);
        System.out.println(hubert.getCost());
        for(double weight: beltSimulator.getFb().getWeights()){
            System.out.println(weight);
        }

    }
}
