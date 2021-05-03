package service1.cargo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Cargo {
    private CargoType cargoType;
    private int weightOrQuantity;
}

