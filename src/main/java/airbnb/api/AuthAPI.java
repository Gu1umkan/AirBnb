package airbnb.api;

import airbnb.dto.request.LoginRequest;
import airbnb.dto.request.SignUpRequest;
import airbnb.dto.response.LoginResponse;
import airbnb.dto.response.RegisterResponse;
import airbnb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthAPI {

    private final UserService useService;

    @PostMapping
    public RegisterResponse singUp(@RequestBody SignUpRequest signUpRequest) {
        return useService.singUp(signUpRequest);
    }

    @GetMapping
    public LoginResponse singIn(@RequestBody LoginRequest singInRequest) {
        return useService.singIn(singInRequest);
    }


}
