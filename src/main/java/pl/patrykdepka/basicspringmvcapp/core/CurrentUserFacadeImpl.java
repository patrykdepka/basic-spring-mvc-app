package pl.patrykdepka.basicspringmvcapp.core;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.patrykdepka.basicspringmvcapp.appuser.AppUser;
import pl.patrykdepka.basicspringmvcapp.appuser.AppUserNotFoundException;
import pl.patrykdepka.basicspringmvcapp.appuser.AppUserRepository;

@Component
public class CurrentUserFacadeImpl implements CurrentUserFacade {
    private final AppUserRepository appUserRepository;

    public CurrentUserFacadeImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUser getCurrentUser() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentUser == null || currentUser.getPrincipal().equals("anonymousUser")) {
            throw new AccessDeniedException("Access denied");
        }
        return appUserRepository
                .findByEmail(currentUser.getName())
                .orElseThrow(() -> new AppUserNotFoundException(String.format("User with email %s not found", currentUser.getName())));
    }
}