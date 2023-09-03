package pl.foodflow.business.exceptions;

public class OrderItemsNotFoundException extends RuntimeException {

    public OrderItemsNotFoundException(String message) {
        super(message);
    }
}
