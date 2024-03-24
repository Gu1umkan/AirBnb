package airbnb.service.impl;

import airbnb.dto.request.AnnouncementRequest;
import airbnb.dto.response.AnnouncementResponse;
import airbnb.dto.response.FindByAnnouncementID;
import airbnb.dto.response.SimpleResponse;
import airbnb.entities.Announcement;
import airbnb.entities.User;
import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import airbnb.entities.enums.Role;
import airbnb.repository.AnnouncementRepository;
import airbnb.repository.UserRepository;
import airbnb.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    @Transactional
    public SimpleResponse assignHome(Long applicationId) {
        Announcement application = announcementRepo.findByAnnouncementId(applicationId);
        application.setIsActive(true);


        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Успешно одобрен !! ")
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
    public List<AnnouncementResponse> findAll() {
        return announcementRepo.findAllAnnouncement();

    }

    @Override
    public SimpleResponse delete(Long announcementId) {
        Announcement announcement = announcementRepo.findById(announcementId).orElseThrow(()->
                new RuntimeException("invalid announcementId"));

        announcementRepo.delete(announcement);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("sonun ")
                .build();
      }

    @Override
    public List<AnnouncementResponse> sortByRegion(Region region) {
        return announcementRepo.sortByRegion(region);
    }

    @Override
    public List<AnnouncementResponse> sortByHouse(HouseType houseType) {
        return announcementRepo.sortByHouse(houseType);
    }

    @Override
    public List<AnnouncementResponse> sortByPrice(BigDecimal price) {
        return announcementRepo.sortByPrice(price);
    }

    @Override
    public List<AnnouncementResponse> Search(String search, Region region, HouseType houseType) {
        return announcementRepo.Search(search, region,houseType );
    }


    @Override
    public List<AnnouncementResponse> isActive() {
        boolean is = true;
        return announcementRepo.isActive(is);
    }

    @Override
    public FindByAnnouncementID findById(Long announcementId) {
        Announcement byAnnouncementId = announcementRepo.findByAnnouncementId(announcementId);
        return announcementRepo.findByAnnouncementID(byAnnouncementId.getId());
    }

}

