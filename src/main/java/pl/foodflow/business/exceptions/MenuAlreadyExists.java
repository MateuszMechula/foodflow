package pl.foodflow.business.exceptions;

public class MenuAlreadyExists extends RuntimeException {

    public MenuAlreadyExists(String message) {
        super(message);
    }
}
