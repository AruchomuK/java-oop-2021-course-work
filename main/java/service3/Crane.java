package service3;

import lombok.Getter;
import service1.ship.Ship;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Crane implements Callable<Object> {

    private int currentTime = -43200;

    @Getter
    private int craneFine = 0;

    @Getter
    private ConcurrentLinkedQueue<Ship> ships;

    public Crane(ConcurrentLinkedQueue<Ship> ships) {
        this.ships = ships;
    }

    @Override
    public Object call() throws InterruptedException {
        while (!ships.isEmpty()) {
            Ship currentShip = ships.poll();

            currentTime = Math.max(currentTime, currentShip.getArrivalTime());
            currentTime += currentShip.getUnloadingTime();

            Ship nextShip = ships.peek();

            if (nextShip == null) {
                break;
            }

            if (nextShip.getArrivalTime() < currentTime) {
                craneFine += calculateFine(currentTime - nextShip.getArrivalTime());
            }

            Thread.sleep(10);
        }
        return null;
    }

    public int calculateFine(int penaltyTime) {
        if (penaltyTime % 60 == 0) {
            return 100 * (penaltyTime / 60);
        }

        return 100 * (penaltyTime / 60 + 1);
    }
}
