package airbnb.validation;

import airbnb.validation.validator.CheckOutAndCheckInValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = CheckOutAndCheckInValidator.class)
public @interface CheckOutAndCheckInValidation {
    String message() default "{Invalid date!}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}