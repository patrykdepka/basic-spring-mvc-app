package pl.patrykdepka.basicspringmvcapp.appuser;

import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserRegistrationDTO;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserTableAPDTO;

import java.util.List;

public interface IAppUserService {

    boolean checkIfUserExists(String email);

    void createUser(AppUserRegistrationDTO userRegistration);

    List<AppUserTableAPDTO> findAllUsers();
}
