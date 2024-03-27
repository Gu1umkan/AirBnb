package airbnb.validation;
import airbnb.validation.validator.LocalDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = LocalDateValidator.class
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface  LocalDateValidation {
    String message() default "{Date entered incorrectly!!!}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
