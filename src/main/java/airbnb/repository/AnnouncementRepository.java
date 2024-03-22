package airbnb.repository;

import airbnb.entities.Announcement;
import airbnb.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    default Announcement findByAnnouncementId(Long announcementId) {
        return findById(announcementId).orElseThrow(() ->
                new NotFoundException("Application with id: " + announcementId + "  not found!"));
    }
}