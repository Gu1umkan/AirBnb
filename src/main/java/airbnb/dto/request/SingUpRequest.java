package airbnb.dto.request;

import airbnb.entities.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Setter
@Getter
@Builder
public class SingUpRequest {

    private String fullName;

    private String  image;

    private String email;

    private String password;
    private String PasswordConfig;

    @Enumerated(EnumType.STRING)
    private Role role;

    private BigDecimal money;

    private LocalDate createdAt;
}
