package airbnb.dto.response;

import java.time.LocalDate;

public record SecondBookingResponse(
        LocalDate checkIn,
        LocalDate checkOut
        ) {
}
