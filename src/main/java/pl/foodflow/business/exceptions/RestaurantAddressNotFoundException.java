package pl.foodflow.business.exceptions;

public class RestaurantAddressNotFoundException extends RuntimeException {

    public RestaurantAddressNotFoundException(String message) {
        super(message);
    }
}
