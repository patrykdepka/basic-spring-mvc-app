package pl.patrykdepka.basicspringmvcapp.appuser.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class EditAppUserProfileDTO {
    private String firstName;
    private String lastName;
    private MultipartFile profileImage;
    private String bio;
    private String city;

    private EditAppUserProfileDTO() {
    }

    public static class AppUserProfileEditDTOBuilder {
        private String firstName;
        private String lastName;
        private MultipartFile profileImage;
        private String bio;
        private String city;

        public AppUserProfileEditDTOBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AppUserProfileEditDTOBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AppUserProfileEditDTOBuilder withProfileImage(MultipartFile profileImage) {
            this.profileImage = profileImage;
            return this;
        }

        public AppUserProfileEditDTOBuilder withBio(String bio) {
            this.bio = bio;
            return this;
        }

        public AppUserProfileEditDTOBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public EditAppUserProfileDTO build() {
            EditAppUserProfileDTO editUserProfileDTO = new EditAppUserProfileDTO();
            editUserProfileDTO.setFirstName(firstName);
            editUserProfileDTO.setLastName(lastName);
            editUserProfileDTO.setProfileImage(profileImage);
            editUserProfileDTO.setBio(bio);
            editUserProfileDTO.setCity(city);
            return editUserProfileDTO;
        }
    }
}