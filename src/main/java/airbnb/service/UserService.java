package airbnb.service;

import airbnb.dto.request.LoginRequest;
import airbnb.dto.request.SignUpRequest;
import airbnb.dto.response.LoginResponse;
import airbnb.dto.response.RegisterResponse;
import airbnb.dto.response.SimpleResponse;

public interface UserService {

    SimpleResponse singUp(SignUpRequest singUpRequest);

    LoginResponse singIn(LoginRequest singInRequest);



}
