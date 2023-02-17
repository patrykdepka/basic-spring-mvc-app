package pl.patrykdepka.basicspringmvcapp.appuser.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EditAppUserAccountDataDTO {
    @NotNull(message = "{form.field.someField.error.notNull.message}")
    private boolean enabled;
    @NotNull(message = "{form.field.someField.error.notNull.message}")
    private boolean accountNonLocked;
    @NotNull(message = "{form.field.someField.error.notNull.message}")
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
