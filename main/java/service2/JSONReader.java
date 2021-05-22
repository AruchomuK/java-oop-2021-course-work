package service2;

import com.fasterxml.jackson.databind.ObjectMapper;

import service1.cargo.Cargo;
import service1.ship.Ship;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSONReader {
    private String filename;

    List<Ship> ships;

    public List<Ship> readSchedule(String filename) {
        ships = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();

        try {
            ships = Arrays.asList(mapper.readValue(Paths.get(filename).toFile(), Ship[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ships;
    }
}
