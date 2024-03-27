package airbnb.dto.response;

import lombok.Builder;

@Builder
public record FeedbackUserResponse(
        Long id,
        String fullName,
        String image
) {
}
