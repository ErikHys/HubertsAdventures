package utils;


public class Sars<T, A, R> {
    private final T state;
    private final T newState;
    private final A action;
    private final R reward;

    public Sars(T state, A action, R reward, T newState){
        this.state = state;
        this.action = action;
        this.reward = reward;
        this.newState = newState;
    }


    public T getState() {
        return state;
    }

    public A getAction() {
        return action;
    }

    public R getReward() {
        return reward;
    }

    public T getNewState() {
        return newState;
    }
}
