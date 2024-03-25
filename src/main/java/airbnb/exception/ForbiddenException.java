package airbnb.exception;

import lombok.extern.slf4j.Slf4j;


public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String message) {
        super(message);

    }
}
