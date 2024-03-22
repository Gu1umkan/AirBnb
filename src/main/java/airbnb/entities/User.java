package airbnb.entities;


import jakarta.persistence.*;
import airbnb.entities.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.REMOVE;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", allocationSize = 1)

    private  Long id;
    private String fullName;
    private String  image;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    private BigDecimal money;
    private LocalDate createdAt;
    private LocalDate updatedAt;



    //************************************  Like   **************************************
    @OneToMany(mappedBy = "user",cascade = {DETACH,REMOVE})
    private List<Like> likes;

    //************************************  Comment   **************************************
    @OneToMany(mappedBy = "user",cascade = {DETACH,REMOVE})
    private List<Feedback> feedbacks;

    //************************************  Block   **************************************
    @OneToOne(cascade = {DETACH,REMOVE})
    private Block block;


    //************************************  Announcement   **************************************
    @OneToMany(mappedBy = "user",cascade = {DETACH,REMOVE})
    private List<Announcement> announcements;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

