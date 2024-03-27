package airbnb.service.impl;

import airbnb.dto.request.BookingRequest;
import airbnb.dto.response.SecondBookingResponse;
import airbnb.dto.response.SimpleResponse;
import airbnb.entities.Announcement;
import airbnb.entities.Booking;
import airbnb.entities.User;
import airbnb.entities.enums.Role;
import airbnb.exception.BookingAlreadyExistsException;
import airbnb.exception.ForbiddenException;
import airbnb.exception.NotFoundException;
import airbnb.repository.AnnouncementRepository;
import airbnb.repository.BookingRepository;
import airbnb.repository.UserRepository;
import airbnb.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;

    @Override
    public SecondBookingResponse findBookingByUserAndAnnouncement(Long announcementId) {
        User loginUser = getCurrentUser();
        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(() -> new NotFoundException("Announcement with id " + announcementId + " not found!"));
        Booking booking = bookingRepository.findBookingByUserAndAnnouncement(loginUser, announcement);
        if (booking != null) {
            return new SecondBookingResponse(booking.getCheckIn(), booking.getCheckOut());
        }
        return null;
    }

    @Override
    @Transactional
    public SimpleResponse updateTimeOfBooking(BookingRequest bookingRequest, Long announcementId) {
        User loginUser = getCurrentUser();
        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(() -> new NotFoundException("Announcement with id " + announcementId + " not found!"));
        BigDecimal price = announcement.getPrice();
        Booking booking = bookingRepository.findBookingByUserAndAnnouncement(loginUser, announcement);
        if (booking == null) {
            throw new NotFoundException("Booking with user and announcement not found!");
        }
        long daysBetween = ChronoUnit.DAYS.between(bookingRequest.checkIn(), bookingRequest.checkOut());
        long totalPrice = Math.multiplyExact(daysBetween, price.longValue());
        if (loginUser.getMoney().compareTo(BigDecimal.valueOf(totalPrice)) >= 0) {
            BigDecimal newBalance = loginUser.getMoney().subtract(BigDecimal.valueOf(totalPrice));
            loginUser.setMoney(newBalance);

            booking.setCheckIn(bookingRequest.checkIn());
            booking.setCheckOut(bookingRequest.checkOut());
            booking.setTotalPrice(BigDecimal.valueOf(totalPrice));

            return SimpleResponse.builder().message("Successfully updated!").httpStatus(HttpStatus.OK).build();
        } else {
            throw new NotFoundException("You don't have enough funds!");
        }
    }
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.getByEmail(email);
    }

    @Override
    public SimpleResponse booking(BookingRequest bookingRequest, Long announcementId) {
        User currentUser = getCurrentUser();
        Booking booking = new Booking();
        Announcement announcement = announcementRepository.findByAnnouncementId(announcementId);
        Booking foundBooking = bookingRepository.findBookingByUserAndAnnouncement(currentUser, announcement);
        BigDecimal price = announcement.getPrice();
        if (!currentUser.getRole().equals(Role.USER)) {
            throw new ForbiddenException("You can't book!!!");
        }
        if (bookingRequest.checkIn() == null || bookingRequest.checkOut() == null) {
            throw new NullPointerException("Check-in or check-out date is null!");
        }
        if (foundBooking != null) {
            throw new BookingAlreadyExistsException("Booking with this user and announcement already exists");
        }
        for (Booking announcementBooking : announcement.getBookings()) {
            if (bookingRequest.checkIn().isBefore(announcementBooking.getCheckOut()) &&
                    bookingRequest.checkOut().isAfter(announcementBooking.getCheckIn())) {
                throw new NotFoundException("This house is already occupied!");
            }
        }
        long daysBetween = ChronoUnit.DAYS.between(bookingRequest.checkIn(), bookingRequest.checkOut());
        long totalPrice = Math.multiplyExact(daysBetween, price.longValue());
        if (currentUser.getMoney().compareTo(BigDecimal.valueOf(totalPrice)) >= 0) {
            BigDecimal newBalance = currentUser.getMoney().subtract(BigDecimal.valueOf(totalPrice));
            currentUser.setMoney(newBalance);
            userRepository.save(currentUser);
            booking.setCheckIn(bookingRequest.checkIn());
            booking.setCheckOut(bookingRequest.checkOut());
            booking.setTotalPrice(BigDecimal.valueOf(totalPrice));
            booking.setUser(currentUser);
            booking.setAnnouncement(announcement);
            bookingRepository.save(booking);
            log.info("You have successfully booked a house for " + daysBetween + " days!");
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("You have successfully booked a house for " + daysBetween + " days!")
                    .build();
        } else {
            throw new NotFoundException("You don't have enough funds!");
        }
    }
}
