package fr.uge.webservices;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

public class App {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);

            ICarDataBase c = new CarDataBase("test.json");
            c.init();
            Naming.bind("rmi://localhost:1099/CarDataBase", (Remote) c);
            System.out.println("RMI ok");
        } catch (Exception e) {
            System.err.println("Problem: " + e);
        }

    }
}
