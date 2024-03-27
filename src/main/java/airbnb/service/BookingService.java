package airbnb.service;

import airbnb.dto.request.BookingRequest;
import airbnb.dto.response.SecondBookingResponse;
import airbnb.dto.response.SimpleResponse;

import java.security.Principal;

public interface BookingService {
    SecondBookingResponse findBookingByUserAndAnnouncement(Long announcementId);
    SimpleResponse updateTimeOfBooking(BookingRequest bookingRequest, Long announcementId);
    SimpleResponse booking(BookingRequest bookingRequest, Long announcementId);

}
