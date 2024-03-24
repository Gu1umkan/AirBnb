package airbnb.validation.AnnouncementPrice;

import airbnb.validation.BigDecimalValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class AnnouncementPriceValidator implements ConstraintValidator<AnnouncementPriceValidation, BigDecimal> {
    @Override
    public boolean isValid(BigDecimal bigDecimal, ConstraintValidatorContext constraintValidatorContext) {
        return bigDecimal.compareTo(BigDecimal.ZERO) >= 0;
    }
}
