package airbnb.dto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record BookingFeedbackResponse(
        Long id,
        String fullName,
        String image,
        String feedback,
        List<String> images,
        LocalDate createdAt,
        int rating
) {
}
