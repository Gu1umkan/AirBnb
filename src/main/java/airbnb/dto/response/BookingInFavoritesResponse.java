package airbnb.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record BookingInFavoritesResponse(
        Long id,
        String fullName,
        String email,
        String image

) {
}
