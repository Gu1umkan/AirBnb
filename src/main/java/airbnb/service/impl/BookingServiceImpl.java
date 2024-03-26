package airbnb.service.impl;

import airbnb.dto.request.BookingRequest;
import airbnb.dto.response.SimpleResponse;
import airbnb.entities.Announcement;
import airbnb.entities.Booking;
import airbnb.entities.User;
import airbnb.entities.enums.Role;
import airbnb.exception.ForbiddenException;
import airbnb.exception.NotFoundException;
import airbnb.repository.AnnouncementRepository;
import airbnb.repository.BookingRepository;
import airbnb.repository.UserRepository;
import airbnb.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.getByEmail(email);
    }

    @Override
    public SimpleResponse booking(BookingRequest bookingRequest, Long announcementId) {
        User currentUser = getCurrentUser();
        Booking booking = new Booking();
        Announcement announcement = announcementRepository.findByAnnouncementId(announcementId);
        BigDecimal price = announcement.getPrice();
        if (!currentUser.getRole().equals(Role.USER)) {
            throw new ForbiddenException("You can't book!!!");
        }
        if (bookingRequest.checkIn() != null && bookingRequest.checkOut() != null) {
            if (booking.getAnnouncement().getId() == null) {
                long daysBetween = ChronoUnit.DAYS.between(bookingRequest.checkIn(), bookingRequest.checkOut());
                long totalPrice = Math.multiplyExact(daysBetween, price.longValue());
                booking.setCheckIn(bookingRequest.checkIn());
                booking.setCheckOut(bookingRequest.checkOut());
                booking.setTotalPrice(BigDecimal.valueOf(totalPrice));
                booking.setUser(currentUser);
                booking.setAnnouncement(announcement);
                if (currentUser.getMoney().compareTo(BigDecimal.valueOf(totalPrice)) >= 0) {
                    bookingRepository.save(booking);
                    log.info("You have successfully booked a house for " + daysBetween + " days!");
                } else {
                    throw new NotFoundException("You don't have enough funds!");
                }
            }else {
                throw new NotFoundException("This house is already occupied!");
            }
        } else {
            throw new NullPointerException("Check-in or check-out date is null!");
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("You have successfully booked a house for days!")
                .build();
    }
}
