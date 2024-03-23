package airbnb.service.impl;

import airbnb.dto.response.SimpleResponse;
import airbnb.entities.Announcement;
import airbnb.repository.AnnouncementRepository;
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
                .message("Успешно одобрен !! ")
                .build();
    }
}
