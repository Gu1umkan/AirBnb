package airbnb.repository;

import airbnb.dto.response.AnnouncementSortResponse;
import airbnb.entities.Announcement;
import airbnb.entities.enums.Role;
import airbnb.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    default Announcement findByAnnouncementId(Long announcementId) {
        return findById(announcementId).orElseThrow(() ->
                new NotFoundException("Application with id: " + announcementId + "  not found!"));
    }
//    @Modifying
//    @Transactional
    @Query("""
            select new airbnb.dto.response.AnnouncementSortResponse(a.id,a.price,a.title,a.description,a.maxOfGuests,
            a.town,a.address,a.isActive,a.rejectAnnouncement,a.images,a.houseType,a.region)
            from Announcement  a where a.region = :region
             """)
    List<AnnouncementSortResponse> sortByRegion(Role region);


//    @Modifying
//    @Transactional
@Query("""
    select new airbnb.dto.response.AnnouncementSortResponse(
        a.id,
        a.price,
        a.title,
        a.description,
        a.maxOfGuests,
        a.town,
        a.address,
        a.isActive,
        a.rejectAnnouncement,
        a.images,
        a.houseType,
        a.region
    ) 
    from Announcement a
""")
    List<AnnouncementSortResponse> findAllAnnouncement();
}