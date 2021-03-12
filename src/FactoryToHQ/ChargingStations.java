package FactoryToHQ;

import utils.Location;

public class ChargingStations {
    private Location[] chargingLocations;
    private int[] costs;
    private int[] costsC;

    public ChargingStations(){
        chargingLocations = new Location[8];
        costs = new int[8];
        costsC = new int[8];
        chargingLocations[0] = new Location(21, 0);
        chargingLocations[1] = new Location(17, 14);
        chargingLocations[2] = new Location(11, 23);
        chargingLocations[3] = new Location(28, 23);
        chargingLocations[4] = new Location(1, 28);
        chargingLocations[5] = new Location(1, 30);
        chargingLocations[6] = new Location(2, 2);
        chargingLocations[7] = new Location(27, 30);
        costs[0] = 100;
        costsC[0] = 0;
        costs[1] = 22;
        costsC[1] = 2;
        costs[2] = 13;
        costsC[2] = 0;
        costs[3] = 10;
        costsC[3] = 0;
        costs[4] = 18;
        costsC[4] = 1;
        costs[5] = 10;
        costsC[5] = 0;
        costs[6] = 1;
        costsC[6] = 0;
        costs[7] = -1;
        costsC[7] = 0;
    }

    public int getCost(Location currentLocation, int charge){
        for (int i = 0; i < chargingLocations.length; i++) {
            if (currentLocation.x == chargingLocations[i].x && currentLocation.y == chargingLocations[i].y){
                return costHelper(i, charge);
            }
        }
        return 0;
    }

    private int costHelper(int i, int charge) {
        if (i == 3){
            if (charge < 7){
                return 10;
            }else {
                return 1;
            }
        }
        return costs[i] - costsC[i]*charge;
    }
}
