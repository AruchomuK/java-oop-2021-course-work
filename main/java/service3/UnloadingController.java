package service3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import service1.ship.Ship;
import service2.JSONReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Controller
public class UnloadingController {

    @PostMapping("/completeUnloading")
    @ResponseBody
    public void completeUnloading() {
        RestTemplate restTemplate = new RestTemplate();

        String address = "http://localhost:8082/getScheduleInJSON";
        File receivedFile = restTemplate.getForEntity(address, File.class).getBody();

        JSONReader jsonReader = new JSONReader();
        List<Ship> shipListFromSchedule = jsonReader.readSchedule(receivedFile.getName());

        ShipsDistribution shipsDistribution = new ShipsDistribution(shipListFromSchedule);

        ShipsUnloading looseShipsUnloading = new ShipsUnloading(shipsDistribution.getLooseCargos());
        ShipsUnloading liquidShipsUnloading = new ShipsUnloading(shipsDistribution.getLiquidCargos());
        ShipsUnloading containerShipsUnloading = new ShipsUnloading(shipsDistribution.getContainerCargos());

        List<ShipsUnloading> unloadings = new ArrayList<>(3);

        unloadings.add(looseShipsUnloading);
        unloadings.add(liquidShipsUnloading);
        unloadings.add(containerShipsUnloading);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            List<Future<Object>> stat = executor.invokeAll(unloadings);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();

        Statistics statistics = new Statistics();
        statistics.putStatisticsInList(unloadings);

        String postAddress = "http://localhost:8082/getStatistics";
        List<StatisticForOneUnloading> statisticsList = statistics.putStatisticsInList(unloadings);

        ObjectMapper mapper = new ObjectMapper();
        String statisticsListAsString = "\0";

        try {
            statisticsListAsString = mapper.writeValueAsString(statisticsList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        restTemplate.postForEntity(postAddress, statisticsListAsString, String.class);
    }
}
