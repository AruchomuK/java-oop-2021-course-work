package service3;

import lombok.Getter;
import service1.ship.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class ShipsUnloading implements Callable<Object> {
    private final int CRANE_PRICE = 30000;

    @Getter
    private int cranesQuantity = 0;

    @Getter
    private int fine = 0;

    @Getter
    private List<Ship> ships;

    private ConcurrentLinkedQueue<Ship> queueOfShips;

    private List<Crane> cranes;

    public ShipsUnloading(List<Ship> ships) {
        this.ships = ships;
    }

    @Override
    public Crane call() throws Exception {
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

        return (Crane) cranes;
    }

    public int collectFines(List<Crane> cranes) {
        int fine = 0;

        for (Crane crane : cranes) {
            fine += crane.getCraneFine();
        }

        return fine;
    }
}
