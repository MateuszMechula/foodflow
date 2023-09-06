package pl.foodflow.business.exceptions;

public class MenuCategoryNotFoundException extends RuntimeException {

    public MenuCategoryNotFoundException(String message) {
        super(message);
    }
}
