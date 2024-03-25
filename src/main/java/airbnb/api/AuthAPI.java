package airbnb.api;

import airbnb.dto.request.LoginRequest;
import airbnb.dto.request.SignUpRequest;
import airbnb.dto.response.GetAllUserResponse;
import airbnb.dto.response.LoginResponse;
import airbnb.dto.response.RegisterResponse;
import airbnb.dto.response.SimpleResponse;
import airbnb.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthAPI {

    private final UserService useService;

    @PostMapping("/signUp")
    public SimpleResponse singUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return useService.singUp(signUpRequest);



    }

    @PostMapping("/signIn")
    public LoginResponse singIn(@Valid @RequestBody LoginRequest singInRequest) {
        return useService.singIn(singInRequest);
    }


}
