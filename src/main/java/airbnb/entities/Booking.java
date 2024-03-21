package airbnb.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static jakarta.persistence.CascadeType.DETACH;

@Entity
@Table(name = "bookings")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookings_seq")
    @SequenceGenerator(name = "bookings_seq", allocationSize = 1)

    private Long id;
    private LocalDate check_In;
    private LocalDate check_Out;
    private BigDecimal  totalPrice;

    //************************************  User   **************************************
    @OneToOne(cascade = DETACH)
    private User user;

    //************************************  Announcement   **************************************
    @ManyToOne(cascade = DETACH)
    private Announcement announcement;

    //************************************  User   **************************************

}

