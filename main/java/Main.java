import service1.Schedule;
import service1.ship.Ship;
import service2.JSONReader;
import service3.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        Schedule schedule = new Schedule();
        schedule.generateSchedule();

        JSONReader jsonReader = new JSONReader();
        List<Ship> shipListFromSchedule = jsonReader.readSchedule("schedule.json");

        ShipsDistribution shipsDistribution = new ShipsDistribution(shipListFromSchedule);

        ShipsUnloading looseShipsUnloading = new ShipsUnloading(shipsDistribution.getLooseCargos());
        ShipsUnloading liquidShipsUnloading = new ShipsUnloading(shipsDistribution.getLiquidCargos());
        ShipsUnloading containerShipsUnloading = new ShipsUnloading(shipsDistribution.getContainerCargos());

        List<ShipsUnloading> unloadings = new ArrayList<>(3);

        unloadings.add(looseShipsUnloading);
        unloadings.add(liquidShipsUnloading);
        unloadings.add(containerShipsUnloading);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            List<Future<Object>> stat = executor.invokeAll(unloadings);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();

        Statistics statistics = new Statistics();
        statistics.printInfo(unloadings);
    }
}
