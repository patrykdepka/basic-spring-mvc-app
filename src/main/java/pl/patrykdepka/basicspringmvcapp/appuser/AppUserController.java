package pl.patrykdepka.basicspringmvcapp.appuser;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserRegistrationDTO;

@Controller
public class AppUserController {
    private final IAppUserService iAppUserService;

    public AppUserController(IAppUserService iAppUserService) {
        this.iAppUserService = iAppUserService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "forms/login-form";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userRegistrationDTO", new AppUserRegistrationDTO());
        return "forms/registration-form";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userRegistrationDTO") AppUserRegistrationDTO userRegistrationDTO) {
        iAppUserService.createUser(userRegistrationDTO);
        return "redirect:/confirmation";
    }

    @GetMapping("/confirmation")
    public String registrationConfirmation() {
        return "registration-confirmation";
    }
}
