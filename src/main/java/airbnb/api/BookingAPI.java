package airbnb.api;

import airbnb.dto.request.BookingRequest;
import airbnb.dto.response.SimpleResponse;
import airbnb.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingAPI {
    private final BookingService bookingService;

    @PreAuthorize("hasAnyAuthority('VENDOR', 'USER')")
    @Operation(description = "Save booking")
    @PostMapping
    public SimpleResponse saveBooking(@RequestBody @Valid BookingRequest bookingRequest, Principal principal) {
        return bookingService.saveBooking(bookingRequest, principal);
    }

    @PreAuthorize("hasAnyAuthority('VENDOR', 'USER')")
    @Operation(description = "Update booking")
    @PutMapping
    public SimpleResponse updateBooking(@RequestBody @Valid BookingRequest bookingRequest, Principal principal) {
        return bookingService.updateTimeOfBooking(bookingRequest, principal);
    }
}
