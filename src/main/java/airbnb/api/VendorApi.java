package airbnb.api;

import airbnb.dto.response.AnnouncementResponse;
import airbnb.dto.response.BookingsHouseResponse;
import airbnb.dto.response.FindALlBookingsResponse;
import airbnb.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor")
@RequiredArgsConstructor
public class VendorApi {
    private final AnnouncementService announcementService;

    @PreAuthorize("hasAnyAuthority('VENDOR')")
    @GetMapping("/findAllBookings")
    public List<FindALlBookingsResponse> findALlBookings(@RequestParam int page,int size){
        return announcementService.findALlBookings(page,size);
    }

    @PreAuthorize("hasAnyAuthority('VENDOR')")
    @GetMapping("/{announcementId}")
    public BookingsHouseResponse BookingsHouse(@PathVariable Long announcementId){
        return announcementService.BookingsHouse(announcementId);
    }


}
