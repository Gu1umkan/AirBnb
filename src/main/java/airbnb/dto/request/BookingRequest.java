package airbnb.dto.request;

import airbnb.validation.CheckOutAndCheckInValidation;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@CheckOutAndCheckInValidation
public record BookingRequest(
        @NotNull
        LocalDate checkIn,
        @NotNull
        LocalDate checkOut,
        @NotNull
        BigDecimal totalPrice,
        @NotNull
        Long announcementId) {
}
