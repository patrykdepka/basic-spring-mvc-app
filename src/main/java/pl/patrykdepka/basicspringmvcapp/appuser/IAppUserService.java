package pl.patrykdepka.basicspringmvcapp.appuser;

import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserRegistrationDTO;

public interface IAppUserService {

    void createUser(AppUserRegistrationDTO userRegistration);
}
