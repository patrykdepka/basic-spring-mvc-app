package pl.patrykdepka.basicspringmvcapp.appuserdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.patrykdepka.basicspringmvcapp.appuser.AppUser;
import pl.patrykdepka.basicspringmvcapp.appuser.AppUserRepository;
import pl.patrykdepka.basicspringmvcapp.appuserrole.AppUserRole;

import java.util.Set;

@Component
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    public AppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(username)
                .map(this::createAppUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", username)));
    }

    private UserDetails createAppUserDetails(AppUser user) {
        return new AppUserDetails.AppUserDetailsBuilder()
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withUsername(user.getEmail())
                .withPassword(user.getPassword())
                .withEnabled(user.isEnabled())
                .withAccountNonLocked(user.isAccountNonLocked())
                .withRoles(getUserRolesArray(user.getRoles()))
                .build();
    }

    private String[] getUserRolesArray(Set<AppUserRole> roles) {
        return roles
                .stream()
                .map(AppUserRole::getName)
                .toArray(String[]::new);
    }
}
