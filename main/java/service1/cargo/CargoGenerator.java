package service1.cargo;

import java.util.Random;

public class CargoGenerator {
    Random rand = new Random();

    public Cargo generateCargo() {
        CargoType type = generateCargoType();
        int weightOrQuantity = generateWeightOrQuantity();

        return new Cargo(type, weightOrQuantity);
    }

    public CargoType generateCargoType() {
        CargoType[] types = CargoType.values();

        return types[rand.nextInt(types.length)];
    }

    public int generateWeightOrQuantity() {
        return rand.nextInt(100); // Random number, idk why, needs to clarify
    }
}
