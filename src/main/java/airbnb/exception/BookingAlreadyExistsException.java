package airbnb.exception;

public class BookingAlreadyExistsException extends RuntimeException {
    public BookingAlreadyExistsException() {
        super();
    }

    public BookingAlreadyExistsException(String message) {
        super(message);
    }
}
