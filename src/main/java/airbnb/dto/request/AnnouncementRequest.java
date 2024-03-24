package airbnb.dto.request;

import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record AnnouncementRequest(
    @NotNull(message = "not null")
    BigDecimal price,
    @NotNull(message = "not null")
    String title,
    @NotNull(message = "not null")
    String description,
    @NotNull(message = "not null")
    int maxOfGuests,
    @NotNull(message = "not null")
    String town,
    @NotNull(message = "not null")
    String address,
    @NotNull(message = "not null")
    List<String> images,
    @NotNull(message = "not null")
    HouseType houseType,
    @NotNull(message = "not null")
    Region region
    ){
}