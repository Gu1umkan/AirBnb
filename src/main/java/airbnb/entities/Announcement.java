package airbnb.entities;

import jakarta.persistence.*;
import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "announcements")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "announcements_seq")
    @SequenceGenerator(name = "announcements_seq", allocationSize = 1)

    private Long id;
    private BigDecimal price;
    private String title;
    private String description;
    private int maxOfGuests;
    private String town;
    private String address;
    private Boolean  isActive;
    private String rejectAnnouncement;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;

    @Enumerated(EnumType.STRING)
    private HouseType houseType;

    @Enumerated(EnumType.STRING)
    private Region region;

    //************************************  User   **************************************
    @ManyToOne(cascade = DETACH)
    private User user;

    //************************************  Comment  **************************************
    @OneToMany(mappedBy = "announcement",cascade = {DETACH,REMOVE})
    private List<Feedback> feedbacks;

    //************************************  Favorites  **************************************
    @OneToMany(mappedBy = "announcement",cascade = {DETACH,REMOVE})
    private List<Favorite> favorites;

    //************************************  Booking  **************************************

    @OneToMany(mappedBy = "announcement",cascade = {PERSIST,DETACH})
    private List<Booking> bookings;
}

