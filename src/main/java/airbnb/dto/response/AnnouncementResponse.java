package airbnb.dto.response;

import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import jakarta.persistence.ElementCollection;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AnnouncementResponse {
    private Long id;
    private BigDecimal price;
    private String title;
    private String description;
    private int maxOfGuests;
    private String town;
    private String address;
    private Boolean isActive;
    private String rejectAnnouncement;
    private HouseType houseType;
    private Region region;
    private List<String> images;

    public AnnouncementResponse(Long id, BigDecimal price, String title, String description, int maxOfGuests, String town, String address, Boolean isActive, String rejectAnnouncement, HouseType houseType, Region region) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.description = description;
        this.maxOfGuests = maxOfGuests;
        this.town = town;
        this.address = address;
        this.isActive = isActive;
        this.rejectAnnouncement = rejectAnnouncement;
        this.houseType = houseType;
        this.region = region;
    }
}
