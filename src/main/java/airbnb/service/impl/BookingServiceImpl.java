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

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    @Override
    public SecondBookingResponse findBookingByPrincipalAndAnnouncement(Principal principal, Long announcementId) {
        User loginUser = getPrincipalUser(principal);
        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(() -> new NotFoundException("Announcement with id " + announcementId + " not found!"));
        Booking booking = bookingRepository.findBookingByUserAndAnnouncement(loginUser, announcement);
        if (booking != null) {
            return new SecondBookingResponse(booking.getCheckIn(), booking.getCheckOut());
        }
        return null;
    }

    @Override
    public SimpleResponse saveBooking(BookingRequest bookingRequest, Principal principal) {
        User loginUser = getPrincipalUser(principal);

        Announcement announcement = announcementRepository.findById(bookingRequest.announcementId()).orElseThrow(() -> new NotFoundException("Announcement with id " + bookingRequest.announcementId() + " not found!"));
        Booking foundBooking = bookingRepository.findBookingByUserAndAnnouncement(loginUser, announcement);
        boolean isBookingAlready = bookingRepository.isBookingAlready(bookingRequest.checkIn(), bookingRequest.checkOut(), announcement.getId());

        if (foundBooking != null) {
            throw new BookingAlreadyExistsException("Booking with this user and announcement already exists");
        }
        if (isBookingAlready) {
            throw new BookingAlreadyExistsException("Booking already exists for the specified period!");
        }

        Booking booking = new Booking();
        booking.setAnnouncement(announcement);
        booking.setUser(loginUser);
        booking.setCheckIn(bookingRequest.checkIn());
        booking.setTotalPrice(bookingRequest.totalPrice());
        booking.setCheckOut(bookingRequest.checkOut());
        bookingRepository.save(booking);
        return SimpleResponse.builder().message("Successfully saved!").httpStatus(HttpStatus.OK).build();
    }

    private User getPrincipalUser(Principal principal) {
        if (principal == null) {
            throw new ForbiddenException("You must be authenticated to perform this action!");
        }
        String email = principal.getName();
        User loginUser = userRepository.getByEmail(email);
        if (loginUser.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("User with role ADMIN can't save/update/get booking!");
        }
        return loginUser;
    }

    @Override
    @Transactional
    public SimpleResponse updateTimeOfBooking(BookingRequest bookingRequest, Principal principal) {
        User loginUser = getPrincipalUser(principal);
        Announcement announcement = announcementRepository.findById(bookingRequest.announcementId()).orElseThrow(() -> new NotFoundException("Announcement with id " + bookingRequest.announcementId() + " not found!"));
        Booking booking = bookingRepository.findBookingByUserAndAnnouncement(loginUser, announcement);
        if (booking == null) {
            throw new NotFoundException("Booking with user and announcement not found!");
        }
        booking.setCheckOut(bookingRequest.checkOut());
        booking.setCheckIn(bookingRequest.checkIn());
        booking.setTotalPrice(bookingRequest.totalPrice());
        return SimpleResponse.builder().message("Successfully updated!").httpStatus(HttpStatus.OK).build();
    }
}
