package pl.patrykdepka.basicspringmvcapp.appuser;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserRegistrationDTO;
import pl.patrykdepka.basicspringmvcapp.appuserrole.AppUserRole;
import pl.patrykdepka.basicspringmvcapp.appuserrole.AppUserRoleRepository;

import java.util.Optional;

@Service
public class AppUserService implements IAppUserService {
    private static final String USER_ROLE = "USER";
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppUserRoleRepository appUserRoleRepository;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, AppUserRoleRepository appUserRoleRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUserRoleRepository = appUserRoleRepository;
    }

    public boolean checkIfUserExists(String email) {
        return appUserRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public void createUser(AppUserRegistrationDTO userRegistration) {
        AppUser user = new AppUser();
        user.setFirstName(userRegistration.getFirstName());
        user.setLastName(userRegistration.getLastName());
        user.setEmail(userRegistration.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        Optional<AppUserRole> userRole = appUserRoleRepository.findRoleByName(USER_ROLE);
        userRole.ifPresentOrElse(
                role -> user.getRoles().add(role),
                () -> {
                    throw new NoSuchRoleException(String.format("Role with name %s not found", USER_ROLE));
                }
        );
        appUserRepository.save(user);
    }
}
