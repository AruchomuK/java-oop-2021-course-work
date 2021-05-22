package service2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import service1.ship.Ship;
import service3.ShipsUnloading;
import service3.Statistics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Controller
public class JSONStuffController {
    File jsonFile;

    @GetMapping("/getScheduleInJSON")
    @ResponseBody
    public File getScheduleInJSON() {
        RestTemplate restTemplate = new RestTemplate();

        String address = "http://localhost:8081/createSchedule";
        String received = restTemplate.getForEntity(address, String.class).getBody();

        jsonFile = new File("schedule.json");

        try {
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(received);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonFile;
    }

    @GetMapping("/getScheduleFromFile")
    @ResponseBody
    public List<Ship> getScheduleFromFile() {
        JSONReader jsonReader = new JSONReader();
        List<Ship> ships = jsonReader.readSchedule("schedule.json");

        return ships;
    }

    @PostMapping("/getStatistics")
    @ResponseBody
    public void getStatistics(@RequestBody String statisticsAsString) {
        File jsonStatisticsFile = new File("statistics.json");

        try {
            FileWriter fileWriter = new FileWriter(jsonStatisticsFile);
            fileWriter.write(statisticsAsString);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
