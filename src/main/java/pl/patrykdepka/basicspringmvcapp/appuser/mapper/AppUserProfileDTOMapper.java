package pl.patrykdepka.basicspringmvcapp.appuser.mapper;

import pl.patrykdepka.basicspringmvcapp.appuser.AppUser;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserProfileDTO;

import java.util.Base64;

public class AppUserProfileDTOMapper {

    private AppUserProfileDTOMapper() {
    }

    public static AppUserProfileDTO mapToAppUserProfileDTO(AppUser user) {
        return new AppUserProfileDTO.AppUserProfileDTOBuilder()
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withProfileImageType(user.getProfileImage().getFileType())
                .withProfileImageData(Base64.getEncoder().encodeToString(user.getProfileImage().getFileData()))
                .withEmail(user.getEmail())
                .withBio(user.getBio())
                .withCity(user.getCity())
                .build();
    }
}