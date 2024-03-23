package airbnb.service.impl;

import airbnb.dto.request.AnnouncementRequest;
import airbnb.dto.response.AnnouncementSortResponse;
import airbnb.dto.response.SimpleResponse;
import airbnb.entities.Announcement;
import airbnb.entities.User;
import airbnb.entities.enums.Role;
import airbnb.repository.AnnouncementRepository;
import airbnb.repository.UserRepository;
import airbnb.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepo;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.getByEmail(email);
    }

    @Override
    public SimpleResponse assignHome(Long applicationId) {
        Announcement application = announcementRepo.findByAnnouncementId(applicationId);
        application.setIsActive(true);

        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Успешно одобреон !! ")
                .build();
    }

    @Override
    public SimpleResponse saveAnnouncement(AnnouncementRequest announcementRequest) {
        User currentUser = getCurrentUser();
        if (currentUser.getRole().equals(Role.USER)) currentUser.setRole(Role.VENDOR);
        announcementRepo.save(Announcement.builder()
                        .images(announcementRequest.images())
                        .title(announcementRequest.title())
                        .description(announcementRequest.description())
                        .houseType(announcementRequest.houseType())
                        .maxOfGuests(announcementRequest.maxOfGuests())
                        .price(announcementRequest.price())
                        .region(announcementRequest.region())
                        .address(announcementRequest.address())
                        .town(announcementRequest.town())
                        .isActive(false)
                        .user(currentUser)
                .build());
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved your application")
                .build();
    }

    @Override
    public List<AnnouncementSortResponse> sortByRegion(Role region) {
        return announcementRepo.sortByRegion(region);
    }

    @Override
    public List<AnnouncementSortResponse> findAll() {
        return announcementRepo.findAllAnnouncement();
    }
}
