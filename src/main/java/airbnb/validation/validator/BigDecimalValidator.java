package airbnb.validation.validator;


import airbnb.repository.UserRepository;
import airbnb.validation.BigDecimalValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;


public class BigDecimalValidator implements ConstraintValidator<BigDecimalValidation, BigDecimal> {


    @Override
    public boolean isValid(BigDecimal bigDecimal, ConstraintValidatorContext constraintValidatorContext) {
        return bigDecimal.compareTo(BigDecimal.ZERO) >= 0;
    }
}
