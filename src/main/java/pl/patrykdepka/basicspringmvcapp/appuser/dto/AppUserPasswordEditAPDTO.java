package pl.patrykdepka.basicspringmvcapp.appuser.dto;

import lombok.Getter;
import lombok.Setter;
import pl.patrykdepka.basicspringmvcapp.appuser.PasswordValueMatch;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@PasswordValueMatch.List({
        @PasswordValueMatch(
                field = "newPassword",
                fieldMatch = "confirmNewPassword"
        )
})
public class AppUserPasswordEditAPDTO {
    @NotNull(message = "{form.field.newPassword.error.notNull.message}")
    @NotEmpty(message = "{form.field.newPassword.error.notEmpty.message}")
    @Size(min = 5, max = 100, message = "{form.field.newPassword.error.size.message}")
    private String newPassword;
    @NotNull(message = "{form.field.confirmNewPassword.error.notNull.message}")
    @NotEmpty(message = "{form.field.confirmNewPassword.error.notEmpty.message}")
    @Size(min = 5, max = 100, message = "{form.field.confirmNewPassword.error.size.message}")
    private String confirmNewPassword;
}