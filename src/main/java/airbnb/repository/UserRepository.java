package airbnb.repository;

import airbnb.dto.response.SimpleResponse;
import airbnb.entities.User;
import airbnb.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    default User getByEmail(String email) {
        return findByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email: " + email + " not found!"));
    }


    @Query("select case when count(u)>0 then true  else false end from User u where u.email like :email")
    boolean existsByEmail(String email);

    default User gettById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found! "));
    }
}
