package airbnb.dto.response;

import lombok.Builder;

@Builder
public record ForShortUserInfo(
        long id,
        String fullName,
        String gmail,
        String photo
) {
}
