package airbnb.dto.response;

import lombok.Builder;

import java.util.List;
@Builder
public record GetAllUserResponse(
        int page,
        int size,
        List<UserInfoResponse> userInfoResponses
){}
