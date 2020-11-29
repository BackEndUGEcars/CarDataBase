package fr.uge.webservices;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Allow to manage Car
 *
 */
public class Car extends UnicastRemoteObject implements ICar{
	private long isRented = -1;

    private int nbRent = 0;

    private float noteCar = 0 ;
    private int nbNoteCar = 0;

    private float noteCarCleanliness = 0;
    private int nbNoteCarCleanliness = 0;


    private float rentPrice; //en €
    private float sellPrice; //en €


    private String model; //TODO
    private String imagePath; //TODO
    
    private final Queue<Long> rentQueue = new LinkedList<Long>();

    private Car() throws RemoteException {
    }

    /**
     * Car constructor
     * @param rentPrice, the rent price
     * @param sellPrice, the sell price
     * @param model, the model
     * @param imagePath, the image path
     * @throws RemoteException
     */
    public Car(float rentPrice, float sellPrice, String model, String imagePath) throws RemoteException {
        this.rentPrice = rentPrice;
        this.sellPrice = sellPrice;
        this.model = model;
        this.imagePath = imagePath;
    }

    /**
     * Car constructor
     * @param rentPrice, the rent price
     * @param sellPrice, the sell price
     * @throws RemoteException
     */
    public Car(float rentPrice, float sellPrice) throws RemoteException {
        this.rentPrice = rentPrice;
        this.sellPrice = sellPrice;
    }

    /*
     * Add a cleanliness note to the car
     * @param note, the cleanliness note
     * @throws RemoteException
     */
    public float addNoteCleanliness(float note) throws RemoteException {
        noteCarCleanliness = (noteCarCleanliness * nbNoteCarCleanliness + note) / (nbNoteCarCleanliness + 1 );
        nbNoteCarCleanliness++;
        return noteCarCleanliness;
    }

    /*
     * Add a note to the car
     * @param note, the note
     * @throws RemoteException
     */
    public float addNoteCar(float note)  throws RemoteException {
        noteCar = (noteCar * nbNoteCar + note) / (nbNoteCar + 1 );
        nbNoteCar++;
        return noteCar;
    }

    /*
     * Get the car note
     * @throws RemoteException
     */
    public float getNoteCar() throws RemoteException  {
        return noteCar;
    }

    /*
     * Get the car cleanliness note
     * @throws RemoteException
     */
    public float getNoteCarCleanliness() throws RemoteException  {
        return noteCarCleanliness;
    }

    /*
     * Rent the car
     * @param id, the location id
     * @return boolean, true if the car can be rented, false either
     * @throws RemoteException
     */
    public boolean rent(long id) throws RemoteException  {
        if (isRented != -1){

        	addEmployeeQueue(id);

            return false;
        }
        nbRent++;
        isRented = id;
        return true;

    }

    /*
     * Unrent the car
     * @return long, -1 if there is no other location, either, the id of the new location
     * @throws RemoteException
     */
    public long unrent() throws RemoteException {
        if (isRented == -1){
            return -1L;
        }
        isRented = -1L;
        var newRent = removeEmployeeQueue();
        if (newRent != -1) {
        	rent(newRent);

        }
        return isRented;
    }
    
    /*
     * Get the rent price
     * @return float, the rent price
     * @throws RemoteException
     */
    public float getRentPrice() throws RemoteException  {
        return rentPrice;
    }

    /*
     * Get the sell price
     * @return float, the sell price
     * @throws RemoteException
     */
    public float getSellPrice() throws RemoteException  {
        return sellPrice;
    }

    /*
     * Check if the car is sellable
     * @return boolean, true if the car is sellable, false either
     * @throws RemoteException
     */
    public boolean isSellable() throws RemoteException {
        return nbRent > 0;
    }

    /*
     * Get the car model
     * @return String, the car model
     * @throws RemoteException
     */
    public String getModel()  throws RemoteException {
        return model;
    }

