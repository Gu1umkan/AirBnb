package airbnb.repository;

import airbnb.dto.response.AnnouncementResponse;
import airbnb.dto.response.FindByAnnouncementID;
import airbnb.entities.Announcement;
import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import airbnb.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
    List<AnnouncementResponse> sortByRegion(Region region);


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
    List<AnnouncementResponse> sortByHouse(HouseType houseType);

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
    List<AnnouncementResponse> sortByPrice(BigDecimal price);
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
     from Announcement a 
            where a.address like %:search%
            or a.region = :region
            or cast(a.price as string) like %:search%
            or a.town like %:search%
            or a.houseType = :houseType
            """)
    List<AnnouncementResponse> Search(String search,Region region,HouseType houseType);

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
    ) from Announcement a  where a.isActive = :is
    """)
    List<AnnouncementResponse> isActive(boolean is);


//     @Modifying
//     @Transactional
    @Query("""
    select new airbnb.dto.response.FindByAnnouncementID(
    u.fullName,
    u.email,
    u.image,
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
    ) from Announcement a join a.user u where  a.id =:announcementId
    """)
    FindByAnnouncementID findByAnnouncementID(Long announcementId);

    @Query("select  a from Announcement a ")
    Page<Announcement> getAllAnnouncement(Pageable pageable);

    @Query("select a from Announcement a order by  a.price asc")
    List<Announcement> SortAsc(String asc);

    @Query("select a from Announcement  a  order by  a.price desc ")
    List<Announcement> SortDesc(String desc);
}