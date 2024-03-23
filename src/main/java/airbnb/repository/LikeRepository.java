package airbnb.repository;

import airbnb.entities.Feedback;
import airbnb.entities.Like;
import airbnb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByUserAndFeedback(User user, Feedback feedback);
}