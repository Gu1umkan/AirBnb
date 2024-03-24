package airbnb.validation.AnnouncementPrice;

import airbnb.validation.validator.BigDecimalValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = AnnouncementPriceValidator.class
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnouncementPriceValidation {
    String message() default "{Price cannot be negative!}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
