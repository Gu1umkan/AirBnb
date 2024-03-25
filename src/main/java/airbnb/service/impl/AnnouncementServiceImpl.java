package airbnb.service.impl;

import airbnb.dto.request.AnnouncementRequest;
import airbnb.dto.response.*;
import airbnb.entities.Announcement;
import airbnb.entities.Booking;
import airbnb.entities.User;
import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import airbnb.entities.enums.Role;
import airbnb.repository.AnnouncementRepository;
import airbnb.repository.BookingRepository;
import airbnb.repository.UserRepository;
import airbnb.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepo;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;


    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.getByEmail(email);
    }

    @Override
    @Transactional
    public SimpleResponse assignHome(Long applicationId) {
        Announcement application = announcementRepo.findByAnnouncementId(applicationId);
        application.setIsActive(true);


        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Успешно одобрен !! ")
                .build();
    }

    @Override
    public SimpleResponse saveAnnouncement(AnnouncementRequest announcementRequest) {
        User currentUser = getCurrentUser();
        if (currentUser.getRole().equals(Role.USER)) currentUser.setRole(Role.VENDOR);
        announcementRepo.save(Announcement.builder()
                        .images(announcementRequest.images())
                        .title(announcementRequest.title())
                        .description(announcementRequest.description())
                        .houseType(announcementRequest.houseType())
                        .maxOfGuests(announcementRequest.maxOfGuests())
                        .price(announcementRequest.price())
                        .region(announcementRequest.region())
                        .address(announcementRequest.address())
                        .town(announcementRequest.town())
                        .isActive(false)
                        .user(currentUser)
                .build());
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved your application")
                .build();
    }

    @Override
    public List<AnnouncementResponsePagination> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Announcement> posts = announcementRepo.getAllAnnouncement(pageable);
        List<AnnouncementResponsePagination> responseAll = new ArrayList<>();

        for (Announcement announcementAlls :posts.getContent() ){
            AnnouncementResponsePagination announcementResponsePagination = AnnouncementResponsePagination
                    .builder()
                    .page(posts.getNumber() + 1)
                    .size(posts.getTotalPages())
                    .price(announcementAlls.getPrice())
                    .title(announcementAlls.getTitle())
                    .description(announcementAlls.getDescription())
                    .maxOfGuests(announcementAlls.getMaxOfGuests())
                    .town(announcementAlls.getTown())
                    .address(announcementAlls.getAddress())
                    .isActive(announcementAlls.getIsActive())
                    .rejectAnnouncement(announcementAlls.getRejectAnnouncement())
                    .houseType(announcementAlls.getHouseType())
                    .region(announcementAlls.getRegion())
                    .build();
            responseAll.add(announcementResponsePagination);
        }

        return responseAll;

    }

    @Override
    public SimpleResponse delete(Long announcementId) {
        Announcement announcement = announcementRepo.findById(announcementId).orElseThrow(()->
                new RuntimeException("invalid announcementId"));

        announcementRepo.delete(announcement);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("sonun ")
                .build();
      }

    @Override
    public List<AnnouncementResponse> sortByRegion(Region region) {
        return announcementRepo.sortByRegion(region);
    }

    @Override
    public List<AnnouncementResponse> sortByHouse(HouseType houseType) {
        List<AnnouncementResponse> houseResponse = announcementRepo.sortByHouse(houseType);
        for (AnnouncementResponse h : houseResponse) {
            h.setImages( announcementRepo.findImagesByHouseId(h.getId()));
        }
        return houseResponse;
    }

    @Override
    public List<AnnouncementResponse> sortByPrice(String ascOrDesc) {
        List<AnnouncementResponse> announcementResponses = new ArrayList<>();

        if(ascOrDesc.equalsIgnoreCase("asc")){
            List<Announcement> announcementAsc = announcementRepo.SortAsc("asc");
            for (Announcement announcement : announcementAsc) {
                announcementResponses.add(new AnnouncementResponse(announcement.getId(), announcement.getPrice(), announcement.getTitle(),
                        announcement.getDescription(), announcement.getMaxOfGuests(), announcement.getTown(), announcement.getAddress(),
                        announcement.getIsActive(), announcement.getRejectAnnouncement(), announcement.getHouseType(), announcement.getRegion()));
               }
        }else if(ascOrDesc.equalsIgnoreCase("desc")){
            List<Announcement> announcementDesc = announcementRepo.SortDesc("desc");
            for (Announcement announcement : announcementDesc) {
                announcementResponses.add(new AnnouncementResponse(announcement.getId(), announcement.getPrice(), announcement.getTitle(),
                        announcement.getDescription(), announcement.getMaxOfGuests(), announcement.getTown(), announcement.getAddress(),
                        announcement.getIsActive(), announcement.getRejectAnnouncement(), announcement.getHouseType(), announcement.getRegion()));
            }
        }
        return announcementResponses;
    }

    @Override
    public List<AnnouncementResponse> Search(String search, Region region, HouseType houseType) {
     return announcementRepo.Search(search,region,houseType);
    }

    @Override
    public List<AnnouncementResponse> isActive() {
        boolean is = true;
        return announcementRepo.isActive(is);
    }

    @Override
    public FindByAnnouncementID findById(Long announcementId) {
        Announcement byAnnouncementId = announcementRepo.findByAnnouncementId(announcementId);
        return announcementRepo.findByAnnouncementID(byAnnouncementId.getId());
    }

    @Override
    public List<FindALlBookingsResponse> findALlBookings(int page, int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Booking> posts = bookingRepository.findAll(pageable);
        List<FindALlBookingsResponse> responseAll = new ArrayList<>();

        for ( Booking post : posts){
            FindALlBookingsResponse findALlBookingsResponse = FindALlBookingsResponse
                    .builder()
                    .page(posts.getNumber() + 1)
                    .size(posts.getTotalPages())
                    .fullName(post.getUser().getFullName())
                    .email(post.getAnnouncement().getUser().getEmail())
                    .image(post.getAnnouncement().getUser().getImage())
                    .price(post.getAnnouncement().getPrice())
                    .title(post.getAnnouncement().getTitle())
                    .description(post.getAnnouncement().getDescription())
                    .maxOfGuests(post.getAnnouncement().getMaxOfGuests())
                    .town(post.getAnnouncement().getTown())
                    .address(post.getAnnouncement().getAddress())
                    .isActive(post.getAnnouncement().getIsActive())
                    .rejectAnnouncement(post.getAnnouncement().getRejectAnnouncement())
                    .houseType(post.getAnnouncement().getHouseType())
                    .region(post.getAnnouncement().getRegion())
                    .checkIn(post.getCheckIn())
                    .checkOut(post.getCheckOut())
                    .build();
            responseAll.add(findALlBookingsResponse);
        }

        return responseAll;
    }

    @Override
    public BookingsHouseResponse BookingsHouse(Long announcementId) {
        return  null;

    }

}

