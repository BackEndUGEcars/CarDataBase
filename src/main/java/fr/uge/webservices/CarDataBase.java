package fr.uge.webservices;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class CarDataBase extends UnicastRemoteObject implements ICarDataBase{

	private Map<Long, ICar> carMap = new HashMap<>(); //Long for id

    private long idMap = 0;
    private String jsonFileName;

    public CarDataBase(String jsonFileName) throws RemoteException {
        super();

        this.jsonFileName = jsonFileName;
    }

    public Map<Long, ICar> getAllCars() throws RemoteException {
        return Collections.unmodifiableMap(carMap);
    }

    public ICar getCar(Long id) throws RemoteException {
        return carMap.get(id);
    }
    
    public String getCarJson(long id) throws RemoteException {
        return carMap.get(id).toJson(id);
    }

    public String getCarJson(long id) throws RemoteException {
        return carMap.get(id).toJson(id);
    }

    public boolean removeCar(Long id) throws RemoteException {
        return null != carMap.remove(id);
    }

    public boolean addCar(ICar t) throws RemoteException {
        idMap++;
        carMap.put(idMap, t);
        return true;
    }

    public Map<Long, ICar> getBuyableCar() throws RemoteException { //not rented and already rented once
        var map = new HashMap<Long, ICar>();

        for (Map.Entry<Long, ICar> entry : carMap.entrySet()) {
            if (entry.getValue().isSellable()){
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;

    }
    
    public String getBuyableCarsJson() throws RemoteException { //not rented and already rented once
        var sj = new StringJoiner(", ");
        for (Map.Entry<Long, ICar> entry : carMap.entrySet()) {
            if (entry.getValue().isSellable()){
            	sj.add(entry.getValue().toJson(entry.getKey()));
            }
        }
        return "{" +
        "    \"cars\": [" +
         sj.toString() +
        "]}";
    }

    public String getBuyableCarsJson() throws RemoteException { //not rented and already rented once
        var sj = new StringJoiner(", ");
        for (Map.Entry<Long, ICar> entry : carMap.entrySet()) {
            if (entry.getValue().isSellable()){
                sj.add(entry.getValue().toJson(entry.getKey()));
            }
        }
        return "{" +
                "    'cars': [" +
                sj.toString() +
                "]}";
    }

    @Override
    public String toString() {
        return "CarDataBase{" +
                "carMap=" + carMap +
                '}';
    }

    public String toJson() throws RemoteException {
        var sj = new StringJoiner(", ");
        for (Map.Entry<Long, ICar> entry : carMap.entrySet()) {
            sj.add(entry.getValue().toJson(entry.getKey()));
        }
        return "{" +
                "    \"cars\": [" +
                 sj.toString() +
                "],    \"idMap\" : "+ idMap +"}";

    }



    public void init() throws IOException, ParseException, RemoteException {
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
        idMap = (long) jsonObject.get("idMap");
    }
    
    public float getPriceOfCar(long id) throws RemoteException{
    	return carMap.get(id).getSellPrice();
    }


    
    @Override
    public boolean rent(Long carId, long employeeId) throws RemoteException  {
        return carMap.get(carId).rent(employeeId);
    }

    @Override
    public long unrent(Long id) throws RemoteException  {
        return carMap.get(id).unrent();
    }

}
