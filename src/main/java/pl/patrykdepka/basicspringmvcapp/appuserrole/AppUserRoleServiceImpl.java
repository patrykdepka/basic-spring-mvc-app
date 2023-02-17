package pl.patrykdepka.basicspringmvcapp.appuserrole;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AppUserRoleServiceImpl implements AppUserRoleService {
    private final AppUserRoleRepository appUserRoleRepository;

    public AppUserRoleServiceImpl(AppUserRoleRepository appUserRoleRepository) {
        this.appUserRoleRepository = appUserRoleRepository;
    }

    public Set<AppUserRole> findAllUserRoles() {
        Iterable<AppUserRole> userRoles = appUserRoleRepository.findAll();
        Set<AppUserRole> userRolesSet = new HashSet<>();
        for (AppUserRole userRole : userRoles) {
            userRolesSet.add(userRole);
        }
        return userRolesSet;
    }
}
