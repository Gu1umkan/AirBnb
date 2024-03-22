package airbnb.dto.request;

import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Builder
public class AnnouncementRequest {
    @NotNull(message = " null")
    private BigDecimal price;
    private String title;
    private String description;
    private int MaxOfGuests;
    private String town;
    private String address;
    private Boolean  isActive;
    private String rejectAnnouncement;
    @ElementCollection
    private List<String> images;
    @Enumerated(EnumType.STRING)
    private HouseType houseType;
    @Enumerated(EnumType.STRING)
    private Region region;
    private int rating;

}
