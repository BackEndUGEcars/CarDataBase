package fr.uge.webservices;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CarDataBase implements ICarDataBase{
    private Map<Long, ICar> carMap = new HashMap<>(); //Long for id
    private long idMap = 0;
    private String jsonFileName;

    public CarDataBase(String jsonFileName) {
        this.jsonFileName = jsonFileName;
    }

    public Map<Long, ICar> getAllCars() {
        return Collections.unmodifiableMap(carMap);
    }

    public ICar getCar(Long id){
        return carMap.get(id);
    }

    public boolean removeCar(Long id){
         return null != carMap.remove(id);
    }

    public boolean addCar(ICar t){
        idMap++;
        carMap.put(idMap, t);
        return true;
    }

    public Map<Long, ICar> getBuyableCar(){ //not rented and already rented once
        var map = new HashMap<Long, ICar>();
        carMap.forEach((id, c) -> {
            if (c.isSellable()){
                map.put(id, c);
            }
        });
        return map;

    }

    @Override
    public String toString() {
        return "CarDataBase{" +
                "carMap=" + carMap +
                '}';
    }

    public String toJson(){
        var sj = new StringJoiner(", ");
        carMap.forEach((id, c) -> {
            sj.add(c.toJson(id));
        });
        return "{" +
                "    'cars': [" +
                 sj.toString() +
                "],    'idMap' : "+ idMap +"}";
    }



    public void init() throws IOException, ParseException{
        var json = IOUtils.toString(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("test.json")), StandardCharsets.UTF_8);
        JSONParser parser = new JSONParser();


        var jsonObject = (JSONObject) parser.parse(json);
        var cars = (JSONArray) jsonObject.get("cars");
        var iterator = cars.iterator();
        while (iterator.hasNext()) {
            var s = iterator.next().toString();
            var jo = (JSONObject) parser.parse(s);
            var c = Car.createCar(s);
            carMap.put((Long) jo.get("id"), c);
        }
    }
}
