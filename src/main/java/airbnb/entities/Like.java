package airbnb.entities;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.DETACH;

@Entity
@Table(name = "likes")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "likes_seq")
    @SequenceGenerator(name = "likes_seq", allocationSize = 1)

    private Long id;
    private Boolean isLike;
    private Boolean dislike;

    //********************************** User  *************************
    @ManyToOne(cascade = DETACH)
    private User user;

    //********************************** Comment  *************************
    @ManyToOne(cascade = DETACH)
    private Feedback feedback;

}

