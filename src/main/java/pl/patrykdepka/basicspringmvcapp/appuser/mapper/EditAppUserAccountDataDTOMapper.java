package pl.patrykdepka.basicspringmvcapp.appuser.mapper;

import pl.patrykdepka.basicspringmvcapp.appuser.AppUser;
import pl.patrykdepka.basicspringmvcapp.appuser.NoSuchRoleException;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.EditAppUserAccountDataDTO;
import pl.patrykdepka.basicspringmvcapp.appuserrole.AppUserRole;

import java.util.Optional;

public class EditAppUserAccountDataDTOMapper {

    private EditAppUserAccountDataDTOMapper() {
    }

    public static EditAppUserAccountDataDTO mapToEditAppUserAccountDataDTO(AppUser user) {
        Optional<AppUserRole> userRoleOpt = user.getRoles().stream().findFirst();
        if (userRoleOpt.isEmpty()) {
            throw new NoSuchRoleException("User has no any role assigned");
        }
        return new EditAppUserAccountDataDTO.EditAppUserAccountDataDTOBuilder()
                .withEnabled(user.isEnabled())
                .withAccountNonLocked(user.isAccountNonLocked())
                .withRoleId(userRoleOpt.get().getId())
                .build();
    }
}
