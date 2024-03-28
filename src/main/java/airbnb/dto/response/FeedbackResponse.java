package airbnb.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record FeedbackResponse(
        Long id,
        boolean myOrNot,
        String feedback,
        List<String> images,
        int rating,
        FeedbackUserResponse user,
        LikeResponse likes,
        LocalDate createdAt,
        LocalDate updatedAt) {
}
