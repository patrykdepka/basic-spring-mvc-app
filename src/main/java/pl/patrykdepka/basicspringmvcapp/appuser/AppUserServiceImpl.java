package pl.patrykdepka.basicspringmvcapp.appuser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.*;
import pl.patrykdepka.basicspringmvcapp.appuser.mapper.AppUserProfileDTOMapper;
import pl.patrykdepka.basicspringmvcapp.appuser.mapper.AppUserTableAPDTOMapper;
import pl.patrykdepka.basicspringmvcapp.appuser.mapper.EditAppUserAccountDataDTOMapper;
import pl.patrykdepka.basicspringmvcapp.appuser.mapper.EditAppUserProfileDTOMapper;
import pl.patrykdepka.basicspringmvcapp.appuserdetails.AppUserDetailsService;
import pl.patrykdepka.basicspringmvcapp.appuserrole.AppUserRole;
import pl.patrykdepka.basicspringmvcapp.appuserrole.AppUserRoleRepository;
import pl.patrykdepka.basicspringmvcapp.profileimage.ProfileImage;
import pl.patrykdepka.basicspringmvcapp.profileimage.ProfileImageService;

import java.util.Optional;
import java.util.Set;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final Log log = LogFactory.getLog(this.getClass());
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileImageService profileImageService;
    private final AppUserRoleRepository appUserRoleRepository;
    private final AppUserDetailsService appUserDetailsService;

    public AppUserServiceImpl(AppUserRepository appUserRepository,
                              PasswordEncoder passwordEncoder,
                              ProfileImageService profileImageService,
                              AppUserRoleRepository appUserRoleRepository,
                              AppUserDetailsService appUserDetailsService
    ) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileImageService = profileImageService;
        this.appUserRoleRepository = appUserRoleRepository;
        this.appUserDetailsService = appUserDetailsService;
    }

    public boolean checkIfUserExists(String email) {
        return appUserRepository.findByEmail(email.toLowerCase()).isPresent();
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

    public AppUserProfileDTO findUserProfile(AppUser user) {
        return AppUserProfileDTOMapper.mapToAppUserProfileDTO(user);
    }

    public EditAppUserProfileDTO findUserProfileToEdit(AppUser user) {
        return EditAppUserProfileDTOMapper.mapToEditAppUserProfileDTO(user);
    }

    @Transactional
    public EditAppUserProfileDTO updateUserProfile(AppUser user, EditAppUserProfileDTO editUserProfile) {
        return EditAppUserProfileDTOMapper.mapToEditAppUserProfileDTO(setUserProfileFields(editUserProfile, user, false));
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

    public EditAppUserProfileDTO findUserProfileToEdit(Long id) {
        return appUserRepository
                .findById(id)
                .map(EditAppUserProfileDTOMapper::mapToEditAppUserProfileDTO)
                .orElseThrow(() -> new AppUserNotFoundException("User with ID " + id + " not found"));
    }

    @Transactional
    public EditAppUserProfileDTO updateUserProfile(Long id, EditAppUserProfileDTO editUserProfileDTO) {
        return appUserRepository
                .findById(id)
                .map(target -> setUserProfileFields(editUserProfileDTO, target, true))
                .map(EditAppUserProfileDTOMapper::mapToEditAppUserProfileDTO)
                .orElseThrow(() -> new AppUserNotFoundException("User with ID " + id + " not found"));
    }

    @Transactional
    public void updatePassword(AppUser user, Long id, String newPassword) {
        if (!isCurrentUserAdmin(user.getRoles())) {
            throw new AccessDeniedException("Access denied");
        }
        appUserRepository
                .findById(id)
                .ifPresentOrElse(
                        userToEdit -> {
                            log.debug(LogMessage.format("Changing password for user '%s'", userToEdit.getEmail()));
                            userToEdit.setPassword(passwordEncoder.encode(newPassword));
                        },
                        () -> {
                            throw new AppUserNotFoundException(String.format("User with ID %s not found", id));
                        }
                );
    }

    private AppUser setUserProfileFields(EditAppUserProfileDTO source, AppUser target, boolean isEditByAdmin) {
        boolean isAppUserDetailsEdited = false;

        if (source.getFirstName() != null && !source.getFirstName().equals(target.getFirstName())) {
            target.setFirstName(source.getFirstName());
            isAppUserDetailsEdited = true;
        }
        if (source.getLastName() != null && !source.getLastName().equals(target.getLastName())) {
            target.setLastName(source.getLastName());
            isAppUserDetailsEdited = true;
        }
        if (source.getProfileImage() != null && !source.getProfileImage().isEmpty()) {
            Optional<ProfileImage> profileImage = profileImageService.updateProfileImage(target, source.getProfileImage());
            if (profileImage.isPresent()) {
                target.setProfileImage(profileImage.get());
                isAppUserDetailsEdited = true;
            }
        }
        if (source.getBio() != null && !source.getBio().equals(target.getBio())) {
            target.setBio(source.getBio());
        }
        if (source.getCity() != null && !source.getCity().equals(target.getCity())) {
            target.setCity(source.getCity());
        }

        if (isAppUserDetailsEdited && !isEditByAdmin) {
            appUserDetailsService.updateAppUserDetails(target);
        }

        return target;
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

    private boolean isCurrentUserAdmin(Set<AppUserRole> roles) {
        return roles.stream().anyMatch(role -> role.getName().equals(ADMIN_ROLE));
    }
}
