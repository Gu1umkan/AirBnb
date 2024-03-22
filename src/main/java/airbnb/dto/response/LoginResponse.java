package airbnb.dto.response;

import airbnb.entities.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record LoginResponse(
        String token,
        Long id,
        String fullName,
        String image,
        String email,
        Role role,
        BigDecimal money,
        LocalDate  createAt

        ) {
}
