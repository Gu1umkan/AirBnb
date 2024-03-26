package airbnb.service;

import airbnb.dto.request.BookingRequest;
import airbnb.dto.response.BookingResponse;
import airbnb.dto.response.SecondBookingResponse;
import airbnb.dto.response.SimpleResponse;

import java.security.Principal;

public interface BookingService {
    SecondBookingResponse findBookingByPrincipalAndAnnouncement(Principal principal, Long announcementId);
    SimpleResponse saveBooking(BookingRequest bookingRequest, Principal principal);
    SimpleResponse updateTimeOfBooking(BookingRequest bookingRequest, Principal principal);
}
