package pl.patrykdepka.basicspringmvcapp.appuser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.*;

public interface AppUserService {

    boolean checkIfUserExists(String email);

    void createUser(AppUserRegistrationDTO userRegistration);

    AppUserProfileDTO findUserProfile(AppUser user);

    EditAppUserProfileDTO findUserProfileToEdit(AppUser user);

    EditAppUserProfileDTO updateUserProfile(AppUser user, EditAppUserProfileDTO editUserProfile);

    void updatePassword(AppUser user, String currentPassword, String newPassword);

    Page<AppUserTableAPDTO> findAllUsers(Pageable pageable);

    Page<AppUserTableAPDTO> findUsersBySearch(String searchQuery, Pageable pageable);

    EditAppUserAccountDataDTO findUserAccountDataToEdit(Long id);

    EditAppUserAccountDataDTO updateUserAccountData(Long id, EditAppUserAccountDataDTO userAccountEditAP);

    EditAppUserProfileDTO findUserProfileToEdit(Long id);

    EditAppUserProfileDTO updateUserProfile(Long id, EditAppUserProfileDTO editUserProfileDTO);

    void updatePassword(AppUser user, Long id, String newPassword);

    void deleteUser(AppUser user, Long id);
}
