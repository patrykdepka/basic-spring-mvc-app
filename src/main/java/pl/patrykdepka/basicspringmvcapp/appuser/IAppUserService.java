package pl.patrykdepka.basicspringmvcapp.appuser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserRegistrationDTO;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserTableAPDTO;

public interface IAppUserService {

    boolean checkIfUserExists(String email);

    void createUser(AppUserRegistrationDTO userRegistration);

    Page<AppUserTableAPDTO> findAllUsers(Pageable pageable);

    Page<AppUserTableAPDTO> findUsersBySearch(String searchQuery, Pageable pageable);
}
