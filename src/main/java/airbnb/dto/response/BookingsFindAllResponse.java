package airbnb.dto.response;

import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record BookingsFindAllResponse(
        String fullName,
        String email,
        String image,
        BigDecimal price,
        String title,
        String description,
        int maxOfGuests,
        String town,
        String address,

        HouseType houseType,
        Region region
) {
}
