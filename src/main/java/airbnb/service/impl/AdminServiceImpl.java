package airbnb.service.impl;
import airbnb.dto.response.HomeResponse;
import airbnb.dto.response.PaginationResponse;
import airbnb.dto.response.SimpleResponse;
import airbnb.entities.Announcement;
import airbnb.exception.NotFoundException;
import airbnb.repository.AdminRepository;
import airbnb.repository.AnnouncementRepository;
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

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AnnouncementRepository homeRepository;

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
}
