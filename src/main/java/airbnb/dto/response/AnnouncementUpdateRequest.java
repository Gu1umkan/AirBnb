package airbnb.dto.response;

import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import airbnb.validation.AnnouncementPrice.AnnouncementPriceValidation;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AnnouncementUpdateRequest {

    private BigDecimal price;
    private String title;
    private String description;
    private int maxOfGuests;
    private String town;
    @Column(unique = true)
    private String address;
    @ElementCollection
    private List<String> images;
    @Enumerated(EnumType.STRING)
    private HouseType houseType;
    @Enumerated(EnumType.STRING)
    private Region region;


}
