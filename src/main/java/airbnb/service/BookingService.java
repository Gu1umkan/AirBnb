package airbnb.service;

import airbnb.dto.request.BookingRequest;
import airbnb.dto.response.SimpleResponse;

public interface BookingService {
    SimpleResponse booking(BookingRequest bookingRequest,Long announcementId);
}
