package airbnb.api;

import airbnb.dto.request.AnnouncementRequest;
import airbnb.dto.response.*;
import airbnb.service.AnnouncementService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
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
    @GetMapping("/BookingsHouse/{announcementId}")
    public BookingsHouseResponse BookingsHouse(@PathVariable Long announcementId){
        return announcementService.BookingsHouse(announcementId);
    }

    @PreAuthorize("hasAuthority('VENDOR')")
    @GetMapping("/MyAnnouncement")
    public List<VendorMyAnnouncementResponse> MyAnnouncement(@RequestParam int page, int size){
        return announcementService.MyAnnouncement(page,size);
    }

    @PreAuthorize("hasAuthority('VENDOR')")
    @DeleteMapping("/delete/{announcementId}")
    public SimpleResponse delete(@PathVariable Long announcementId){
        return announcementService.removeByAnnouncementVendor(announcementId);
    }

    @PreAuthorize("hasAuthority('VENDOR')")
    @PutMapping("/update/{announcementId}")
    public SimpleResponse update(@PathVariable Long announcementId,
                                 @RequestBody AnnouncementUpdateRequest announcementRequest){
        return announcementService.update(announcementId,announcementRequest);
    }






}
