package airbnb.api;

import airbnb.dto.request.AnnouncementRequest;
import airbnb.dto.response.AnnouncementSortResponse;
import airbnb.dto.response.SimpleResponse;
import airbnb.entities.enums.Role;
import airbnb.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcement")
@RequiredArgsConstructor
public class AnnouncementApi {
    private final AnnouncementService announcementService;

    @Secured("USER")
    @PostMapping("/saveAnnouncement")
    public SimpleResponse save(@RequestBody AnnouncementRequest announcementRequest){
        return announcementService.saveAnnouncement(announcementRequest);
    }
    @Secured("ADMIN")
    @PostMapping("/assign/{announcementId}")
    public SimpleResponse assignHome(@PathVariable Long announcementId){
        return  announcementService.assignHome(announcementId);
    }

    @Secured({"ADMIN,USER,VENDOR"})
    @GetMapping("/findAll")
    public List<AnnouncementSortResponse> findAll(){
        return announcementService.findAll();
    }

    @Secured({"ADMIN,USER,VENDOR"})
    @GetMapping("/sortByRegion")
    public List<AnnouncementSortResponse> sortByRegion(@RequestParam Role region) {
       return announcementService.sortByRegion(region);
   }







}
