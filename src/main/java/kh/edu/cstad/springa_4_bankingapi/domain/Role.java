package kh.edu.cstad.springa_4_bankingapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 30)
    private String role;

    @ManyToMany(mappedBy = "roles")
//    @JoinTable(name  = "users_roles", inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"))
    private List<User> users;

    @Override
    public String getAuthority() {
        return "ROLE_" + role;
    }
}
