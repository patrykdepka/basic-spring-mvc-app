package pl.patrykdepka.basicspringmvcapp.appuser;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserRegistrationDTO;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserTableAPDTO;

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
    public String getAllUsers(@RequestParam(name = "page", required = false) Integer pageNumber, Model model) {
        int page = pageNumber != null ? pageNumber : 1;

        if (page > 0) {
            PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.fromString("asc"), "lastName"));
            Page<AppUserTableAPDTO> users = iAppUserService.findAllUsers(pageRequest);

            if (page <= users.getTotalPages()) {
                model.addAttribute("users", users);
                model.addAttribute("prefixUrl", "/admin-panel/users?");
                return "admin/app-users-table";
            } else {
                return "redirect:/admin-panel/users?page=" + users.getTotalPages();
            }
        } else {
            return "redirect:/admin-panel/users?page=1";
        }
    }
}
