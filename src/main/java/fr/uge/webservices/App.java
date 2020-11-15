package fr.uge.webservices;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class App {
    public static void main(String[] args) throws IOException, ParseException {
    	try {
			LocateRegistry.createRegistry(1099);
			ICarDataBase c = new CarDataBase("test.json");
			Naming.bind("rmi://localhost:1099/CarDataBase", c);
		} catch (Exception e) {
			System.err.println("Problem: " + e);
		}
    	/*
        var carBase = new CarDataBase("test.json");
        carBase.init();*/
//        for (var i = 0 ; i < 5 ; ++i){
//            carBase.addCar(new Car(i, i * 5));
//        }
//
//        var car1 = carBase.getCar(1L);
//        var car2 = carBase.getCar(2L);
//
//        car1.addNoteCar(5);
//        car1.addNoteCar(0);
//
//        car2.addNoteCar(2);
//        car2.addNoteCar(2);
//        car2.addNoteCar(2);
//
//
//        System.out.println(carBase);
//        carBase.test();

/*
        System.out.println(" toJson : ");
        System.out.println(carBase.toJson());*/
    }
}
