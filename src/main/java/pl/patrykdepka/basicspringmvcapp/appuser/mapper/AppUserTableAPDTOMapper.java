package pl.patrykdepka.basicspringmvcapp.appuser.mapper;

import pl.patrykdepka.basicspringmvcapp.appuser.AppUser;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserTableAPDTO;

import java.util.List;
import java.util.stream.Collectors;

public class AppUserTableAPDTOMapper {

    private AppUserTableAPDTOMapper() {
    }

    public static List<AppUserTableAPDTO> mapToAppUserTableAPDTOs(List<AppUser> users) {
        return users.stream().map(AppUserTableAPDTOMapper::mapToAppUserTableAPDTO).collect(Collectors.toList());
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
