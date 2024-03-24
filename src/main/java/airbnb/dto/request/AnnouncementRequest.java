package airbnb.dto.request;

import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.util.List;

public record AnnouncementRequest(BigDecimal price,
                                  String title,
                                  String description,
                                  int maxOfGuests,
                                  String town,
                                  String address,
                                  List<String> images,
                                  HouseType houseType,
                                  Region region) {
}