package airbnb.repository;

import airbnb.dto.response.AnnouncementResponse;
import airbnb.entities.Announcement;
import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import airbnb.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    default Announcement findByAnnouncementId(Long announcementId) {
        return findById(announcementId).orElseThrow(() ->
                new NotFoundException("Application with id: " + announcementId + "  not found!"));
    }

    @Query("""
    select new airbnb.dto.response.AnnouncementResponse(
    a.id,
    a.price,
    a.title,
    a.description,
    a.maxOfGuests,
    a.town,
    a.address,
    a.isActive,
    a.rejectAnnouncement,
    a.houseType,
    a.region
    ) from Announcement  a  
     """)
    List<AnnouncementResponse> findAllAnnouncement();


    @Query("""
    select new airbnb.dto.response.AnnouncementResponse(
    a.id,
    a.price,
    a.title,
    a.description,
    a.maxOfGuests,
    a.town,
    a.address,
    a.isActive,
    a.rejectAnnouncement,
    a.houseType,
    a.region
    ) from Announcement  a  where a.region = :region
     """)
    AnnouncementResponse sortByRegion(Region region);


    @Query("""
    select new airbnb.dto.response.AnnouncementResponse(
    a.id,
    a.price,
    a.title,
    a.description,
    a.maxOfGuests,
    a.town,
    a.address,
    a.isActive,
    a.rejectAnnouncement,
    a.houseType,
    a.region
    ) from Announcement  a  where a.houseType = :houseType
     """)
    AnnouncementResponse sortByHouse(HouseType houseType);

    @Query("""
    select new airbnb.dto.response.AnnouncementResponse(
    a.id,
    a.price,
    a.title,
    a.description,
    a.maxOfGuests,
    a.town,
    a.address,
    a.isActive,
    a.rejectAnnouncement,
    a.houseType,
    a.region
    ) from Announcement  a  where a.price = :price
     """)
    AnnouncementResponse sortByPrice(BigDecimal price);
    @Query("""
    select new airbnb.dto.response.AnnouncementResponse(
    a.id,
    a.price,
    a.title,
    a.description,
    a.maxOfGuests,
    a.town,
    a.address,
    a.isActive,
    a.rejectAnnouncement,
    a.houseType,
    a.region
    ) 
     from Announcement a\s
            where a.address like %:search%
            or a.region = :region
            or cast(a.price as string) like %:search%
            or a.town like %:search%
            or a.houseType = :houseType
            """)
    AnnouncementResponse Search(String search,Region region,HouseType houseType);

}