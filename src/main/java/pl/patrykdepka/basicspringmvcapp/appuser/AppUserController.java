package pl.patrykdepka.basicspringmvcapp.appuser;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserRegistrationDTO;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.AppUserTableAPDTO;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.EditAppUserAccountDataDTO;
import pl.patrykdepka.basicspringmvcapp.appuser.dto.EditAppUserProfileDTO;
import pl.patrykdepka.basicspringmvcapp.appuserrole.AppUserRoleService;
import pl.patrykdepka.basicspringmvcapp.core.CurrentUserFacade;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class AppUserController {
    private final AppUserService appUserService;
    private final AppUserRoleService appUserRoleService;
    private final MessageSource messageSource;
    private final CurrentUserFacade currentUserFacade;

    public AppUserController(AppUserService appUserService,
                             AppUserRoleService appUserRoleService,
                             MessageSource messageSource,
                             CurrentUserFacade currentUserFacade
    ) {
        this.appUserService = appUserService;
        this.appUserRoleService = appUserRoleService;
        this.messageSource = messageSource;
        this.currentUserFacade = currentUserFacade;
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
        if (appUserService.checkIfUserExists(userRegistrationDTO.getEmail())) {
            bindingResult.addError(new FieldError("userRegistrationDTO", "email", messageSource.getMessage("form.field.email.error.emailIsInUse.message", null, Locale.getDefault())));
        }
        if (bindingResult.hasErrors()) {
            return "forms/registration-form";
        } else {
            appUserService.createUser(userRegistrationDTO);
            return "redirect:/confirmation";
        }
    }

    @GetMapping("/confirmation")
    public String registrationConfirmation() {
        return "registration-confirmation";
    }

    @GetMapping("/profile")
    public String getUserProfile(Model model) {
        model.addAttribute("userProfile", appUserService.findUserProfile(currentUserFacade.getCurrentUser()));
        return "app-user-profile";
    }

    @GetMapping("/settings/profile")
    public String showUserProfileEditForm(Model model) {
        model.addAttribute("profileUpdated", false);
        model.addAttribute("editUserProfileDTO", appUserService.findUserProfileToEdit(currentUserFacade.getCurrentUser()));
        return "forms/app-user-profile-edit-form";
    }

    @PatchMapping("/settings/profile")
    public String updateUserProfile(@Valid @ModelAttribute(name = "editUserProfileDTO") EditAppUserProfileDTO editUserProfileDTO,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("profileUpdated", false);
        } else {
            model.addAttribute("editUserProfileDTO", appUserService.updateUserProfile(currentUserFacade.getCurrentUser(), editUserProfileDTO));
            model.addAttribute("profileUpdated", true);
        }
        return "forms/app-user-profile-edit-form";
    }

    @GetMapping("/admin-panel/users")
    public String getAllUsers(@RequestParam(name = "page", required = false) Integer pageNumber,
                              @RequestParam(name = "sort_by", required = false) String sortProperty,
                              @RequestParam(name = "order_by", required = false) String sortDirection,
                              Model model) {
        int page = pageNumber != null ? pageNumber : 1;
        String property = sortProperty != null && !"".equals(sortProperty) ? sortProperty : "lastName";
        String direction = sortDirection != null && !"".equals(sortDirection) ? sortDirection : "asc";

        if (page > 0) {
            PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.fromString(direction), property));
            Page<AppUserTableAPDTO> users = appUserService.findAllUsers(pageRequest);

            if (page <= users.getTotalPages()) {
                model.addAttribute("users", users);
                model.addAttribute("prefixUrl", "/admin-panel/users?");

                if (sortProperty != null) {
                    String sortParams = "sort_by=" + sortProperty + "&order_by=" + sortDirection;
                    model.addAttribute("sortParams", sortParams);
                }
                return "admin/app-users-table";
            } else {
                if ((sortProperty != null && !"".equals(sortProperty)) && (sortDirection != null && !"".equals(sortDirection))) {
                    return "redirect:/admin-panel/users?page=" + users.getTotalPages() + "&sort_by=" + sortProperty + "&order_by=" + sortDirection;
                }
                return "redirect:/admin-panel/users?page=" + users.getTotalPages();
            }
        } else {
            if ((sortProperty != null && !"".equals(sortProperty)) && (sortDirection != null && !"".equals(sortDirection))) {
                return "redirect:/admin-panel/users?page=1" + "&sort_by=" + sortProperty + "&order_by=" + sortDirection;
            }
            return "redirect:/admin-panel/users?page=1";
        }
    }

    @GetMapping("/admin-panel/users/results")
    public String getUsersBySearch(@RequestParam(name = "search_query", required = false) String searchQuery,
                                   @RequestParam(name = "page", required = false) Integer pageNumber,
                                   @RequestParam(name = "sort_by", required = false) String sortProperty,
                                   @RequestParam(name = "order_by", required = false) String sortDirection,
                                   Model model) {
        if (searchQuery != null) {
            int page = pageNumber != null ? pageNumber : 1;
            String property = sortProperty != null && !"".equals(sortProperty) ? sortProperty : "lastName";
            String direction = sortDirection != null && !"".equals(sortDirection) ? sortDirection : "asc";

            if (page > 0) {
                PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.fromString(direction), property));
                Page<AppUserTableAPDTO> users = appUserService.findUsersBySearch(searchQuery, pageRequest);

                if (users.getNumberOfElements() == 0) {
                    model.addAttribute("users", users);
                    if (page > 1) {
                        if (sortProperty != null) {
                            return "redirect:/admin-panel/users/results?search_query=" + searchQuery + "&sort_by=" + sortProperty;
                        }
                        return "redirect:/admin-panel/users/results?search_query=" + searchQuery;
                    } else {
                        model.addAttribute("prefixUrl", "/admin-panel/users/results?search_query=" + searchQuery + "&");
                        return "admin/app-users-table";
                    }
                } else if (page <= users.getTotalPages()) {
                    model.addAttribute("users", users);
                    searchQuery = searchQuery.replace(" ", "+");
                    model.addAttribute("searchQuery", searchQuery);
                    model.addAttribute("prefixUrl", "/admin-panel/users/results?search_query=" + searchQuery + "&");
                    return "admin/app-users-table";
                } else {
                    searchQuery = searchQuery.replace(" ", "+");
                    if (sortProperty != null) {
                        return "redirect:/admin-panel/users/results?search_query=" + searchQuery + "&page=" + users.getTotalPages() + "&sort_by=" + sortProperty;
                    }
                    return "redirect:/admin-panel/users/results?search_query=" + searchQuery + "&page=" + users.getTotalPages();
                }
            } else {
                searchQuery = searchQuery.replace(" ", "+");
                if (sortProperty != null) {
                    return "redirect:/admin-panel/users/results?search_query=" + searchQuery + "&page=1" + "&sort_by=" + sortProperty;
                }
                return "redirect:/admin-panel/users/results?search_query=" + searchQuery + "&page=1";
            }
        } else {
            return "redirect:/admin-panel/users/results?search_query=";
        }
    }

    @GetMapping("/admin-panel/users/{id}/settings/account")
    public String showUserAccountEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("accountUpdated", false);
        model.addAttribute("userId", id);
        model.addAttribute("editUserAccountDataDTO", appUserService.findUserAccountDataToEdit(id));
        model.addAttribute("userRoles", appUserRoleService.findAllUserRoles());
        return "admin/forms/app-user-account-edit-form";
    }

    @PatchMapping("/admin-panel/users/{id}/settings/account")
    public String updateUserAccount(@PathVariable Long id,
                                    @Valid @ModelAttribute(name = "editUserAccountDataDTO") EditAppUserAccountDataDTO editUserAccountDataDTO,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("accountUpdated", false);
            model.addAttribute("userId", id);
            model.addAttribute("userRoles", appUserRoleService.findAllUserRoles());
        } else {
            model.addAttribute("userId", id);
            model.addAttribute("editUserAccountDataDTO", appUserService.updateUserAccountData(id, editUserAccountDataDTO));
            model.addAttribute("accountUpdated", true);
            model.addAttribute("userRoles", appUserRoleService.findAllUserRoles());
        }
        return "admin/forms/app-user-account-edit-form";
    }

    @GetMapping("/admin-panel/users/{id}/settings/profile")
    public String showUserProfileEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("profileUpdated", false);
        model.addAttribute("userId", id);
        model.addAttribute("editUserProfileDTO", appUserService.findUserProfileToEdit(id));
        return "admin/forms/app-user-profile-edit-form";
    }

    @PatchMapping("/admin-panel/users/{id}/settings/profile")
    public String updateUserProfile(@PathVariable Long id,
                                    @Valid @ModelAttribute(name = "editUserProfileDTO") EditAppUserProfileDTO editUserProfileDTO,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("profileUpdated", false);
            model.addAttribute("userId", id);
        } else {
            model.addAttribute("userId", id);
            model.addAttribute("editUserProfileDTO", appUserService.updateUserProfile(id, editUserProfileDTO));
            model.addAttribute("profileUpdated", true);
        }
        return "admin/forms/app-user-profile-edit-form";
    }
}
