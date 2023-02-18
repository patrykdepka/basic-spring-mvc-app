package pl.patrykdepka.basicspringmvcapp.appuser;

import lombok.Getter;
import lombok.Setter;
import pl.patrykdepka.basicspringmvcapp.appuserrole.AppUserRole;
import pl.patrykdepka.basicspringmvcapp.profileimage.ProfileImage;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToOne
    @JoinTable(
            name = "user_profile_image",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "profile_image_id", referencedColumnName = "id")
    )
    private ProfileImage profileImage;
    private boolean enabled;
    private boolean accountNonLocked;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "app_user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<AppUserRole> roles = new HashSet<>();
}
