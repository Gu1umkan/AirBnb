package airbnb.dto.response;
import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import lombok.Builder;
import java.util.List;
@Builder
public record ForHomeProfile(Long id,
                             List<String> images,
                             String title,
                             String description,
                             int maxOfGuests,
                             String town,
                             String address,
                             HouseType houseType,
                             Region region,
                             ForShortUserInfo user) {
}
