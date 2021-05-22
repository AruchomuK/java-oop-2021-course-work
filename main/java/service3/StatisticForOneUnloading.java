package service3;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticForOneUnloading {
    private String unloadingType;
    private int size;
    private int cranesQuantity;
    private int fine;
}
