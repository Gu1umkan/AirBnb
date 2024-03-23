package airbnb.dto.response;

import lombok.Builder;

@Builder
public record LikeResponse(
   int countOfLike,
   int countOfDislike
) {
}
