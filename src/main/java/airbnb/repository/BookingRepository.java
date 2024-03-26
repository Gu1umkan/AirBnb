package airbnb.repository;

import airbnb.entities.Announcement;
import airbnb.entities.Booking;
import airbnb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import airbnb.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
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
    default Booking findByBookingId(Long bookingId) {
        return findById(bookingId).orElseThrow(() ->
                new NotFoundException("Application with id: " + bookingId + "  not found!"));
    }
    @Query("select b from Booking b join  b.user u where u.id = :id ")
    Page<Booking> findByIdd(Pageable pageable, Long id);

//    @Query("""
//    select  new airbnb.dto.response.BookingsHouseResponse(
//
//    ) from Booking b
//    """)
//    List<BookingsHouseResponse> BookingHouse(Long id);

//    @Query(value = """
//     select i.images from announcement_images i
//     where i.announcement_id = :id
//     """, nativeQuery = true)
//
//    List<String> findImagesByHouseIdd(Long id);
}