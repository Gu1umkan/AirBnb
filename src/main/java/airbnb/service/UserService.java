package airbnb.service;

import airbnb.dto.request.LoginRequest;
import airbnb.dto.request.SignUpRequest;
import airbnb.dto.response.GetAllUserResponse;
import airbnb.dto.response.LoginResponse;
import airbnb.dto.response.SimpleResponse;
import airbnb.dto.response.UserInfoResponse;
import airbnb.entities.User;

import java.util.List;

public interface UserService {

    SimpleResponse singUp(SignUpRequest singUpRequest);

    LoginResponse singIn(LoginRequest singInRequest);


    GetAllUserResponse getAll(int page, int size);

    UserInfoResponse findById();

    SimpleResponse deleteById(Long id);

    UserInfoResponse updateById(SignUpRequest signUpRequest);


    SimpleResponse assignUser(Long announcementId);
}
