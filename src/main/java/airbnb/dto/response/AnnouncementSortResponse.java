package airbnb.dto.response;

import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import lombok.Builder;


import java.math.BigDecimal;
import java.util.List;


@Builder
public record AnnouncementSortResponse(

        Long id,
        BigDecimal price,
        String title,
        String description,
        int maxOfGuests,
        String town,
        String address,
        Boolean isActive,
        String rejectAnnouncement,
        List<String> images,
        HouseType houseType,
        Region region
){

}
