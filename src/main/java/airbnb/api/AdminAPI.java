package airbnb.api;
import airbnb.dto.response.ForHomeProfile;
import airbnb.dto.response.ForUsersTable;
import airbnb.dto.response.PaginationResponse;
import airbnb.dto.response.SimpleResponse;
import airbnb.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admins")
@RequiredArgsConstructor
public class AdminAPI {
    private final AdminService adminService;
    @GetMapping("/application")
    PaginationResponse getAll(@RequestParam int page,
                              @RequestParam int size){
        return adminService.getAllHome(page,size);
    }
    @GetMapping("/reject/{id}")
    SimpleResponse reject(@PathVariable long id,
                          @RequestParam String cause){
        return adminService.reject(id, cause);
    }
    @DeleteMapping("/deleteHome/{id}")
    SimpleResponse delete(@PathVariable long id){
        return adminService.delete(id);
    }
    @PutMapping("/acceptHome/{id}")
    SimpleResponse accept (@PathVariable long id){
        return  adminService.accept(id);
    }
    @GetMapping("/findHome/{id}")
    ForHomeProfile find (@PathVariable long id){return adminService.find(id);}
    @GetMapping("/users")
    List<ForUsersTable> getAll (){
        return adminService.getAll();
    }

}
