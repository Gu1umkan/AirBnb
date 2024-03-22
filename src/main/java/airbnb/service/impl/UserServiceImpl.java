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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AnnouncementRepository  announcementRepository;

    @Override
    public RegisterResponse singUp(SignUpRequest singUpRequest) {
        boolean  exists = userRepository.existsByEmail(singUpRequest.getEmail());
        if(exists) throw  new NotFoundException("Email : " + singUpRequest.getEmail()+ " already exist");
        if(!singUpRequest.getPassword().equals(singUpRequest.getPasswordConfig()))
            throw new NotFoundException("Invalid password");

        User user = new User();
        user.setFullName(singUpRequest.getFullName());
        user.setEmail(singUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(singUpRequest.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        String newToken = jwtService.createToken(user);

        return RegisterResponse.builder()
                .token(newToken)
                .simpleResponse(
                        SimpleResponse.builder()
                                .httpStatus(HttpStatus.OK)
                                .message("Successfully sign - up! ")
                                .build())
                .build();

    }

    @Override
    public LoginResponse singIn(LoginRequest singInRequest) {
        User user =  userRepository.findByEmail(singInRequest.email()).orElseThrow(() ->
                new NotFoundException("User with email: " + singInRequest.email()+" not found!"));
        String encodePassword = user.getPassword();
        String password = singInRequest.password();

        boolean matches = passwordEncoder.matches(password,encodePassword);
        if(!matches) throw new NotFoundException("Invalid password !! ");

        String token = jwtService.createToken(user);

        return LoginResponse
                .builder()
                .token(token)
                .id(user.getId())
                .fullName(user.getFullName())
                .image(user.getImage())
                .email(user.getEmail())
                .role(user.getRole())
                .money(user.getMoney())
                .createAt(user.getCreatedAt())
                .build();
    }
}
