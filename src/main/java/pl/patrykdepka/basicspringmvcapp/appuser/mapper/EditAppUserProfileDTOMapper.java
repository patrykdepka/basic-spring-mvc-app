package pl.patrykdepka.basicspringmvcapp.appuser.mapper;

import pl.patrykdepka.basicspringmvcapp.appuser.AppUser;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.EditAppUserProfileDTO;

public class EditAppUserProfileDTOMapper {

    private EditAppUserProfileDTOMapper() {
    }

    public static EditAppUserProfileDTO mapToEditAppUserProfileDTO(AppUser user) {
        return new EditAppUserProfileDTO.AppUserProfileEditDTOBuilder()
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withBio(user.getBio())
                .withCity(user.getCity())
                .build();
    }
}