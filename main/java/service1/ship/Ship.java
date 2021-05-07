package service1.ship;

import service1.cargo.Cargo;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Ship {
    private String name;
    private Cargo cargo;
    private int arrivalTime;
    private int unloadingTime;
    private int workingCranesPerformance;
}




