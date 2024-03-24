package airbnb.dto.response;

import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record HomeResponse(Long id,
                           List<String> images,
                           BigDecimal price,
                           String title,
                           String description,
                           int maxOfGuests,
                           String town,
                           String address,
                           float rating,
                           Boolean  isActive,
                           String rejectAnnouncement,
                           HouseType houseType,
                           Region region) {

}
