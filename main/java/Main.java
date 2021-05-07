import service3.Schedule;
import service3.ShipsDistribution;
import service3.ShipsUnloading;

public class Main {
    public static void main(String[] args) {
        Schedule schedule = new Schedule();
        schedule.generateSchedule();

        ShipsDistribution shipsDistribution = new ShipsDistribution(schedule.getSchedule());

        ShipsUnloading looseShipsUnloading = new ShipsUnloading(shipsDistribution.getLooseCargos());
        ShipsUnloading liquidShipsUnloading = new ShipsUnloading(shipsDistribution.getLiquidCargos());
        ShipsUnloading containerShipsUnloading = new ShipsUnloading(shipsDistribution.getContainerCargos());

        looseShipsUnloading.start();
        liquidShipsUnloading.start();
        containerShipsUnloading.start();

        //shipsUnloading.printInfo();
    }
}
