package airbnb.dto.request;


import airbnb.validation.EmailValidation;
import airbnb.validation.PasswordValidation;

public record LoginRequest(
        @EmailValidation
        String email,
        @PasswordValidation
        String password
) {

}
