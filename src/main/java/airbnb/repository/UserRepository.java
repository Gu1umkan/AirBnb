package airbnb.repository;

import airbnb.dto.response.SimpleResponse;
import airbnb.entities.User;
import airbnb.exception.NotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

   Optional<User> findByEmail(String email);
   default User getByEmail(String email){
       return findByEmail(email).orElseThrow(()->
               new NotFoundException("User with email: " + email + " not found!"));
   }


    boolean existsByEmail(String email);

}
