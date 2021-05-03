package service3;

import lombok.Getter;
import lombok.Setter;
import service1.cargo.CargoType;
import service1.ship.Ship;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Crane extends Thread {

    private int currentTime = -43200;

    @Getter
    private int craneFine = 0;

    @Getter
    private ConcurrentLinkedQueue<Ship> ships;

    public Crane(ConcurrentLinkedQueue<Ship> ships) {
        this.ships = ships;
    }

    @Override
    public void run() {
        while (!ships.isEmpty()) {
            Ship currentShip = ships.poll();

            currentTime = Math.max(currentTime, currentShip.getArrivalTime());

            //int currentShipWorkingCranes = currentShip.getWorkingCranesQuantity();

            //currentShip.setWorkingCranesQuantity(currentShipWorkingCranes + 1);

                /*if (currentShip.getWorkingCranesQuantity() == 2) {
                    int newUnloadingTime = currentShip.calculateUnloadingTime(currentShip.getCargo(),
                            currentShip.getWorkingCranesPerformance() * 2);
                    currentShip.setUnloadingTime(newUnloadingTime);
                }*/

            currentTime += currentShip.getUnloadingTime();

            Ship nextShip = ships.peek();

            if (nextShip == null) {
                break;
            }

            if (nextShip.getArrivalTime() < currentTime) {
                craneFine += calculateFine(currentTime - nextShip.getArrivalTime());
            }
        }
    }

    public int calculateFine(int penaltyTime) {
        if (penaltyTime % 60 == 0) {
            return 100 * (penaltyTime / 60);
        }

        return 100 * (penaltyTime / 60 + 1);
    }
}
