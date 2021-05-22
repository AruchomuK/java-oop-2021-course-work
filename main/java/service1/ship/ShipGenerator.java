package service1.ship;

import lombok.NoArgsConstructor;
import service1.cargo.Cargo;
import service1.cargo.CargoGenerator;
import java.util.Random;

@NoArgsConstructor
public class ShipGenerator {
    public final int MAX_MODELING_MINUTES = 43200;
    private final int ALPHABET_LENGTH = 26;

    private int cranePerformance;

    Random rand = new Random();

    public Ship generateShip() {
        CargoGenerator cargoGenerator = new CargoGenerator();

        String name = generateName();
        Cargo cargo = cargoGenerator.generateCargo();
        int arrivalTime = generateArrivalTime();

        switch (cargo.getCargoType()) {
            case LOOSE:
                cranePerformance = 3;
                break;
            case LIQUID:
                cranePerformance = 2;
                break;
            case CONTAINER:
                cranePerformance = 1;
                break;
        }

        int unloadingTime = calculateUnloadingTime(cargo, cranePerformance);

        return new Ship(name, cargo, arrivalTime, unloadingTime, cranePerformance);
    }

    public String generateName() {
        String name = "";
        char symbol = '\0';
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            symbol = (char)(rand.nextInt(ALPHABET_LENGTH) + 'a');
            stringBuilder.append(symbol);
        }

        name = stringBuilder.toString();

        return name;
    }

    public int generateArrivalTime() {
        return rand.nextInt(MAX_MODELING_MINUTES);
    }

    public int calculateUnloadingTime(Cargo cargo, int cranePerformance) {
        return cargo.getWeightOrQuantity() / cranePerformance;
    }
}
