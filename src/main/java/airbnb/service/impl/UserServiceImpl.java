package airbnb.service.impl;

import airbnb.config.jwt.JwtService;
import airbnb.dto.request.LoginRequest;
import airbnb.dto.request.SignUpRequest;
import airbnb.dto.response.LoginResponse;
import airbnb.dto.response.RegisterResponse;
import airbnb.dto.response.SimpleResponse;
import airbnb.entities.User;
import airbnb.entities.enums.Role;
import airbnb.exception.NotFoundException;
import airbnb.repository.AnnouncementRepository;
import airbnb.repository.UserRepository;
import airbnb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AnnouncementRepository announcementRepository;

    @Override
    public SimpleResponse singUp(SignUpRequest singUpRequest) {
        if (userRepository.existsByEmail(singUpRequest.getEmail())) {
            throw new BadCredentialsException("User with " + singUpRequest.getEmail() + " already exists");
        }
        userRepository.save(User.builder()
                .fullName(singUpRequest.getFullName())
                .image(singUpRequest.getImage())
                .email(singUpRequest.getEmail())
                .password(passwordEncoder.encode(singUpRequest.getPassword()))
                .role(singUpRequest.getRole())
                .money(singUpRequest.getMoney())
                .build());
        log.info("User: " + singUpRequest.getFullName() + " successfully saved! ");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("User: " + singUpRequest.getFullName() + " successfully saved! ")
                .build();

    }

    @Override
    public LoginResponse singIn(LoginRequest singInRequest) {
        User byEmail = userRepository.getByEmail(singInRequest.email());
        boolean matches = passwordEncoder.matches(singInRequest.password(), byEmail.getPassword());
        if (!matches) throw new RuntimeException("Invalid password! ");
        String token = jwtService.createToken(byEmail);

        return LoginResponse
                .builder()
                .token(token)
                .id(byEmail.getId())
                .fullName(byEmail.getFullName())
                .image(byEmail.getImage())
                .email(byEmail.getEmail())
                .role(byEmail.getRole())
                .money(byEmail.getMoney())
                .build();
    }
}
