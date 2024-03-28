package airbnb.service.impl;

import airbnb.config.jwt.JwtService;
import airbnb.dto.request.LoginRequest;
import airbnb.dto.request.SignUpRequest;
import airbnb.dto.response.GetAllUserResponse;
import airbnb.dto.response.LoginResponse;
import airbnb.dto.response.SimpleResponse;
import airbnb.dto.response.UserInfoResponse;
import airbnb.entities.Announcement;
import airbnb.entities.User;
import airbnb.entities.enums.Role;
import airbnb.exception.ForbiddenException;
import airbnb.exception.NotFoundException;
import airbnb.repository.AnnouncementRepository;
import airbnb.repository.UserRepository;
import airbnb.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AnnouncementRepository announcementRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.getByEmail(email);
    }

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
                .money(singUpRequest.getMoney())
                .role(Role.USER)
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

    @Override
    public GetAllUserResponse getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<User> users = userRepository.findAll(pageable);
        List<UserInfoResponse> userInfoResponseList = new ArrayList<>();
        for (User user : users) {
            userInfoResponseList.add(user.convert());
        }
        return GetAllUserResponse.builder()
                .page(users.getNumber() + 1)
                .size(users.getTotalPages())
                .userInfoResponses(userInfoResponseList)
                .build();
    }

    @Override
    public UserInfoResponse findById() {
        User user = getCurrentUser();
        return UserInfoResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .image(user.getImage())
                .email(user.getEmail())
                .role(user.getRole())
                .money(user.getMoney())
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        User user = userRepository.gettById(id);
        if (user.getRole().equals(Role.ADMIN)) {
            log.error(" You can't remove the administrator!!! ");
            throw new ForbiddenException(" You can't remove the administrator!!! ");
        }
        userRepository.delete(user);
        log.info(" User with: " + user.getFullName() + " deleted! ");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("User with: " + user.getFullName() + " deleted! ")
                .build();
    }

    @Override
    @Transactional
    public UserInfoResponse updateById(SignUpRequest signUpRequest) {
        User user = getCurrentUser();
        user.setFullName(signUpRequest.getFullName());
        user.setImage(signUpRequest.getImage());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setMoney(signUpRequest.getMoney());
        return UserInfoResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .image(user.getImage())
                .email(user.getEmail())
                .role(user.getRole())
                .money(user.getMoney())
                .build();
    }

    @Override
    public SimpleResponse assignUser(Long announcementId) {
        User currentUser = getCurrentUser();
        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(() ->
                new NotFoundException("Announcement with: " + announcementId + " not found! "));
        currentUser.getAnnouncements().add(announcement);
        announcement.setUser(currentUser);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(" Appointment was successful!!! ")
                .build();

    }


}
