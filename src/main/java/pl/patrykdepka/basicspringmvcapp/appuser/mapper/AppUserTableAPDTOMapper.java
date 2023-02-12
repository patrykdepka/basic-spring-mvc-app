package pl.patrykdepka.basicspringmvcapp.appuser.mapper;

import org.springframework.data.domain.Page;
import pl.patrykdepka.basicspringmvcapp.appuser.AppUser;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserTableAPDTO;

public class AppUserTableAPDTOMapper {

    private AppUserTableAPDTOMapper() {
    }

    public static Page<AppUserTableAPDTO> mapToAppUserTableAPDTOs(Page<AppUser> users) {
        return users.map(AppUserTableAPDTOMapper::mapToAppUserTableAPDTO);
    }

    private static AppUserTableAPDTO mapToAppUserTableAPDTO(AppUser user) {
        return new AppUserTableAPDTO.AppUserTableAPDTOBuilder()
                .withId(user.getId())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withEmail(user.getEmail())
                .withEnabled(user.isEnabled())
                .withAccountNonLocked(user.isAccountNonLocked())
                .withRoles(user.getRoles())
                .build();
    }
}
