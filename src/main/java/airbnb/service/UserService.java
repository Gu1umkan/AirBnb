package airbnb.service;

import airbnb.dto.request.LoginRequest;
import airbnb.dto.request.SingUpRequest;
import airbnb.dto.response.LoginResponse;
import airbnb.dto.response.RegisterResponse;
import airbnb.dto.response.SimpleResponse;

public interface UserService {

    RegisterResponse singUp(SingUpRequest singUpRequest);

    LoginResponse singIn(LoginRequest singInRequest);



}
