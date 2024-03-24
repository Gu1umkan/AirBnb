package airbnb.service;

import airbnb.dto.request.AnnouncementComentRequest;
import airbnb.dto.request.AnnouncementRequest;
import airbnb.dto.response.AnnouncementResponse;
import airbnb.dto.response.SimpleResponse;
import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;

import java.math.BigDecimal;
import java.util.List;

public interface AnnouncementService {
    SimpleResponse assignHome(Long applicationId, AnnouncementComentRequest announcementComentRequest);

    SimpleResponse saveAnnouncement(AnnouncementRequest announcementRequest);

    List<AnnouncementResponse> findAll();


    SimpleResponse delete(Long announcementId);

    AnnouncementResponse sortByRegion(Region region);


    AnnouncementResponse sortByHouse(HouseType houseType);

    AnnouncementResponse sortByPrice(BigDecimal price);

    AnnouncementResponse Search(String search,Region region,HouseType houseType);

}
