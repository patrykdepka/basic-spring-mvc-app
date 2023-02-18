package pl.patrykdepka.basicspringmvcapp.appuser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserRegistrationDTO;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserTableAPDTO;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.EditAppUserAccountDataDTO;
import pl.patrykdepka.basicspringmvcapp.appuser.mapper.AppUserTableAPDTOMapper;
import pl.patrykdepka.basicspringmvcapp.appuser.mapper.EditAppUserAccountDataDTOMapper;
import pl.patrykdepka.basicspringmvcapp.appuserrole.AppUserRole;
import pl.patrykdepka.basicspringmvcapp.appuserrole.AppUserRoleRepository;
import pl.patrykdepka.basicspringmvcapp.profileimage.ProfileImageService;

import java.util.Optional;
import java.util.Set;

@Service
public class AppUserServiceImpl implements AppUserService {
    private static final String USER_ROLE = "USER";
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileImageService profileImageService;
    private final AppUserRoleRepository appUserRoleRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository,
                              PasswordEncoder passwordEncoder,
                              ProfileImageService profileImageService,
                              AppUserRoleRepository appUserRoleRepository
    ) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileImageService = profileImageService;
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
        user.setProfileImage(profileImageService.createDefaultProfileImage());
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

    public Page<AppUserTableAPDTO> findAllUsers(Pageable pageable) {
        return AppUserTableAPDTOMapper.mapToAppUserTableAPDTOs(appUserRepository.findAllUsers(pageable));
    }

    public Page<AppUserTableAPDTO> findUsersBySearch(String searchQuery, Pageable pageable) {
        searchQuery = searchQuery.toLowerCase();
        String[] searchWords = searchQuery.split(" ");

        if (searchWords.length == 1 && !"".equals(searchWords[0])) {
            return AppUserTableAPDTOMapper
                    .mapToAppUserTableAPDTOs(appUserRepository.findAll(AppUserSpecification.bySearch(searchWords[0]), pageable));
        }
        if (searchWords.length == 2) {
            return AppUserTableAPDTOMapper
                    .mapToAppUserTableAPDTOs(appUserRepository.findAll(AppUserSpecification.bySearch(searchWords[0], searchWords[1]), pageable));
        }

        return Page.empty();
    }

    public EditAppUserAccountDataDTO findUserAccountDataToEdit(Long id) {
        return appUserRepository
                .findById(id)
                .map(EditAppUserAccountDataDTOMapper::mapToEditAppUserAccountDataDTO)
                .orElseThrow(() -> new AppUserNotFoundException("User with ID " + id + " not found"));
    }

    @Transactional
    public EditAppUserAccountDataDTO updateUserAccountData(Long id, EditAppUserAccountDataDTO userAccountEditAP) {
        return appUserRepository
                .findById(id)
                .map(target -> setUserAccountFields(userAccountEditAP, target))
                .map(EditAppUserAccountDataDTOMapper::mapToEditAppUserAccountDataDTO)
                .orElseThrow(() -> new AppUserNotFoundException("User with ID " + id + " not found"));
    }

    private AppUser setUserAccountFields(EditAppUserAccountDataDTO source, AppUser target) {
        if (source.isEnabled() != target.isEnabled()) {
            target.setEnabled(source.isEnabled());
        }
        if (source.isAccountNonLocked() != target.isAccountNonLocked()) {
            target.setAccountNonLocked(source.isAccountNonLocked());
        }
        for (AppUserRole appUserRole : target.getRoles()) {
            if (source.getRoleId() != appUserRole.getId()) {
                Optional<AppUserRole> userRoleOpt = appUserRoleRepository.findById(source.getRoleId());
                userRoleOpt.ifPresentOrElse(
                        userRole -> target.setRoles(Set.of(userRole)),
                        () -> {
                            throw new NoSuchRoleException(String.format("Role with name %s not found", USER_ROLE));
                        }
                );
            }
        }

        return target;
    }
}
