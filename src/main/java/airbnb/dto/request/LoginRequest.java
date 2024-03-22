package airbnb.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
public record   LoginRequest(
        String email,
        String password
) {

}
