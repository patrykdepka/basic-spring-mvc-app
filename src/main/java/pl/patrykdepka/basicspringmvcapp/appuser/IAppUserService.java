package pl.patrykdepka.basicspringmvcapp.appuser;

import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserRegistrationDTO;

public interface IAppUserService {

    boolean checkIfUserExists(String email);

    void createUser(AppUserRegistrationDTO userRegistration);
}
