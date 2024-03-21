package airbnb.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.DETACH;

@Entity
@Table(name = "favorites")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favorites_seq")
    @SequenceGenerator(name = "favorites_seq", allocationSize = 1)

    private Long id;
    private LocalDate createAt;
    private LocalDate updateAt;

    //******************************************  User   ***************************
    @OneToOne(cascade = DETACH)
    private User user;


    //******************************************  Announcement  ***************************
    @ManyToOne(cascade = DETACH)
    private Announcement announcement;

}

