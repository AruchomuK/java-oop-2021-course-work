package service2;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import service1.cargo.Cargo;
import service1.cargo.CargoType;
import service1.ship.Ship;
import service3.Statistics;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@NoArgsConstructor
@AllArgsConstructor
public class JSONWriter {
    List<Ship> ships;

    public void writeStatistics(Statistics statistics) {
        final String filename = "statistics.json";

        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(Paths.get(filename).toFile(), statistics.getStatistics());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeSchedule() {
        final String filename = "schedule.json";

        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(Paths.get(filename).toFile(), ships);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addManually(List<Ship> schedule) {
        Scanner in = new Scanner(System.in);

        String userAnswer = "y";

        while (!userAnswer.equals("n")) {
            System.out.println("Do you want to add one more ship? y/n: ");

            userAnswer = in.next();

            if (userAnswer.equals("y") || userAnswer.equals("yes")) {
                System.out.println("Enter the ship's name: ");
                String name = in.next();

                System.out.println("Enter the cargo' type (0 for LOOSE, 1 for LIQUID, 2 for CONTAINER): ");
                int cargoTypeAsInt = in.nextInt();

                System.out.println("Enter the cargo's weight or quantity: ");
                int weightOrQuantity = in.nextInt();

                System.out.println("Enter the ship's arrival time: ");
                int arrivalTime = in.nextInt();

                CargoType cargoType;
                switch(cargoTypeAsInt) {
                    case 0:
                        cargoType = CargoType.LOOSE;
                        break;
                    case 1:
                        cargoType = CargoType.LIQUID;
                        break;
                    case 2:
                        cargoType = CargoType.CONTAINER;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + cargoTypeAsInt);
                }

                int workingCranesPerformance = 0;
                switch(cargoTypeAsInt) {
                    case 0:
                        workingCranesPerformance = 1;
                        break;
                    case 1:
                        workingCranesPerformance = 2;
                        break;
                    case 2:
                        workingCranesPerformance = 3;
                        break;
                }

                int unloadingTime = weightOrQuantity / workingCranesPerformance;

                Cargo cargo = new Cargo(cargoType, weightOrQuantity);

                Ship ship = new Ship(name, cargo, arrivalTime, unloadingTime, workingCranesPerformance);

                schedule.sort(Comparator.comparingInt(Ship::getArrivalTime));
            }
        }

    }
}
