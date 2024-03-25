package airbnb.api;
import airbnb.dto.request.AnnouncementRequest;
import airbnb.dto.response.AnnouncementResponse;
import airbnb.dto.response.AnnouncementResponsePagination;
import airbnb.dto.response.FindByAnnouncementID;
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
    public SimpleResponse assignHome(@PathVariable Long announcementId){
        return  announcementService.assignHome(announcementId);
    }

    @PreAuthorize("hasAnyAuthority('VENDOR','USER','ADMIN')")
    @GetMapping("/findAll")
    public List<AnnouncementResponsePagination> findAll(@RequestParam int page,
                                                        @RequestParam int size){
        return announcementService.findAll(page,size);
    }

    @PreAuthorize("hasAnyAuthority('VENDOR','USER','ADMIN')")
    @DeleteMapping("/delete/{announcementId}")
    public SimpleResponse delete(@PathVariable Long announcementId){
        return announcementService.delete(announcementId);
    }

    @PreAuthorize("hasAnyAuthority('VENDOR','USER','ADMIN')")
    @GetMapping("/sortByRegion")
    public List<AnnouncementResponse> sortByRegion(@RequestParam Region region){
       return announcementService.sortByRegion(region);
    }

    @PreAuthorize("hasAnyAuthority('VENDOR','USER','ADMIN')")
    @GetMapping("/sortByHouse")
    public List<AnnouncementResponse> sortByHouse(@RequestParam HouseType houseType){
        return announcementService.sortByHouse(houseType);
    }

    @PreAuthorize("hasAnyAuthority('VENDOR','USER','ADMIN')")
    @GetMapping("/SortByPrice")
    public List<AnnouncementResponse> sortByPrise(@RequestParam String ascOrDesc){
        return announcementService.sortByPrice(ascOrDesc);
    }

    @PreAuthorize("hasAnyAuthority('VENDOR','USER','ADMIN')")
    @GetMapping("/Search")
    public List<AnnouncementResponse> Search(@RequestParam String search,
                                       Region region,
                                       HouseType houseType){
        return announcementService.Search(search,region ,houseType );
    }
    @PreAuthorize("hasAnyAuthority('VENDOR','USER','ADMIN')")
    @GetMapping("/isActive")
    public  List<AnnouncementResponse> isActive(){
        return announcementService.isActive();
    }

    @PreAuthorize("hasAnyAuthority('VENDOR','USER','ADMIN')")
    @GetMapping("/findById/{announcementId}")
    public FindByAnnouncementID findById(@PathVariable Long announcementId){
        return announcementService.findById(announcementId);
    }














}
