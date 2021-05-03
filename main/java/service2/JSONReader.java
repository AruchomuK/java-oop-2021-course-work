package service2;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import service1.cargo.Cargo;
import service1.cargo.CargoType;
import service1.ship.Ship;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONReader {
    private final String filename = "schedule.json";

    public List<Ship> readSchedule() {
        List<Ship> ships = new ArrayList<>();

        try (FileReader reader = new FileReader(filename)){
            JSONParser jsonParser = new JSONParser();

            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray jsonShipsArray = (JSONArray) jsonObject.get("schedule");

            for (int i = 0; i < jsonShipsArray.size(); i++) {
                JSONObject jsonShip = new JSONObject();
                String name = (String) jsonShip.get("Name");
                CargoType cargoType = (CargoType) jsonShip.get("Cargo type");
                int cargoWeightOrQuantity = (int) jsonShip.get("Cargo quantity");
                int arrivalTime = (int) jsonShip.get("Cargo quantity");
                int unloadingTime = (int) jsonShip.get("Cargo quantity");
                int cranePerformance = (int) jsonShip.get("Cargo quantity");

                Cargo cargo = new Cargo(cargoType, cargoWeightOrQuantity);
                ships.add(new Ship(name, cargo, arrivalTime, unloadingTime, cranePerformance));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ships;
    }
}
