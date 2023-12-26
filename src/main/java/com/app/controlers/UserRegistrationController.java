package com.app.controlers;

import com.app.dtos.UserRegistration;
import com.app.models.Users;
import com.app.services.AuthServices;
import com.app.views.UserWi;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserRegistrationController {

    private AuthServices authServices;

    @Autowired
    public UserRegistrationController(AuthServices authServices) {
        this.authServices = authServices;
    }

    @ModelAttribute("userRegistrationDto")
    public UserRegistration initForm() {
        return new UserRegistration();
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid UserRegistration userRegistration,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistration", userRegistration);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDto", bindingResult);

            return "redirect:/users/register";
        }

        // Set the role to "USER" before registering the user
        userRegistration.setRole("USER");
        this.authServices.register(userRegistration);

        return "redirect:/users/login";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/users/login";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        String username = principal.getName();
        Users user = authServices.getUser(username);

        UserWi userWi = new UserWi(
                username,
                user.getEmail(),
                user.getFirst_name(),
                user.getLast_name()
        );

        model.addAttribute("user", userWi);

        return "profile";
    }
}
