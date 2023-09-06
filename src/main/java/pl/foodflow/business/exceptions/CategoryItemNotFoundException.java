package pl.foodflow.business.exceptions;

public class CategoryItemNotFoundException extends RuntimeException {

    public CategoryItemNotFoundException(String message) {
        super(message);
    }
}
