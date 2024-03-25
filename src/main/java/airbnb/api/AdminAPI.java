package airbnb.api;
import airbnb.dto.response.PaginationResponse;
import airbnb.dto.response.SimpleResponse;
import airbnb.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admins")
@RequiredArgsConstructor
public class AdminAPI {
    private final AdminService adminService;
    @GetMapping
    PaginationResponse getAll(@RequestParam int page,
                              @RequestParam int size){
        return adminService.getAllHome(page,size);
    }
    @GetMapping("/reject/{id}")
    SimpleResponse reject(@PathVariable long id,
                          @RequestParam String cause){
        return adminService.reject(id, cause);
    }
    @DeleteMapping("/delete/{id}")
    SimpleResponse delete(@PathVariable long id){
        return adminService.delete(id);
    }
    @PutMapping("/accept/{id}")
    SimpleResponse accept (@PathVariable long id){
        return  adminService.accept(id);
    }


}
