package airbnb.api;
import airbnb.dto.response.PaginationResponse;
import airbnb.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
