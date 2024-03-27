package airbnb.service.impl;

import airbnb.dto.request.AnnouncementRequest;
import airbnb.dto.response.*;
import airbnb.entities.*;
import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import airbnb.entities.enums.Role;
import airbnb.exception.NotFoundException;
import airbnb.repository.AnnouncementRepository;
import airbnb.repository.BookingRepository;
import airbnb.repository.UserRepository;
import airbnb.service.AnnouncementService;
import airbnb.service.BookingService;
import airbnb.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepo;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final FeedbackService feedbackService;
    private final BookingService bookingService;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.getByEmail(email);
    }

    @Override
    @Transactional
    public SimpleResponse assignHome(Long applicationId) {
        Announcement application = announcementRepo.findByAnnouncementId(applicationId);
        application.getUser().setRole(Role.VENDOR);
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

        for (Announcement announcementAlls : posts.getContent()) {
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
                    .image(announcementAlls.getImages())
                    .build();
            responseAll.add(announcementResponsePagination);
        }

        return responseAll;

    }

    @Override
    public SimpleResponse delete(Long announcementId) {
        Announcement announcement = announcementRepo.findById(announcementId).orElseThrow(() ->
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
        List<AnnouncementResponse> houseResponse = announcementRepo.sortByRegion(region);
        for (AnnouncementResponse h : houseResponse) {
            h.setImages(announcementRepo.findImagesByHouseId(h.getId()));
        }
        return houseResponse;
    }

    @Override
    public List<AnnouncementResponse> sortByHouse(HouseType houseType) {
        List<AnnouncementResponse> houseResponse = announcementRepo.sortByHouse(houseType);
        for (AnnouncementResponse h : houseResponse) {
            h.setImages(announcementRepo.findImagesByHouseId(h.getId()));
        }
        return houseResponse;
    }

    @Override
    public List<AnnouncementResponse> sortByPrice(String ascOrDesc) {
        List<AnnouncementResponse> announcementResponses = new ArrayList<>();

        if (ascOrDesc.equalsIgnoreCase("asc")) {
            List<Announcement> announcementAsc = announcementRepo.SortAsc("asc");
            for (Announcement announcement : announcementAsc) {
                announcementResponses.add(new AnnouncementResponse(announcement.getId(), announcement.getPrice(), announcement.getTitle(),
                        announcement.getDescription(), announcement.getMaxOfGuests(), announcement.getTown(), announcement.getAddress(),
                        announcement.getIsActive(), announcement.getRejectAnnouncement(), announcement.getHouseType(), announcement.getRegion()));
            }
        } else if (ascOrDesc.equalsIgnoreCase("desc")) {
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
        List<AnnouncementResponse> houseResponse = announcementRepo.Search(search, region, houseType);
        for (AnnouncementResponse h : houseResponse) {
            h.setImages(announcementRepo.findImagesByHouseId(h.getId()));
        }
        return houseResponse;
    }

    @Override
    public List<AnnouncementResponse> isActive() {
        boolean is = true;
        List<AnnouncementResponse> houseResponse = announcementRepo.isActive(is);
        for (AnnouncementResponse h : houseResponse) {
            h.setImages(announcementRepo.findImagesByHouseId(h.getId()));
        }
        return houseResponse;
    }

    @Override
    public FindByAnnouncementID findById(Long announcementId) {
        Announcement byAnnouncementId = announcementRepo.findByAnnouncementId(announcementId);
        User currentUser = getCurrentUser();
        return FindByAnnouncementID
                .builder()
                .id(byAnnouncementId.getId())
                .price(byAnnouncementId.getPrice())
                .title(byAnnouncementId.getTitle())
                .description(byAnnouncementId.getDescription())
                .maxOfGuests(byAnnouncementId.getMaxOfGuests())
                .town(byAnnouncementId.getTown())
                .myAnnouncementOrNot(currentUser.getId().equals(byAnnouncementId.getUser().getId()))
                .bookedOrNot(bookingService.findBookingByUserAndAnnouncement(byAnnouncementId.getId()))
                .address(byAnnouncementId.getAddress())
                .houseType(byAnnouncementId.getHouseType())
                .region(byAnnouncementId.getRegion())
                .feedbacks(feedbackService.getAllFeedbacksByAnnouncement(byAnnouncementId.getId()))
                .build();

    }


    ////////////////

    @Override
    @Transactional
    public List<FindALlBookingsResponse> findALlBookings(int page, int size) {
        User currentUser = getCurrentUser();

        List<BookingsFindAllResponse> bookingsFindAllResponses = new ArrayList<>();

        for (Announcement announcement : currentUser.getAnnouncements()) {
            for (Booking booking : announcement.getBookings()) {
                bookingsFindAllResponses.add(new BookingsFindAllResponse(
                        booking.getUser().getFullName(),
                        booking.getUser().getEmail(),
                        booking.getUser().getImage(),

                        booking.getAnnouncement().getPrice(),
                        booking.getAnnouncement().getTitle(),
                        booking.getAnnouncement().getDescription(),
                        booking.getAnnouncement().getMaxOfGuests(),
                        booking.getAnnouncement().getTown(),
                        booking.getAnnouncement().getAddress(),
                        booking.getAnnouncement().getHouseType(),
                        booking.getAnnouncement().getRegion()

                ));
            }
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Booking> posts = bookingRepository.findByIdd(pageable, currentUser.getId());
        List<FindALlBookingsResponse> responseAll = new ArrayList<>();

        for (Booking post : posts) {
            FindALlBookingsResponse findALlBookingsResponse = FindALlBookingsResponse
                    .builder()
                    .page(posts.getNumber() + 1)
                    .size(posts.getTotalPages())
                    .bookingsFindAllResponses(bookingsFindAllResponses)
                    .checkIn(post.getCheckIn())
                    .checkOut(post.getCheckOut())
                    .build();

            responseAll.add(findALlBookingsResponse);
        }

        return responseAll;
    }

    @Override
    @Transactional
    public BookingsHouseResponse BookingsHouse(Long announcementId) {
        Announcement byAnnouncementId = announcementRepo.findById(announcementId).orElseThrow(() ->
                new NotFoundException("announcementId invalid !!!  "));

        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (Booking booking : byAnnouncementId.getBookings()) {
            bookingResponses.add(new BookingResponse(
                    booking.getId(),
                    booking.getUser().getFullName(),
                    booking.getUser().getEmail(),
                    booking.getUser().getImage(),
                    booking.getCheckIn(),
                    booking.getCheckOut(),
                    booking.getTotalPrice()
            ));
        }

        List<BookingInFavoritesResponse> bookingInFavoritesResponseList = new ArrayList<>();
        for (Favorite favorite : byAnnouncementId.getFavorites()) {
            bookingInFavoritesResponseList.add(new BookingInFavoritesResponse(
                    favorite.getUser().getId(),
                    favorite.getUser().getFullName(),
                    favorite.getUser().getEmail(),
                    favorite.getUser().getImage()
            ));
        }


        return BookingsHouseResponse
                .builder()
                .id(byAnnouncementId.getId())
                .houseType(byAnnouncementId.getHouseType())
                .images(byAnnouncementId.getImages())
                .title(byAnnouncementId.getTitle())
                .description(byAnnouncementId.getDescription())
                .address(byAnnouncementId.getAddress())
                .town(byAnnouncementId.getTown())
                .region(byAnnouncementId.getRegion())
                .maxOfGuests(byAnnouncementId.getMaxOfGuests())
                .booked(bookingResponses)
                .inFavorites(bookingInFavoritesResponseList)
                .feedback(feedbackService.getAllFeedbacksByAnnouncement(byAnnouncementId.getId()))
                .build();
    }

    @Override
    @Transactional
    public List<VendorMyAnnouncementResponse> MyAnnouncement(int page, int size) {
        User currentUser = getCurrentUser();

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Announcement> posts = announcementRepo.getAllAnnouncement(pageable);
        List<VendorMyAnnouncementResponse> responseAll = new ArrayList<>();

        for (Announcement post : currentUser.getAnnouncements()) {
            VendorMyAnnouncementResponse findALlBookingsResponse = VendorMyAnnouncementResponse
                    .builder()
                    .page(posts.getNumber() + 1)
                    .size(posts.getTotalPages())
                    .fullName(post.getUser().getFullName())
                    .email(post.getUser().getEmail())
                    .image(post.getUser().getImage())
                    .price(post.getPrice())
                    .title(post.getTitle())
                    .description(post.getDescription())
                    .maxOfGuests(post.getMaxOfGuests())
                    .town(post.getTown())
                    .address(post.getAddress())
                    .isActive(post.getIsActive())
                    .rejectAnnouncement(post.getRejectAnnouncement())
                    .houseType(post.getHouseType())
                    .region(post.getRegion())
                    .build();
            responseAll.add(findALlBookingsResponse);
        }

        return responseAll;
    }

    @Override
    public SimpleResponse removeByAnnouncementVendor(Long announcementId) {
        Announcement byAnnouncementId = announcementRepo.findByAnnouncementId(announcementId);
        return announcementRepo.removeByAnnouncementVendor(byAnnouncementId.getId());
    }

    @Override
    @Transactional
    public SimpleResponse update(Long announcementId, AnnouncementUpdateRequest announcementRequest) {

        User currentUser = getCurrentUser();
        Announcement byAnnouncementId = announcementRepo.findByAnnouncementId(announcementId);


        if (currentUser.getRole().equals(Role.VENDOR)) {
            if (byAnnouncementId != null) {
                byAnnouncementId.setPrice(announcementRequest.getPrice());
                byAnnouncementId.setTitle(announcementRequest.getTitle());
                byAnnouncementId.setDescription(announcementRequest.getDescription());
                byAnnouncementId.setMaxOfGuests(announcementRequest.getMaxOfGuests());
                byAnnouncementId.setTown(announcementRequest.getTown());
                byAnnouncementId.setAddress(announcementRequest.getAddress());
                byAnnouncementId.setImages(announcementRequest.getImages());
                byAnnouncementId.setHouseType(announcementRequest.getHouseType());
                byAnnouncementId.setRegion(announcementRequest.getRegion());
            }
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("updated !! ")
                .build();
    }

}