package airbnb.validation.validator;

import airbnb.dto.request.BookingRequest;
import airbnb.validation.CheckOutAndCheckInValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class CheckOutAndCheckInValidator implements ConstraintValidator<CheckOutAndCheckInValidation, BookingRequest> {
    @Override
    public boolean isValid(BookingRequest bookingRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (bookingRequest == null) {
            return false;
        }
        LocalDate checkIn = bookingRequest.checkIn();
        LocalDate checkOut = bookingRequest.checkOut();
        if (checkIn == null || checkOut == null) {
            return false;
        }
        if (checkIn.isBefore(LocalDate.now())) {
            return false;
        }
        if (checkOut.isBefore(checkIn)) {
            return false;
        }
        return !checkOut.equals(checkIn);
    }
}