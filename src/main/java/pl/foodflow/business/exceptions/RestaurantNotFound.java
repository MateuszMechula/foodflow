package pl.foodflow.business.exceptions;

public class RestaurantNotFound extends RuntimeException {

    public RestaurantNotFound(String message) {
        super(message);
    }
}
