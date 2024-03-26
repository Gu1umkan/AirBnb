package airbnb.dto.request;

import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import airbnb.validation.AnnouncementPrice.AnnouncementPriceValidation;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
@Builder
public record AnnouncementRequest(
        @AnnouncementPriceValidation
    BigDecimal price,
    @NotBlank
    String title,
        @NotBlank
    String description,

    int maxOfGuests,
        @NotBlank
    String town,
        @Column(unique = true)  @NotBlank
    String address,
        @NotBlank
    List<String> images,
        @NotBlank
    HouseType houseType,
        @NotBlank
    Region region
    ){
}