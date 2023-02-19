package pl.patrykdepka.basicspringmvcapp.core;

import pl.patrykdepka.basicspringmvcapp.appuser.AppUser;

public interface CurrentUserFacade {

    AppUser getCurrentUser();
}