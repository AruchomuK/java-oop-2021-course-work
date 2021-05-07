package service2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import service1.cargo.Cargo;
import service1.cargo.CargoType;
import service1.ship.Ship;

import java.io.IOException;

public class JSONDeserializer extends StdDeserializer<Ship> {

    public JSONDeserializer() {
        this(null);
    }

    protected JSONDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Ship deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String name = node.get("name").asText();

        String cargoTypeAsString = node.get("cargo").get("cargoType").asText();
        int weightOrQuantity = node.get("cargo").get("weightOrQuantity").asInt();

        CargoType cargoType;
        switch(cargoTypeAsString) {
            case "LOOSE":
                cargoType = CargoType.LOOSE;
                break;
            case "LIQUID":
                cargoType = CargoType.LIQUID;
                break;
            case "CONTAINER":
                cargoType = CargoType.CONTAINER;
                break;
            default:
                throw new IOException();
        }

        int arrivalTime = node.get("arrivalTime").asInt();
        int unloadingTime = node.get("unloadingTime").asInt();
        int workingCranesPerformance = node.get("workingCranesPerformance").asInt();

        Cargo cargo = new Cargo(cargoType, weightOrQuantity);
        
        return new Ship(name, cargo, arrivalTime, unloadingTime, workingCranesPerformance);
    }
}
