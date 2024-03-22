package airbnb.service;

import airbnb.dto.request.LoginRequest;
import airbnb.dto.request.SignUpRequest;
import airbnb.dto.response.LoginResponse;
import airbnb.dto.response.RegisterResponse;

public interface UserService {

    RegisterResponse singUp(SignUpRequest singUpRequest);

    LoginResponse singIn(LoginRequest singInRequest);



}
