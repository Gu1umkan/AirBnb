package airbnb.service;

import airbnb.dto.request.AnnouncementRequest;
import airbnb.dto.response.SimpleResponse;

public interface AnnouncementService {
    SimpleResponse assignHome(Long applicationId);


    SimpleResponse save(AnnouncementRequest announcementRequest);
}
