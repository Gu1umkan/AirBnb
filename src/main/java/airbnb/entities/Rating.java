package airbnb.entities;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.DETACH;

@Entity
@Table(name = "ratings")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rating_seq")
    @SequenceGenerator(name = "ratings_seq", allocationSize = 1)

    private Long id;
    private int countOfStar;

    //************************************  User   **************************************
    @ManyToOne(cascade = DETACH)
    private User user;

    //************************************  Announcement   **************************************
    @ManyToOne(cascade = DETACH)
    private Announcement announcement;
}

