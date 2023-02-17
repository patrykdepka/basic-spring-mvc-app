package pl.patrykdepka.basicspringmvcapp.appuser.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditAppUserAccountDataDTO {
    private boolean enabled;
    private boolean accountNonLocked;
    private Long roleId;

    public static class EditAppUserAccountDataDTOBuilder {
        private boolean enabled;
        private boolean accountNonLocked;
        private Long roleId;

        public EditAppUserAccountDataDTOBuilder withEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public EditAppUserAccountDataDTOBuilder withAccountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
            return this;
        }

        public EditAppUserAccountDataDTOBuilder withRoleId(Long roleId) {
            this.roleId = roleId;
            return this;
        }

        public EditAppUserAccountDataDTO build() {
            EditAppUserAccountDataDTO userAccountEditAP = new EditAppUserAccountDataDTO();
            userAccountEditAP.setEnabled(enabled);
            userAccountEditAP.setAccountNonLocked(accountNonLocked);
            userAccountEditAP.setRoleId(roleId);
            return userAccountEditAP;
        }
    }
}
