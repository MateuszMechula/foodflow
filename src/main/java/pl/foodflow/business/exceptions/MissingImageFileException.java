package pl.foodflow.business.exceptions;

public class MissingImageFileException extends RuntimeException {
    public MissingImageFileException(String message) {
        super(message);
    }
}
