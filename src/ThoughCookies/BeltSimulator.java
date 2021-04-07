package ThoughCookies;

import utils.ReadAndWrite;
import utils.TrainableApproximators.FourierBasis;
import utils.TrainableApproximators.IApproximator;
import utils.TrainableApproximators.LinearCombination;
import utils.Vector2D;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BeltSimulator {
    private final IApproximator fb;
    private final double beta;
    private final double epsilon;
    private double avgR;
    private final Random random;
    private Cookie cookie;
    private final Hubert hubert;


    public BeltSimulator(Hubert hubert, double beta, double lr, double epsilon){
        random = new Random();
        this.hubert = hubert;
        this.cookie = new Cookie(10.0*random.nextDouble());
        this.fb = new FourierBasis(4, lr);
        this.beta = beta;
        this.avgR = 0;
        this.epsilon = epsilon;

    }

    public void randomStep(){
        double lastPos = hubert.getPosition();
        hubert.doAction(random.nextInt(3)-1);
        baseStep(lastPos);

    }

    public int fourierBasisOneStep(int action){
        double[] oldState = new double[]{hubert.getPosition(), hubert.getVelocity(), cookie.getPosition(), cookie.getAge()};
        double lastPos = hubert.getPosition();
        double currentCost = hubert.getCost();
        hubert.doAction(action);
        baseStep(lastPos);
        double newCost = hubert.getCost();
        double r = newCost-currentCost;
        int actionPrime = getFourierStep();
        double oldPred = fb.predict(oldState);
        double delta = r - avgR + fb.predict(new double[]{hubert.getPosition(), hubert.getVelocity(), cookie.getPosition(), cookie.getAge()})
                - oldPred;
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
        double p = random.nextDouble();
        int moveIdx = -2;
        if(p < epsilon){
            moveIdx = random.nextInt(3)-1;
        }else {
            double moveValue = Double.NEGATIVE_INFINITY;
            for (int i = -1; i < 2; i++) {
                double[] state = getNextState(i);
                double value = fb.predict(state);
                if (moveValue < value) {
                    moveIdx = i;
                    moveValue = value;
                }
            }
        }
        return moveIdx >= -1 ? moveIdx : random.nextInt(3)-1;
    }

    private double[] getNextState(int i) {
        double cPos = cookie.getPosition();
        double hPos = hubert.nextPos(i);
        double hVel = hubert.nextVel(i);
        double cAge = cookie.getAge() + cookie.getTimeStep();
        return new double[]{hPos, hVel, cPos, cAge};
    }

    public IApproximator getFb() {
        return fb;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[1];

        double[] betas = new double[]{0.1, 0.01, 0.001, 0.0001, 0.00001};
        double[] alphas = new double[]{0.01, 0.001, 0.0001, 0.00001};
        int idx = 0;
        for (double beta: betas){
            for (double alpha: alphas){
                Thread thread = new Thread(() -> {
                    Hubert hubert = new Hubert(5.0);
                    BeltSimulator beltSimulator = new BeltSimulator(hubert, beta, alpha, 0.05);

                    int action = 0;
                    for (int i = 0; i < 1000000; i++) {

                        action = beltSimulator.fourierBasisOneStep(action);
                    }
                    hubert.reset();
                    beltSimulator.avgR = 0;
                    action = 0;
                    Vector2D[] path = new Vector2D[1000];
                    for (int i = 0; i < 1000; i++) {
                        path[i] = new Vector2D(hubert.getPosition(), beltSimulator.cookie.getPosition());
                        action = beltSimulator.fourierBasisOneStep(action);
                    }
                    try {
                        ReadAndWrite.writePath(path, "cookiepath.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(beta + " <- beta, alpha -> " + alpha);
                    System.out.println(beltSimulator.avgR);
                    System.out.println(hubert.getCost());
                    System.out.println();
                });
                thread.start();
                threads[idx] = thread;
                idx++;
            }
        }
        for(Thread t: threads){
            t.join();
        }


    }
}
