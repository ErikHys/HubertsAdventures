package GlidingHubert;

public enum LeftRight {
    LEFT(0), RIGHT(1);

    private final int value;
    private LeftRight(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
