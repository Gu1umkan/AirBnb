package airbnb.repository;

import airbnb.dto.response.BookingsHouseResponse;
import airbnb.entities.Announcement;
import airbnb.entities.Booking;
import airbnb.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
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