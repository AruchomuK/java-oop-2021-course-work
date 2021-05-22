package service3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import service1.cargo.CargoType;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class Statistics {

    @Getter
    private List<StatisticForOneUnloading> statistics;

    public List<StatisticForOneUnloading> putStatisticsInList(List<ShipsUnloading> unloadings) {
        statistics = new ArrayList<>();

        for (ShipsUnloading unloading : unloadings) {
            int size = unloading.getShips().size();
            int cranesQuantity = unloading.getCranesQuantity();
            int fine = unloading.getFine();

            CargoType cargoType = unloading.getShips().get(0).getCargo().getCargoType();
            String unloadingType;

            switch (cargoType) {
                case LOOSE:
                    unloadingType = "LOOSE";
                    break;
                case LIQUID:
                    unloadingType = "LIQUID";
                    break;
                case CONTAINER:
                    unloadingType = "CONTAINER";
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + cargoType);
            }

            StatisticForOneUnloading stat = new StatisticForOneUnloading(unloadingType, size, cranesQuantity, fine);
            statistics.add(stat);
        }

        return statistics;
    }

    public void printInfo(List<ShipsUnloading> unloadings) {
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
