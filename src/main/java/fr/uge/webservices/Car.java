package fr.uge.webservices;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Car implements ICar{
    private Boolean isRented = false;
    private int nbRent = 0;

    private float noteCar = 0 ;
    private int nbNoteCar = 0;

    private float noteCarCleanliness = 0;
    private int nbNoteCarCleanliness = 0;

    private float rentPrice; //en €
    private float sellPrice; //en €

    private String model; //TODO
    private String imagePath; //TODO





    private Car() {
    }

    public Car(float rentPrice, float sellPrice, String model, String imagePath) {
        this.rentPrice = rentPrice;
        this.sellPrice = sellPrice;
        this.model = model;
        this.imagePath = imagePath;
    }

    public Car(float rentPrice, float sellPrice) {
        this.rentPrice = rentPrice;
        this.sellPrice = sellPrice;
    }

    public float addNoteCleanliness(float note) {
        noteCarCleanliness = (noteCarCleanliness * nbNoteCarCleanliness + note) / (nbNoteCarCleanliness + 1 );
        nbNoteCarCleanliness++;
        return noteCarCleanliness;
    }

    public float addNoteCar(float note) {
        noteCar = (noteCar * nbNoteCar + note) / (nbNoteCar + 1 );
        nbNoteCar++;
        return noteCar;
    }

    public float getNoteCar() {
        return noteCar;
    }

    public float getNoteCarCleanliness() {
        return noteCarCleanliness;
    }

    public boolean rent() {
        if (isRented){
            return false;
        }
        nbRent++;
        isRented = true;
        return true;

    }

    public boolean unrent(){
        if (isRented){
            isRented = false;
            return true;
        }
        return false;
    }

    public float getRentPrice() {
        return rentPrice;
    }

    public float getSellPrice() {
        return sellPrice;
    }

    public boolean isSellable(){
        return nbRent > 0;
    }

    public String getModel() {
        return model;
    }

    public String getImagePath() {
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

    public String toJson(Long id){
        return "{" +
                "'id':" + id +
                ", 'isRented':" + isRented +
                ", 'nbRent':" + nbRent +
                ", 'noteCar':" + noteCar +
                ", 'nbNoteCar':" + nbNoteCar +
                ", 'noteCarCleanliness':" + noteCarCleanliness +
                ", 'nbNoteCarCleanliness':" + nbNoteCarCleanliness +
                ", 'rentPrice':" + rentPrice +
                ", 'sellPrice':" + sellPrice +
                ", 'model':'" + model + '\'' +
                ", 'imagePath':" + imagePath +
                '}';
    }

    public static Car createCar(String json) throws ParseException {
        JSONParser parser = new JSONParser();
        var jsonObject = (JSONObject) parser.parse(json);
        var car = new Car();
        car.isRented = (boolean) jsonObject.get("isRented");
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




}
