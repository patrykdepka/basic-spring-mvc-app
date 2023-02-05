package pl.patrykdepka.basicspringmvcapp.appuser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppUserController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "forms/login-form";
    }
}
