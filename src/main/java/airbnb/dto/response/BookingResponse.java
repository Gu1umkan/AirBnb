package airbnb.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookingResponse(

        Long ids,
        String fullName,
        String email,
        String image,
        LocalDate checkIn,
        LocalDate checkOut,
        BigDecimal totalPrice

) {
}
