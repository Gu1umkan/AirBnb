package airbnb.dto.response;

import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record FindByAnnouncementID(
        Long id,
        BigDecimal price,
        String title,
        String description,
        int maxOfGuests,
        String town,
        String address,
        boolean myAnnouncementOrNot,
        SecondBookingResponse bookedOrNot,
        HouseType houseType,
        Region region,

        List<FeedbackResponse> feedbacks
) {
}
