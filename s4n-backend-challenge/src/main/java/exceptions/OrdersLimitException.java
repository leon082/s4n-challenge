package exceptions;

public class OrdersLimitException extends RuntimeException {

    public OrdersLimitException(String message) {
        super(message);

    }
}
