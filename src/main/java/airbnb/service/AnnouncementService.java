package airbnb.service;

import airbnb.dto.request.AnnouncementRequest;
import airbnb.dto.response.AnnouncementSortResponse;
import airbnb.dto.response.SimpleResponse;
import airbnb.entities.enums.Role;

import java.util.List;

public interface AnnouncementService {
    SimpleResponse assignHome(Long applicationId);

    SimpleResponse saveAnnouncement(AnnouncementRequest announcementRequest);

    List<AnnouncementSortResponse> sortByRegion(Role region);

    List<AnnouncementSortResponse> findAll();

}
