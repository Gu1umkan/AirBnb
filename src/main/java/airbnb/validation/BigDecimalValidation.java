package airbnb.validation;

import airbnb.validation.validator.BigDecimalValidator;
import airbnb.validation.validator.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
            validatedBy = BigDecimalValidator.class
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface  BigDecimalValidation {
    String message() default "{Price cannot be negative!}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
