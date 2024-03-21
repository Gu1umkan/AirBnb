package airbnb.entities;

import jakarta.persistence.*;
import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.REMOVE;

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
    private int MaxOfGuests;
    private String town;
    private String address;

    @ElementCollection
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

    //************************************  Like  **************************************
    @OneToMany(mappedBy = "announcement",cascade = {DETACH,REMOVE})
    private List<Like> likes;

    //************************************  Rating  **************************************
    @OneToMany(mappedBy = "announcement",cascade = {DETACH,REMOVE})
    private List<Rating> ratings;

    //************************************  Favorites  **************************************
    @OneToMany(mappedBy = "announcement",cascade = {DETACH,REMOVE})
    private List<Favorite> favorites;

    //************************************  Booking  **************************************

    @OneToMany(mappedBy = "announcement",cascade = {DETACH,REMOVE})
    private List<Booking> bookings;


}

