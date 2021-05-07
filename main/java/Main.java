import service1.ship.Ship;
import service2.JSONReader;
import service3.Schedule;
import service3.ShipsDistribution;
import service3.ShipsUnloading;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Schedule schedule = new Schedule();
        schedule.generateSchedule();

        JSONReader jsonReader = new JSONReader();
        List<Ship> shipListFromSchedule = jsonReader.readSchedule();

        ShipsDistribution shipsDistribution = new ShipsDistribution(shipListFromSchedule);

        ShipsUnloading looseShipsUnloading = new ShipsUnloading(shipsDistribution.getLooseCargos());
        ShipsUnloading liquidShipsUnloading = new ShipsUnloading(shipsDistribution.getLiquidCargos());
        ShipsUnloading containerShipsUnloading = new ShipsUnloading(shipsDistribution.getContainerCargos());

        looseShipsUnloading.start();
        liquidShipsUnloading.start();
        containerShipsUnloading.start();

        //shipsUnloading.printInfo();
    }
}
