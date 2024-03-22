package airbnb.api;

import airbnb.dto.request.LoginRequest;
import airbnb.dto.request.SingUpRequest;
import airbnb.dto.response.LoginResponse;
import airbnb.dto.response.RegisterResponse;
import airbnb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
//@Tag(name = "Auth API")
public class AuthAPI {

    private final UserService useService;

    @PostMapping
    public RegisterResponse singUp (@RequestBody SingUpRequest singUpRequest){
        return  useService.singUp(singUpRequest);
    }

    @GetMapping
    public LoginResponse singIn (@RequestBody LoginRequest singInRequest){
        return useService.singIn(singInRequest);
    }




}
