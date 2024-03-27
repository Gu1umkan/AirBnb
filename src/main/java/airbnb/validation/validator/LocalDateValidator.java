package airbnb.validation.validator;

import jakarta.validation.ConstraintValidator;
import airbnb.validation.LocalDateValidation;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class LocalDateValidator implements ConstraintValidator<LocalDateValidation, LocalDate> {
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null) {
            return false;
        }
        LocalDate now = LocalDate.now();
        LocalDate nextYear = now.plusYears(1);
        return !localDate.isBefore(now) && !localDate.isAfter(nextYear);
    }
}
