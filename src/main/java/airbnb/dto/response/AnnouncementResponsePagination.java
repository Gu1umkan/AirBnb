package airbnb.dto.response;

import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AnnouncementResponsePagination(
        int page,
        int size,
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
