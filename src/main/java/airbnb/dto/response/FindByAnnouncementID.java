package airbnb.dto.response;

import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record FindByAnnouncementID(
        String fullName,
        String email,
        String image,
        BigDecimal price,
        String title,
        String description,
        int maxOfGuests,
        String town,
        String address,
        Boolean  isActive,
        String rejectAnnouncement,
        HouseType houseType,
        Region region
) {
}
