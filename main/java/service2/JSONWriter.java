package service2;

import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import service1.ship.Ship;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@AllArgsConstructor
public class JSONWriter {
    List<Ship> ships;

    public void writeSchedule() {
        JSONArray jsonShipsArray = new JSONArray();

        for (Ship ship : ships) {
            JSONObject jsonShip = new JSONObject();
            jsonShip.put("Name", ship.getName());
            jsonShip.put("Cargo type", ship.getCargo().getCargoType());
            jsonShip.put("Cargo quantity", ship.getCargo().getWeightOrQuantity());
            jsonShip.put("Arrival time", ship.getArrivalTime());
            jsonShip.put("Unloading time", ship.getUnloadingTime());
            jsonShip.put("Working crane performance", ship.getWorkingCranesPerformance());

            jsonShipsArray.add(jsonShip);
        }

        JSONObject jsonSchedule = new JSONObject();
        jsonSchedule.put("Schedule", jsonShipsArray);

        try (FileWriter file = new FileWriter("schedule.json")) {
            file.write(jsonSchedule.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
