package fr.uge.webservices;

import java.util.Map;

public interface ICarDataBase {
    ICar getCar(Long id);
    boolean removeCar(Long id);
    boolean addCar(ICar t);
    Map<Long, ICar> getBuyableCar();
    String toJson();
    Map<Long, ICar> getAllCars();

}
