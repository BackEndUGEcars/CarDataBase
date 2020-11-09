package fr.uge.webservices;

public interface ICar {
    float addNoteCleanliness(float note);
    float addNoteCar(float note);

    float getNoteCar();
    float getNoteCarCleanliness();


    /**
     * call to rent the Car
     * @return true if car successfully rented false otherwise
     */
    boolean rent();
    boolean unrent();

    float getRentPrice();
    float getSellPrice();

    boolean isSellable();

    String getModel();
    String getImagePath();

    String toJson(Long id);

}
