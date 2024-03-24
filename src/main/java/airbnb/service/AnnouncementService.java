package airbnb.service;

import airbnb.dto.request.AnnouncementRequest;
import airbnb.dto.response.AnnouncementResponse;
import airbnb.dto.response.FindByAnnouncementID;
import airbnb.dto.response.SimpleResponse;
import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;

import java.math.BigDecimal;
import java.util.List;

public interface AnnouncementService {
    SimpleResponse assignHome(Long applicationId);

    SimpleResponse saveAnnouncement(AnnouncementRequest announcementRequest);

    List<AnnouncementResponse> findAll();

    SimpleResponse delete(Long announcementId);

    List<AnnouncementResponse> sortByRegion(Region region);


    List<AnnouncementResponse> sortByHouse(HouseType houseType);

    List<AnnouncementResponse> sortByPrice(BigDecimal price);

    List<AnnouncementResponse> Search(String search,Region region,HouseType houseType);

    List<AnnouncementResponse> isActive();

    FindByAnnouncementID findById(Long announcementId);
}
