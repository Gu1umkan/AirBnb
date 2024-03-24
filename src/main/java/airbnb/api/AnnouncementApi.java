package airbnb.api;

import airbnb.dto.request.AnnouncementComentRequest;
import airbnb.dto.request.AnnouncementRequest;
import airbnb.dto.response.AnnouncementResponse;
import airbnb.dto.response.SimpleResponse;
import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import airbnb.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/announcement")
@RequiredArgsConstructor
public class AnnouncementApi {
    private final AnnouncementService announcementService;
    @PostMapping("/saveAnnouncement")
    public SimpleResponse save(@RequestBody AnnouncementRequest announcementRequest){
        return announcementService.saveAnnouncement(announcementRequest);
    }


    @Secured("ADMIN")
    @PostMapping("/assign/{announcementId}")
    public SimpleResponse assignHome(@PathVariable Long announcementId,
                                     @RequestBody AnnouncementComentRequest announcementComentRequest ){
        return  announcementService.assignHome(announcementId,announcementComentRequest);
    }

    @Secured({"ADMIN,USER,VENDOR"})
    @GetMapping("/findAll")
    public List<AnnouncementResponse> findAll(){
        return announcementService.findAll();
    }

    @PreAuthorize("hasAnyAuthority('VENDOR','USER','ADMIN')")
    @DeleteMapping("/delete/{announcementId}")
    public SimpleResponse delete(@PathVariable Long announcementId){
        return announcementService.delete(announcementId);
    }

    @PreAuthorize("hasAnyAuthority('VENDOR','USER','ADMIN')")
    @GetMapping("/sortByRegion")
    public AnnouncementResponse sortByRegion(@RequestParam Region region){
       return announcementService.sortByRegion(region);
    }

    @PreAuthorize("hasAnyAuthority('VENDOR','USER','ADMIN')")
    @GetMapping("/sortByHouse")
    public AnnouncementResponse sortByHouse(@RequestParam HouseType houseType){
        return announcementService.sortByHouse(houseType);
    }

    @PreAuthorize("hasAnyAuthority('VENDOR','USER','ADMIN')")
    @GetMapping("/SortByPrice")
    public AnnouncementResponse sortByPrise(@RequestParam BigDecimal price){
        return announcementService.sortByPrice(price);
    }

    @PreAuthorize("hasAnyAuthority('VENDOR','USER','ADMIN')")
    @GetMapping("/Search")
    public AnnouncementResponse Search(@RequestParam String search,
                                       Region region,
                                       HouseType houseType){
        return announcementService.Search(search,region ,houseType );
    }







}
