package service3;

import lombok.Getter;
import service1.cargo.CargoType;
import service1.ship.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ShipsUnloading {
    private final int LOOSE_CRANE_PERFORMANCE = 3;
    private final int LIQUID_CRANE_PERFORMANCE = 2;
    private final int CONTAINER_CRANE_PERFORMANCE = 1;

    private int cranesQuantity = 3;

    @Getter
    private int fine;

    private ConcurrentLinkedQueue<Ship> looseCargos;
    private ConcurrentLinkedQueue<Ship> liquidCargos;
    private ConcurrentLinkedQueue<Ship> containerCargos;
    private List<Crane> cranes;

    public ShipsUnloading(List<Ship> schedule) {
        ShipsDistribution shipsDistribution = new ShipsDistribution(schedule);

        looseCargos = new ConcurrentLinkedQueue<>(shipsDistribution.getLooseCargos());
        liquidCargos = new ConcurrentLinkedQueue<>(shipsDistribution.getLooseCargos());
        containerCargos = new ConcurrentLinkedQueue<>(shipsDistribution.getContainerCargos());

        Crane looseCrane = new Crane(looseCargos);
        Crane liquidCrane = new Crane(liquidCargos);
        Crane containerCrane = new Crane(containerCargos);

        cranes = new ArrayList<>(cranesQuantity);
        cranes.add(looseCrane);
        cranes.add(liquidCrane);
        cranes.add(containerCrane);

        for (int i = 0; i < cranesQuantity; i++) {
            cranes.get(i).start();
        }

        this.fine = collectFines(cranes);
    }

    public int collectFines(List<Crane> cranes) {
        int pfine = 0;

        for (Crane crane : cranes) {
            pfine += crane.getCraneFine();
        }

        return pfine;
    }

    public void printInfo() {
        List<Ship> allShips = new ArrayList<>();
        allShips.addAll(looseCargos);
        allShips.addAll(liquidCargos);
        allShips.addAll(containerCargos);

        for (Ship ship : allShips) {
            System.out.println("\nShip's name: " + ship.getName()
                    + "\nShip's arrival time: " + ship.getArrivalTime()
                    + "\nShip's unloading time: " + ship.getUnloadingTime()
                    + "\nShip's cargo weight or quantity: " + ship.getCargo().getWeightOrQuantity());
        }

        //System.out.println("Total fine: " + fine + "\n");
    }
}
