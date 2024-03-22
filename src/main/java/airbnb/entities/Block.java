package airbnb.entities;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blocks")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blocks_seq")
    @SequenceGenerator(name = "blocks_seq", allocationSize = 1)

    private Long id;

    //************************************  User   **************************************
    @ManyToOne
    private User user;

    //************************************  Announcement   **************************************
    @ManyToOne
    private Announcement announcement;
}

