package airbnb.service.impl;


import airbnb.entities.User;
import airbnb.entities.enums.Role;
import airbnb.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Setter
@Getter
public class InitialService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private int numberOfEmployees;

    @PostConstruct
    public  void  saveDate(){
        String encode  = passwordEncoder.encode("java");


        User admin = User
                .builder()
                .fullName("Admin")
                .image("dsffsdf35343t3vd")
                .email("admin@gmail.com")
                .password(encode)
                .money(BigDecimal.valueOf(20000))
                .role(Role.ADMIN)
                .createdAt(LocalDate.of(2004,2,4))
                .build();

        userRepository.save(admin);
//        restaurantRepository.save(rest);

        // validate



    }

}
