package airbnb.dto.response;

import lombok.Builder;

import java.util.List;
@Builder
public record ForUserProfileWithBookings(
        long id,
        String image,
        String fullName,
        String email,
        List<HomeResponse> houses

) {
}
