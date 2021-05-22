package service1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service1.ship.Ship;
import service2.JSONReader;

import java.util.List;

@Controller
public class ScheduleController {

    @GetMapping("/createSchedule")
    @ResponseBody
    public String createSchedule() throws JsonProcessingException {
        Schedule schedule = new Schedule();
        schedule.generateSchedule();

        List<Ship> shipListFromSchedule = schedule.getSchedule();

        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(shipListFromSchedule);
    }


}
