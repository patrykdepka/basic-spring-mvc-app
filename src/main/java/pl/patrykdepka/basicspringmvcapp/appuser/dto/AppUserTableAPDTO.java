package pl.patrykdepka.basicspringmvcapp.appuser.dto;

import lombok.Getter;
import lombok.Setter;
import pl.patrykdepka.basicspringmvcapp.appuserrole.AppUserRole;

import java.util.Set;

@Getter
@Setter
public class AppUserTableAPDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled;
    private boolean accountNonLocked;
    private Set<AppUserRole> roles;

    public static class AppUserTableAPDTOBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private boolean enabled;
        private boolean accountNonLocked;
        private Set<AppUserRole> roles;

        public AppUserTableAPDTOBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public AppUserTableAPDTOBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public AppUserTableAPDTOBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AppUserTableAPDTOBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public AppUserTableAPDTOBuilder withEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public AppUserTableAPDTOBuilder withAccountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
            return this;
        }

        public AppUserTableAPDTOBuilder withRoles(Set<AppUserRole> roles) {
            this.roles = roles;
            return this;
        }

        public AppUserTableAPDTO build() {
            AppUserTableAPDTO userTableAPDTO = new AppUserTableAPDTO();
            userTableAPDTO.setId(id);
            userTableAPDTO.setFirstName(firstName);
            userTableAPDTO.setLastName(lastName);
            userTableAPDTO.setEmail(email);
            userTableAPDTO.setEnabled(enabled);
            userTableAPDTO.setAccountNonLocked(accountNonLocked);
            userTableAPDTO.setRoles(roles);
            return userTableAPDTO;
        }
    }
}
