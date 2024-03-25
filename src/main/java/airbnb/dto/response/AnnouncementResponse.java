package airbnb.dto.response;

import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import jakarta.persistence.ElementCollection;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Builder
public record AnnouncementResponse(
        Long id,
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
){
}
