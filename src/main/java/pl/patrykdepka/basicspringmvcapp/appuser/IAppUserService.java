package pl.patrykdepka.basicspringmvcapp.appuser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserRegistrationDTO;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserTableAPDTO;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.EditAppUserAccountDataDTO;

public interface IAppUserService {

    boolean checkIfUserExists(String email);

    void createUser(AppUserRegistrationDTO userRegistration);

    Page<AppUserTableAPDTO> findAllUsers(Pageable pageable);

    Page<AppUserTableAPDTO> findUsersBySearch(String searchQuery, Pageable pageable);

    EditAppUserAccountDataDTO findUserAccountDataToEdit(Long id);

    EditAppUserAccountDataDTO updateUserAccountData(Long id, EditAppUserAccountDataDTO userAccountEditAP);
}
