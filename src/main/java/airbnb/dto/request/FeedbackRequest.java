package airbnb.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record FeedbackRequest(
        @NotNull
        List<String> images,
        @NotNull
        String feedback,
        @NotNull
        int rating) {
}
