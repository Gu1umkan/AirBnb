package airbnb.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.REMOVE;

@Entity
@Table(name = "feedbacks")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_seq")
    @SequenceGenerator(name = "comments_seq", allocationSize = 1)

    private Long id;
    private String feedback;
    @ElementCollection
    private List<String> images;
    private LocalDate createdAt;
    private LocalDate updateAt;
    private int rating;


    //******************************************  User   ***************************
    @ManyToOne(cascade = DETACH)
    private User user;


    //******************************************  Like   ***************************
    @OneToMany(mappedBy = "feedback",cascade = {DETACH,REMOVE})
    private List<Like> likes;

    //******************************************  Favorite   ************************

    @ManyToOne(cascade = DETACH)
    private Announcement announcement;

}

