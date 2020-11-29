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

/**
 * Allow to manage CarDataBase
 *
 */
public class CarDataBase extends UnicastRemoteObject implements ICarDataBase{

	private Map<Long, ICar> carMap = new HashMap<>(); //Long for id

    private long idMap = 0;
    private String jsonFileName;

    /**
     * CarDataBase constructor
     * @param jsonFileName, the jsonFileName which represent database
     * @throws RemoteException
     */
    public CarDataBase(String jsonFileName) throws RemoteException {
        super();

        this.jsonFileName = jsonFileName;
    }

    /*
     * Get all cars in database
     * @return Map<Long, ICar>, the map with the car ID as key, and the ICar object as value
     * @throws RemoteException
     */
    public Map<Long, ICar> getAllCars() throws RemoteException {
        return Collections.unmodifiableMap(carMap);
    }

    /*
     * Get all cars in database
     * @param id, the id of the car
     * @return ICar, the ICar from the given id
     * @throws RemoteException
     */
    public ICar getCar(Long id) throws RemoteException {
        return carMap.get(id);
    }
    
    /*
     * Get the JSON representation of a car
     * @param id, the id of the car
     * @return String, the JSON representation of the car
     * @throws RemoteException
     */
    public String getCarJson(long id) throws RemoteException {
        return carMap.get(id).toJson(id);
    }

    /*
     * Remove the car from the database
     * @param id, the id of the car
     * @return boolean, true if the car can be removed, false either
     * @throws RemoteException
     */
    public boolean removeCar(Long id) throws RemoteException {
        return null != carMap.remove(id);
    }

    /*
     * Add a car to the database
     * @param t, the car to add
     * @return boolean, true if the car can be added, false either
     * @throws RemoteException
     */
    public boolean addCar(ICar t) throws RemoteException {
        idMap++;
        carMap.put(idMap, t);
        return true;
    }

    /*
     * Get all cars which can be buy
     * @return Map<Long, ICar>, map with id of a car as key and his ICar object as value
     * @throws RemoteException
     */
    public Map<Long, ICar> getBuyableCar() throws RemoteException { //not rented and already rented once
        var map = new HashMap<Long, ICar>();

        for (Map.Entry<Long, ICar> entry : carMap.entrySet()) {
            if (entry.getValue().isSellable()){
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;

    }
    
    /*
     * Get all cars which can be buy by a JSON representation
     * @return String, the JSON representation of all buyables cars
     * @throws RemoteException
     */
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


    @Override
    public String toString() {
        return "CarDataBase{" +
                "carMap=" + carMap +
                '}';
    }

    /*
     * Get the JSON representation of the database
     * @return String, the JSON representation of the database
     * @throws RemoteException
     */
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

    /*
     * Initialize the database
     * @throws RemoteException
     */
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
    
    /*
     * Get the price of a car
     * @param id, the id of the car
     * @return float, the price of the car
     * @throws RemoteException
     */
    public float getPriceOfCar(long id) throws RemoteException{
    	return carMap.get(id).getSellPrice();
    }

    /*
     * Rent a car
     * @param carId, the id of the car
     * @param employeeId, the id of the employee which want to rent
     * @return boolean, true if this car can be rent by this employee, false either
     * @throws RemoteException
     */
    @Override
    public boolean rent(Long carId, long employeeId) throws RemoteException  {
        return carMap.get(carId).rent(employeeId);
    }

    /*
     * Unrent a car
     * @param id, the id of the car
     * @return long, the id of the new employee which rent, -1 of there is no one
     * @throws RemoteException
     */
    @Override
    public long unrent(Long id) throws RemoteException  {
        return carMap.get(id).unrent();
    }

}
