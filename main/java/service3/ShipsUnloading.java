package service3;

import lombok.Getter;
import service1.cargo.CargoType;
import service1.ship.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ShipsUnloading extends Thread {
    private final int CRANE_PRICE = 30000;

    @Getter
    private int cranesQuantity = 0;

    @Getter
    private int fine = 0;

    private List<Ship> ships;
    private ConcurrentLinkedQueue<Ship> queueOfShips;
    private List<Crane> cranes;

    public ShipsUnloading(List<Ship> ships) {
        this.ships = ships;
    }

    @Override
    public void run() {
        while (fine >= CRANE_PRICE * cranesQuantity) {
            queueOfShips = new ConcurrentLinkedQueue<>(ships);

            fine = 0;
            cranesQuantity++;

            cranes = new ArrayList<>(cranesQuantity);

            ExecutorService executor = Executors.newFixedThreadPool(cranesQuantity);

            for (int i = 0; i < cranesQuantity; i++) {
                Crane crane = new Crane(queueOfShips);
                cranes.add(crane);
            }

            try {
                List<Future<Object>> result = executor.invokeAll(cranes);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            executor.shutdown();

            fine = collectFines(cranes);
        }
    }

    public int collectFines(List<Crane> cranes) {
        int pfine = 0;

        for (Crane crane : cranes) {
            pfine += crane.getCraneFine();
        }

        return pfine;
    }

    /*
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

     */
}
