package airbnb.dto.response;

import airbnb.entities.enums.Role;
import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record UserInfoResponse(
        Long id,
        String fullName,
        String image,
        String email,
        Role role,
        BigDecimal money

){}
