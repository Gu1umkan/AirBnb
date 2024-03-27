package airbnb.dto.request;

import airbnb.validation.LocalDateValidation;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BookingRequest(
        @LocalDateValidation
        LocalDate checkIn,
        @LocalDateValidation
        LocalDate checkOut
) {
}
