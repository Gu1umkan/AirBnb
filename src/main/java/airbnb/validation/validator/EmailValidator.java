package airbnb.validation.validator;


import airbnb.repository.UserRepository;
import airbnb.validation.EmailValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

//@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<EmailValidation, String> {
//    private final UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
//        boolean exists = userRepository.existsByEmail(email);
        return email.matches(".+@gmail\\.com");
    }
}
