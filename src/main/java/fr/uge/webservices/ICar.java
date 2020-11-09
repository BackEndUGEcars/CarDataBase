package fr.uge.webservices;

import java.rmi.RemoteException;

public interface ICar {
    float addNoteCleanliness(float note) throws RemoteException;
    float addNoteCar(float note) throws RemoteException ;

    float getNoteCar() throws RemoteException ;
    float getNoteCarCleanliness() throws RemoteException ;


    /**
     * call to rent the Car
     * @return true if car successfully rented false otherwise
     */
    boolean rent() throws RemoteException ;
    boolean unrent() throws RemoteException ;

    float getRentPrice() throws RemoteException ;
    float getSellPrice() throws RemoteException ;

    boolean isSellable() throws RemoteException ;

    String getModel() throws RemoteException ;
    String getImagePath() throws RemoteException ;

    String toJson(Long id) throws RemoteException ;

}
