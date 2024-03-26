package airbnb.api;

import airbnb.dto.request.BookingRequest;
import airbnb.dto.response.SimpleResponse;
import airbnb.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booking")
public class BookingApi {
     private final BookingService bookingService;
     @Secured("USER")
     @PostMapping("/save/{announcementId}")
     public SimpleResponse booking(@Valid @PathVariable Long announcementId, BookingRequest bookingRequest){
          return bookingService.booking(bookingRequest,announcementId);
     }
}
