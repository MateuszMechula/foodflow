package pl.foodflow.business.exceptions;

public class OrderRecordNotFoundException extends RuntimeException {

    public OrderRecordNotFoundException(String message) {
        super(message);
    }
}
