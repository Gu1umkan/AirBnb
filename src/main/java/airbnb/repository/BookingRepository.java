package airbnb.repository;

import airbnb.entities.Announcement;
import airbnb.entities.Booking;
import airbnb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findBookingByUserAndAnnouncement(User user, Announcement announcement);

    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.announcement.id = :announcementId AND "
            + "((b.checkIn <= :checkOut AND b.checkOut >= :checkIn) OR "
            + "(b.checkIn >= :checkIn AND b.checkOut <= :checkOut) OR "
            + "(b.checkIn <= :checkIn AND b.checkOut >= :checkOut))")
    boolean isBookingAlready(
            LocalDate checkIn,
            LocalDate checkOut,
            Long announcementId);
}