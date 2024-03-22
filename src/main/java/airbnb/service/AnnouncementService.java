package airbnb.service;

import airbnb.dto.response.SimpleResponse;

public interface AnnouncementService {
    SimpleResponse assignHome(Long applicationId);
}
