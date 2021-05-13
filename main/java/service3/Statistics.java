package service3;

import lombok.AllArgsConstructor;
import service1.cargo.CargoType;

import java.util.List;

@AllArgsConstructor
public class Statistics {
    private List<ShipsUnloading> unloadings;

    public void printInfo() {
        for (ShipsUnloading unloading : unloadings) {
            int size = unloading.getShips().size();
            int cranesQuantity = unloading.getCranesQuantity();
            int fine = unloading.getFine();

            CargoType cargoType = unloading.getShips().get(0).getCargo().getCargoType();

            switch (cargoType) {
                case LOOSE:
                    System.out.println("LOOSE statistics:\n");
                    break;
                case LIQUID:
                    System.out.println("LIQUID statistics:\n");
                    break;
                case CONTAINER:
                    System.out.println("CONTAINER statistics:\n");
                    break;
            }

            System.out.println("Ships' queue size: " + size + "\n" +
                    "Cranes' quantity: " + cranesQuantity + "\n" +
                    "Total fine: " + fine + "\n");
        }
    }
}
