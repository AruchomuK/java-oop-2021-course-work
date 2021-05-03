package service3;

import service1.ship.Ship;
import service1.ship.ShipGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Schedule {
    private final int MAX_ARRIVAL_DELAY_MINUTES = 10080;
    private final int MIN_ARRIVAL_DELAY_MINUTES = -10080;
    private final int MAX_UNLOADING_DELAY_MINUTES = 1440;

    Random rand = new Random();

    public int generateArrivalDelay() {
        return rand.nextInt(MAX_ARRIVAL_DELAY_MINUTES - MIN_ARRIVAL_DELAY_MINUTES) + MIN_ARRIVAL_DELAY_MINUTES;
    }

    public int generateUnloadingDelay() {
        return rand.nextInt(MAX_UNLOADING_DELAY_MINUTES);
    }

    public List<Ship> generateSchedule() {
        List<Ship> schedule = new ArrayList<Ship>();
        ShipGenerator shipGenerator = new ShipGenerator();

        for (int i = 0; i < 1000; i++) {
            Ship ship = shipGenerator.generateShip();
            ship.setArrivalTime(ship.getArrivalTime() + generateArrivalDelay());
            ship.setUnloadingTime(ship.getUnloadingTime() + generateUnloadingDelay());

            schedule.add(ship);
        }

        schedule.sort(Comparator.comparingInt(Ship::getArrivalTime));

        return schedule;
    }
}
