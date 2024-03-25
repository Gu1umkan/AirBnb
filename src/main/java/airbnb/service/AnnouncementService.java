package airbnb.service;

import airbnb.dto.request.AnnouncementRequest;
import airbnb.dto.response.*;
import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;

import java.util.List;

public interface AnnouncementService {
    SimpleResponse assignHome(Long applicationId);

    SimpleResponse saveAnnouncement(AnnouncementRequest announcementRequest);

    List<AnnouncementResponsePagination> findAll(int page, int size);

    SimpleResponse delete(Long announcementId);

    List<AnnouncementResponse> sortByRegion(Region region);


    List<AnnouncementResponse> sortByHouse(HouseType houseType);

    List<AnnouncementResponse> sortByPrice(String ascOrDesc);

    List<AnnouncementResponse> Search(String search,Region region,HouseType houseType);

    List<AnnouncementResponse> isActive();

    FindByAnnouncementID findById(Long announcementId);


    List<FindALlBookingsResponse> findALlBookings(int page, int size);


    BookingsHouseResponse BookingsHouse(Long announcementId);
}
