package airbnb.dto.request;

import airbnb.entities.enums.Role;
import airbnb.validation.BigDecimalValidation;
import airbnb.validation.EmailValidation;
import airbnb.validation.PasswordValidation;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Builder
public class SignUpRequest {
    @NotBlank
    private String fullName;
    @NotNull
    private String image;
    @EmailValidation
    private String email;
    @PasswordValidation
    private String password;
    @BigDecimalValidation
    private BigDecimal money;

}
