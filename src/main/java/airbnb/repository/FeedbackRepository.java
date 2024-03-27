package airbnb.repository;

import airbnb.entities.Announcement;
import airbnb.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findAllByAnnouncement(Announcement announcement);
}