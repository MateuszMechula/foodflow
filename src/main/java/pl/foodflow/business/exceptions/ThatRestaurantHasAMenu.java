package pl.foodflow.business.exceptions;

public class ThatRestaurantHasAMenu extends RuntimeException {

    public ThatRestaurantHasAMenu(String message) {
        super(message);
    }
}
