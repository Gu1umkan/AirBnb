package airbnb.service.impl;
import airbnb.dto.response.*;
import airbnb.entities.Announcement;
import airbnb.entities.Booking;
import airbnb.entities.User;
import airbnb.exception.NotFoundException;
import airbnb.repository.AdminRepository;
import airbnb.repository.AnnouncementRepository;
import airbnb.repository.BookingRepository;
import airbnb.service.AdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AnnouncementRepository homeRepository;
    private final BookingRepository bookingRepository;

    @Override
    public PaginationResponse getAllHome(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Announcement> all = homeRepository.findAll(pageable);
        List<HomeResponse> list = new ArrayList<>();
        for (Announcement announcement : all) {
            if (!announcement.getIsActive()) {
                list.add(HomeResponse.builder()
                        .id(announcement.getId())
                        .price(announcement.getPrice())
                        .address(announcement.getAddress())
                        .maxOfGuests(announcement.getMaxOfGuests())
                        .description(announcement.getDescription())
                        .images(announcement.getImages())
                        .town(announcement.getTown())
                        .title(announcement.getTitle())
                        .houseType(announcement.getHouseType())
                        .region(announcement.getRegion())
                        .isActive(announcement.getIsActive())
                        .build());
            }
        }
        return PaginationResponse.builder()
                .page(all.getNumber()+1)
                .size(all.getTotalPages())
                .list(list).build();
    }

    @Override
    @Transactional
    public SimpleResponse reject(long id, String cause) {
        Announcement announcement = homeRepository.findById(id).orElseThrow(() -> new NotFoundException("Home with this id not found!"));
        announcement.setRejectAnnouncement(cause);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully sent!").build();


    }

    @Override
    public SimpleResponse delete(long id) {
        Announcement announcement = homeRepository.findById(id).orElseThrow(() -> new NotFoundException("Home with this id not found!"));
        homeRepository.delete(announcement);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted!").build();
    }

    @Override
    @Transactional
    public SimpleResponse accept(long id) {
        Announcement announcement = homeRepository.findById(id).orElseThrow(() -> new NotFoundException("Home with this id not found!"));
        announcement.setIsActive(true);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully accepted!").build();
    }

    @Override
    public ForHomeProfile find(long id) {
        Announcement announcement = homeRepository.findById(id).orElseThrow(() -> new NotFoundException("Home with this id not found!"));
        return ForHomeProfile.builder()
                .user(ForShortUserInfo.builder()
                        .gmail(announcement.getUser().getEmail())
                        .photo(announcement.getUser().getImage())
                        .fullName(announcement.getUser().getFullName())
                        .id(announcement.getUser().getId()).build())
                .title(announcement.getTitle())
                .houseType(announcement.getHouseType())
                .maxOfGuests(announcement.getMaxOfGuests())
                .description(announcement.getDescription())
                .address(announcement.getAddress())
                .town(announcement.getTown())
                .region(announcement.getRegion())
                .id(announcement.getId())
                .build();
    }

    @Override
    public List<ForUsersTable> getAll() {
        List<ForUsersTable> users = new ArrayList<>();
        List<User> allUsers = adminRepository.getAllUsers();
        for (User user : allUsers) {
            List<Booking> all = bookingRepository.findAll();
            int san1 = 0;
            for (Booking booking : all) {
               if (Objects.equals(booking.getUser().getId(), user.getId())){
                   san1 ++;
               }


            }
            int san = user.getAnnouncements().size();

            users.add(ForUsersTable.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .fullName(user.getFullName())
                    .bookings(san1)
                    .home(san)
                    .build());
        }
        return users;
    }

    @Override
    public ForUserProfileWithBookings getWithBookings(long id) {
        User user = adminRepository.findById(id).orElseThrow(() -> new NotFoundException("User with this id not found!"));
        List<Booking> allBookings = bookingRepository.findAll();
        List<HomeResponse> houses = new ArrayList<>();
        for (Booking allBooking : allBookings) {
            if (allBooking.getUser().getId().equals(id)){
                houses.add(HomeResponse.builder()
                        .rating(0)
                        .id(allBooking.getAnnouncement().getId())
                        .images(allBooking.getAnnouncement().getImages())
                        .title(allBooking.getAnnouncement().getTitle())
                        .isActive(allBooking.getAnnouncement().getIsActive())
                        .region(allBooking.getAnnouncement().getRegion())
                        .houseType(allBooking.getAnnouncement().getHouseType())
                        .town(allBooking.getAnnouncement().getTown())
                        .price(allBooking.getAnnouncement().getPrice())
                        .address(allBooking.getAnnouncement().getAddress())
                        .description(allBooking.getAnnouncement().getDescription()).build());
            }
        }
        return ForUserProfileWithBookings.builder()
                .id(user.getId())
                .image(user.getImage())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .houses(houses).build();
    }

    @Override
    public ForUserProfileWithBookings getWithAnnouncements(long id) {
        User user = adminRepository.findById(id).orElseThrow(() -> new NotFoundException("User with this id not found!"));
        List<Announcement> all = homeRepository.findAll();
        List<HomeResponse> houses = new ArrayList<>();
        for (Announcement allBooking : all) {
            if (allBooking.getUser().getId().equals(id)){
                houses.add(HomeResponse.builder()
                        .rating(0)
                        .id(allBooking.getId())
                        .images(allBooking.getImages())
                        .title(allBooking.getTitle())
                        .isActive(allBooking.getIsActive())
                        .region(allBooking.getRegion())
                        .houseType(allBooking.getHouseType())
                        .town(allBooking.getTown())
                        .price(allBooking.getPrice())
                        .address(allBooking.getAddress())
                        .description(allBooking.getDescription()).build());
            }
        }
        return ForUserProfileWithBookings.builder()
                .id(user.getId())
                .image(user.getImage())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .houses(houses).build();
    }
}
