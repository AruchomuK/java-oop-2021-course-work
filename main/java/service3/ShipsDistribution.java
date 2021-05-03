package service3;

import lombok.Data;
import service1.ship.Ship;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShipsDistribution {
    private List<Ship> looseCargos;
    private List<Ship> liquidCargos;
    private List<Ship> containerCargos;

    public ShipsDistribution(List<Ship> schedule) {
        looseCargos = new ArrayList<>();
        liquidCargos = new ArrayList<>();
        containerCargos = new ArrayList<>();

        for (Ship ship : schedule) {
            distributeShip(ship);
        }
    }

    public void distributeShip(Ship ship) {
        switch (ship.getCargo().getCargoType()) {
            case LOOSE:
                looseCargos.add(ship);
                break;
            case LIQUID:
                liquidCargos.add(ship);
                break;
            case CONTAINER:
                containerCargos.add(ship);
                break;
        }
    }
}
