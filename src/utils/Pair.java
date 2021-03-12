package utils;

public class Pair<T, S> {

    private final T a;
    private final S b;
    public Pair(T a, S b){
        this.a = a;
        this.b = b;
    }

    public T getA() {
        return a;
    }

    public S getB() {
        return b;
    }
}