    /*
     * Get the image path
     * @return String, the image path
     * @throws RemoteException
     */
    public String getImagePath()  throws RemoteException {
        return imagePath;
    }

    @Override
    public String toString() {
        return "Car{" +
                "isRented=" + isRented +
                ", nbRent=" + nbRent +
                ", noteCar=" + noteCar +
                ", nbNoteCar=" + nbNoteCar +
                ", noteCarCleanliness=" + noteCarCleanliness +
                ", nbNoteCarCleanliness=" + nbNoteCarCleanliness +
                ", rentPrice=" + rentPrice +
                ", sellPrice=" + sellPrice +
                ", model='" + model + '\'' +
                ", imagePath=" + imagePath +
                '}';
    }

    /*
     * Get the JSON representation of the car
     * @param id, the car id
     * @return String, the car JSON representation
     * @throws RemoteException
     */
    public String toJson(Long id) throws RemoteException {

   	 	return "{" +
                "\"id\":" + id +
                ", \"isRented\":" + isRented +
                ", \"nbRent\":" + nbRent +
                ", \"noteCar\":" + noteCar +
                ", \"nbNoteCar\":" + nbNoteCar +
                ", \"noteCarCleanliness\":" + noteCarCleanliness +
                ", \"nbNoteCarCleanliness\":" + nbNoteCarCleanliness +
                ", \"rentPrice\":" + rentPrice +
                ", \"sellPrice\":" + sellPrice +
                ", \"model\":\"" + model + "\"" +
                ", \"imagePath\":\"" + imagePath + "\""  +
                "}";
   }
    
    /*
     * Check if the car is rented
     * @return long, the id of the actual location
     * @throws RemoteException
     */
    public long isRented() throws RemoteException {
    	return isRented;
    }

    /*
     * Create a car from a JSON
     * @param json, the car JSON representation
     * @return Car, the new Car 
     * @throws RemoteException
     */
    public static Car createCar(String json) throws ParseException, RemoteException {

        JSONParser parser = new JSONParser();
        var jsonObject = (JSONObject) parser.parse(json);
        var car = new Car();
        car.isRented = (long) jsonObject.get("isRented");
        car.nbRent = ((Long) jsonObject.get("nbRent")).intValue();
        car.noteCar = ((Double) jsonObject.get("noteCar")).floatValue();
        car.nbNoteCar = ((Long) jsonObject.get("nbNoteCar")).intValue();
        car.noteCarCleanliness = ((Double) jsonObject.get("noteCarCleanliness")).floatValue();
        car.nbNoteCarCleanliness = ((Long) jsonObject.get("nbNoteCarCleanliness")).intValue();
        car.rentPrice = ((Double) jsonObject.get("rentPrice")).floatValue();
        car.sellPrice = ((Double) jsonObject.get("sellPrice")).floatValue();
        car.model = ((String) jsonObject.get("model")).equals("null") ? null : (String) jsonObject.get("model") ;
        car.imagePath = ((String) jsonObject.get("imagePath")).equals("null") ? null : (String) jsonObject.get("imagePath");

        return car;
    }
    
    /*
     * Add an employee to the location queue
     * @param idEmployee, the id of the employee
     * @return boolean, true if the employee can be added, false either
     * @throws RemoteException
     */
    public boolean addEmployeeQueue(Long idEmployee) throws RemoteException {
    	return rentQueue.offer(idEmployee);
    }
    
    /*
     * Remove an employee from the location queue
     * @return long, true if the employee can be added, false either
     * @throws RemoteException
     */
    public long removeEmployeeQueue() throws RemoteException{
    	var res = rentQueue.poll();
    	return res == null ? -1 : res;    
    }

    /*
     * Get the rent queue
     * @return Queue<Long>, the rent queue
     * @throws RemoteException
     */
	public Queue<Long> getRentQueue() throws RemoteException{
		return rentQueue;
	}




}
