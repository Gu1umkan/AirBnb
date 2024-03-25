package airbnb.exception.handler;

import airbnb.exception.ExceptionResponse;
import airbnb.exception.ForbiddenException;
import airbnb.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalHandlerException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse moneyException (MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .exceptionClassName(e.getClass().getSimpleName())
                .message(e.getMessage())
                .build();
    }
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse forbiddenException (ForbiddenException e) {
        log.error(e.getMessage());
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.FORBIDDEN)
                .exceptionClassName(e.getClass().getSimpleName())
                .message(e.getMessage())
                .build();
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFound (NotFoundException e) {
        log.error(e.getMessage());
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .exceptionClassName(e.getClass().getSimpleName())
                .message(e.getMessage())
                .build();
    }
}
