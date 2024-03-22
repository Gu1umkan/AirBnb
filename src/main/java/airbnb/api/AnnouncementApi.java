package airbnb.api;

import airbnb.dto.request.AnnouncementRequest;
import airbnb.dto.response.SimpleResponse;
import airbnb.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announcement")
@RequiredArgsConstructor
public class AnnouncementApi {
    private final AnnouncementService announcementService;


    @Secured("ADMIN")
    @PostMapping("/assign/{announcementId}")
    public SimpleResponse assignHome(@PathVariable Long announcementId){
        return  announcementService.assignHome(announcementId);
    }

    @Secured("USER")
    @PostMapping("/save")
    public SimpleResponse save(@RequestBody AnnouncementRequest announcementRequest ){
        return announcementService.save(announcementRequest);
    }
}
