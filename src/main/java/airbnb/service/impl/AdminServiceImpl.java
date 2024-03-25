package airbnb.service.impl;

import airbnb.dto.response.AnnouncementResponse;
import airbnb.dto.response.HomeResponse;
import airbnb.dto.response.PaginationResponse;
import airbnb.entities.Announcement;
import airbnb.repository.AdminRepository;
import airbnb.repository.AnnouncementRepository;
import airbnb.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
           list.add(HomeResponse.builder()
                   .id(announcement.getId())
                   .price(announcement.getPrice())
                   .address(announcement.getAddress())
                   .maxOfGuests(announcement.getMaxOfGuests())
                   .description(announcement.getDescription())
                   .images(announcement.getImages())
                   .build()) ;
        }
        return PaginationResponse.builder()
                .page(all.getNumber()+1)
                .size(all.getTotalPages())
                .list(list).build();
    }
}
