package airbnb.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
@Builder
public record BookingResponse(

//        private Long id;
//        private LocalDate checkIn;
//        private LocalDate checkOut;
//        private BigDecimal  totalPrice;

        Long id,
        String fullName,
        String email,
        String image,
        LocalDate checkIn,
        LocalDate checkOut,
        BigDecimal totalPrice


) {
}
