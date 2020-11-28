package fr.uge.webservices;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Queue;


public class Car implements ICar, Serializable{
    private long isRented = -1;

    private int nbRent = 0;

    private float noteCar = 0 ;
    private int nbNoteCar = 0;

    private float noteCarCleanliness = 0;
    private int nbNoteCarCleanliness = 0;


    private float rentPrice; 
    private float sellPrice; 


    private String model; //TODO
    private String imagePath; //TODO
    
    private final Queue<Long> rentQueue = new LinkedList<Long>();

    private Car() throws RemoteException {
    }

    public Car(float rentPrice, float sellPrice, String model, String imagePath) throws RemoteException {
        this.rentPrice = rentPrice;
        this.sellPrice = sellPrice;
        this.model = model;
        this.imagePath = imagePath;
    }

    public Car(float rentPrice, float sellPrice) throws RemoteException {
        this.rentPrice = rentPrice;
        this.sellPrice = sellPrice;
    }

    public float addNoteCleanliness(float note) throws RemoteException {
        noteCarCleanliness = (noteCarCleanliness * nbNoteCarCleanliness + note) / (nbNoteCarCleanliness + 1 );
        nbNoteCarCleanliness++;
        return noteCarCleanliness;
    }

    public float addNoteCar(float note)  throws RemoteException {
        noteCar = (noteCar * nbNoteCar + note) / (nbNoteCar + 1 );
        nbNoteCar++;
        return noteCar;
    }

    public float getNoteCar() throws RemoteException  {
        return noteCar;
    }

    public float getNoteCarCleanliness() throws RemoteException  {
        return noteCarCleanliness;
    }

    public boolean rent(long id) throws RemoteException  {
        if (isRented != -1){

        	addEmployeeQueue(id);

            return false;
        }
        nbRent++;
        isRented = id;
        return true;

    }


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

    public float getRentPrice() throws RemoteException  {
        return rentPrice;
    }

    public float getSellPrice() throws RemoteException  {
        return sellPrice;
    }

    public boolean isSellable() throws RemoteException {
        return nbRent > 0;
    }

    public String getModel()  throws RemoteException {
        return model;
    }

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
    
    public long isRented() throws RemoteException {
    	return isRented;
    }

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
    
    public boolean addEmployeeQueue(Long idEmployee) throws RemoteException {
    	return rentQueue.offer(idEmployee);
    }
    
    public long removeEmployeeQueue() throws RemoteException{
    	var res = rentQueue.poll();
    	return res == null ? -1 : res;    
    }

	public Queue<Long> getRentQueue() throws RemoteException{
		return rentQueue;
	}




}
