package pl.patrykdepka.basicspringmvcapp.appuser;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserRegistrationDTO;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class AppUserController {
    private final IAppUserService iAppUserService;
    private final MessageSource messageSource;

    public AppUserController(IAppUserService iAppUserService, MessageSource messageSource) {
        this.iAppUserService = iAppUserService;
        this.messageSource = messageSource;
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
    public String register(@Valid @ModelAttribute("userRegistrationDTO") AppUserRegistrationDTO userRegistrationDTO,
                           BindingResult bindingResult) {
        if (iAppUserService.checkIfUserExists(userRegistrationDTO.getEmail())) {
            bindingResult.addError(new FieldError("userRegistrationDTO", "email", messageSource.getMessage("form.field.email.error.emailIsInUse.message", null, Locale.getDefault())));
        }
        if (bindingResult.hasErrors()) {
            return "forms/registration-form";
        } else {
            iAppUserService.createUser(userRegistrationDTO);
            return "redirect:/confirmation";
        }
    }

    @GetMapping("/confirmation")
    public String registrationConfirmation() {
        return "registration-confirmation";
    }

    @GetMapping("/admin-panel/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", iAppUserService.findAllUsers());
        return "admin/app-users-table";
    }
}
