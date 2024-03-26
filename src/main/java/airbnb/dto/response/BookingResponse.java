package airbnb.dto.response;

import java.time.LocalDate;

public record BookingResponse(
        LocalDate checkIn,
        LocalDate checkOut
) {
}
