package pl.patrykdepka.basicspringmvcapp.appuser.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserRegistrationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
