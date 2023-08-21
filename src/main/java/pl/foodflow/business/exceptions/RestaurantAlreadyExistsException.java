package pl.foodflow.business.exceptions;

public class RestaurantAlreadyExistsException extends RuntimeException {

    public RestaurantAlreadyExistsException(String message) {
        super(message);
    }
}
