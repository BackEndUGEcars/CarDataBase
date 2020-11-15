package fr.uge.webservices;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICar extends Remote {
    float addNoteCleanliness(float note) throws RemoteException;
    float addNoteCar(float note) throws RemoteException ;

    float getNoteCar() throws RemoteException ;
    float getNoteCarCleanliness() throws RemoteException ;


    /**
     * call to rent the Car
     * @return true if car successfully rented false otherwise
     */
    boolean rent(long id) throws RemoteException ;
    boolean unrent(long id) throws RemoteException ;

    float getRentPrice() throws RemoteException ;
    float getSellPrice() throws RemoteException ;

    boolean isSellable() throws RemoteException ;
    long isRented() throws RemoteException;

    String getModel() throws RemoteException ;
    String getImagePath() throws RemoteException ;

    String toJson(Long id) throws RemoteException ;

}
