package airbnb.api;

import airbnb.dto.request.SignUpRequest;
import airbnb.dto.response.GetAllUserResponse;
import airbnb.dto.response.SimpleResponse;
import airbnb.dto.response.UserInfoResponse;
import airbnb.entities.User;
import airbnb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApi {
    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'VENDOR', 'USER')")
    @GetMapping("/getAll")
    public GetAllUserResponse getAll(@RequestParam int page, @RequestParam int size) {
        return userService.getAll(page, size);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'VENDOR', 'USER')")
    @GetMapping("/findById")
    public UserInfoResponse findById() {
        return userService.findById();
    }

    @Secured("ADMIN")
    @DeleteMapping("/delete/{id}")
    public SimpleResponse deleteById(@PathVariable Long id) {
        return userService.deleteById(id);
    }

    @Secured("ADMIN")
    @PutMapping("/update")
    public UserInfoResponse updateById(@RequestBody SignUpRequest signUpRequest) {
        return userService.updateById(signUpRequest);
    }


}
