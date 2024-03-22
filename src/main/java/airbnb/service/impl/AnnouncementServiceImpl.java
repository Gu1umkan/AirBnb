package airbnb.service.impl;

import airbnb.dto.request.AnnouncementRequest;
import airbnb.dto.response.SimpleResponse;
import airbnb.entities.Announcement;
import airbnb.entities.User;
import airbnb.entities.enums.Role;
import airbnb.repository.AnnouncementRepository;
import airbnb.repository.UserRepository;
import airbnb.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepo;
    @Override
    public SimpleResponse assignHome(Long  applicationId) {
        Announcement application = announcementRepo.findByAnnouncementId(applicationId);
        application.setIsActive(true);

        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Успешно одобреон !! ")
                .build();
    }

    @Override
    public SimpleResponse save(AnnouncementRequest announcementRequest) {
        Announcement announcement = new Announcement();
        User user = new User();

        announcement.setPrice(announcementRequest.getPrice());
        announcement.setTitle(announcementRequest.getTitle());
        announcement.setDescription(announcementRequest.getDescription());
        announcement.setMaxOfGuests(announcementRequest.getMaxOfGuests());
        announcement.setTown(announcementRequest.getTown());
        announcement.setAddress(announcementRequest.getAddress());
        announcement.setIsActive(false);
        announcement.setRejectAnnouncement(announcementRequest.getRejectAnnouncement());
        announcement.setImages(announcementRequest.getImages());
        announcement.setHouseType(announcementRequest.getHouseType());
        announcement.setRegion(announcementRequest.getRegion());
        announcement.setRating(announcementRequest.getRating());
        user.setRole(Role.VENDOR);

        announcementRepo.save(announcement);

          return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(" saved !! ")
                .build();
    }
}
