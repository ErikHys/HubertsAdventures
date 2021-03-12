package GlidingHubert;

public class Velocity {
    public int vAirVert;
    public int vAirHor;
    public int vWindVert;
    public int vWindHor;

    public Velocity(int vAV, int vAH, int vWV, int vWH){
        vAirVert = vAV;
        vAirHor = vAH;
        vWindVert = vWV;
        vWindHor = vWH;
    }

    public Velocity(int vAH, int vWV, int vWH){
        vAirVert = 1;
        vAirHor = vAH;
        vWindVert = vWV;
        vWindHor = vWH;
    }

    public int getVert(){
        return vAirVert + vWindVert;
    }

    public int getHor(){
        return vAirHor + vWindHor;
    }

    public void setvAirHor(int vAirHor) {
        this.vAirHor = vAirHor;
    }

    public void setvAirVert(int vAirVert) {
        this.vAirVert = vAirVert;
    }

    public void setvWindVert(int vWindVert) {
        this.vWindVert = vWindVert;
    }

    public void setvWindHor(int vWindHor) {
        this.vWindHor = vWindHor;
    }
}
