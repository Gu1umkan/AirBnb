package airbnb.repository;

import airbnb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.id != 1")
    List<User> getAllUsers();
}
