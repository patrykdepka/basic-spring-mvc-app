package pl.patrykdepka.basicspringmvcapp.core;

import pl.patrykdepka.basicspringmvcapp.appuser.AppUser;

import java.util.Optional;

public interface CurrentUserFacade {

    AppUser getCurrentUser();

    Optional<AppUser> getCurrentUser2();
}