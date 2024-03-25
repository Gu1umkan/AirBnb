package airbnb.dto.response;

import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
public record FindALlBookingsResponse(
        int page,
        int size,
        List<BookingsFindAllResponse> bookingsFindAllResponses,
        LocalDate checkIn,
        LocalDate checkOut

) {
}
