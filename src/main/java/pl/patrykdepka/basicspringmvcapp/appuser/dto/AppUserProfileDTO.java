package pl.patrykdepka.basicspringmvcapp.appuser.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserProfileDTO {
    private String firstName;
    private String lastName;
    private String profileImageType;
    private String profileImageData;
    private String email;
    private String bio;
    private String city;

    private AppUserProfileDTO() {
    }

    public static class AppUserProfileDTOBuilder {
        private String firstName;
        private String lastName;
        private String profileImageType;
        private String profileImageData;
        private String email;
        private String bio;
        private String city;

        public AppUserProfileDTOBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AppUserProfileDTOBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AppUserProfileDTOBuilder withProfileImageType(String profileImageType) {
            this.profileImageType = profileImageType;
            return this;
        }

        public AppUserProfileDTOBuilder withProfileImageData(String profileImageData) {
            this.profileImageData = profileImageData;
            return this;
        }

        public AppUserProfileDTOBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public AppUserProfileDTOBuilder withBio(String bio) {
            this.bio = bio;
            return this;
        }

        public AppUserProfileDTOBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public AppUserProfileDTO build() {
            AppUserProfileDTO userProfileDTO = new AppUserProfileDTO();
            userProfileDTO.setFirstName(firstName);
            userProfileDTO.setLastName(lastName);
            userProfileDTO.setProfileImageType(profileImageType);
            userProfileDTO.setProfileImageData(profileImageData);
            userProfileDTO.setEmail(email);
            userProfileDTO.setBio(bio);
            userProfileDTO.setCity(city);
            return userProfileDTO;
        }
    }
}