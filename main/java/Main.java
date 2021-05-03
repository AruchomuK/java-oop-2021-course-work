import service3.Schedule;
import service3.ShipsUnloading;

public class Main {
    public static void main(String[] args) {
        Schedule schedule = new Schedule();
        ShipsUnloading shipsUnloading = new ShipsUnloading(schedule.generateSchedule());

        shipsUnloading.printInfo();
        System.out.println("\nTotal fine: " + shipsUnloading.getFine());
    }
}
