package pl.foodflow.business.exceptions;

public class OrdersNotFoundException extends RuntimeException {

    public OrdersNotFoundException(String message) {
        super(message);
    }
}
