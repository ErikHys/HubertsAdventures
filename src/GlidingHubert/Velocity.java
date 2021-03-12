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

    /**
     * Initializes a velocity, a container to keep the different velocity values, Air vertical velocity is set to 1
     * as gravity
     * @param vAH Air horizontal velocity, Hubert's steering
     * @param vWV Updraft
     * @param vWH Horizontal wind
     */
    public Velocity(int vAH, int vWV, int vWH){
        vAirVert = 1;
        vAirHor = vAH;
        vWindVert = vWV;
        vWindHor = vWH;
    }

    /**
     *
     * @return The vertical velocity, air vertical + wind vertical
     */
    public int getVert(){
        return vAirVert + vWindVert;
    }

    /**
     *
     * @return the horizontal velocity, air horizontal + wind horizontal
     */
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
