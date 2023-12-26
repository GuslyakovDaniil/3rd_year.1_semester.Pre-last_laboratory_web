package com.app.controlers;


import com.app.dtos.AddBrandsDto;
import com.app.dtos.AddUsersDto;

import com.app.services.AuthServices;
import com.app.services.NewUsersServices;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final NewUsersServices newUsersServices;

    public UserController(NewUsersServices newUsersServices) {
        this.newUsersServices = newUsersServices;
    }

    private static final Logger LOG = LogManager.getLogger(UserController.class);

    @ModelAttribute("userModel")
    public AddUsersDto initUser(){
        return new AddUsersDto();
    }

    @GetMapping("/add")
    public String addUser(Model model, Principal principal) {
        boolean isAdmin = false;

        if (principal != null) {
            // Получите объект UserDetails из контекста Spring Security
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            // Проверьте, есть ли у пользователя роль ADMIN
            isAdmin = userDetails.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        }

        // Передайте информацию о роли в модель
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("userModel", new AddUsersDto());

        LOG.info("Received request to add user");
        return "user-add";
    }


    @PostMapping("/add")
    public String addUser(@Valid AddUsersDto userModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userModel", userModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);
            return "redirect:/users/add";
        }
        newUsersServices.addUsers(userModel);

        System.out.println("Email: " + userModel.getEmail());
        System.out.println("Password: " + userModel.getPassword());

        return "redirect:/";
    }

    @GetMapping("/all")
    public String showAllUsers(Model model){
        model.addAttribute("UserInfo", newUsersServices.allUsers());
        LOG.info("All Users");
        return  "user-all";
    }

    @GetMapping("/user-details/{user-name}")
    public  String userDetails(@PathVariable("user-name") String email, Model model){
        model.addAttribute("usersDetails", newUsersServices.usersDetails(email));
        LOG.info("Received request for user: {}", email);
        return "user-details";
    }

    @GetMapping("/user-delete/{user-email}")
    public String deleteUser(@PathVariable("user-email") String email){
        newUsersServices.removeUser(email);
        LOG.info("User deleted successfully: {}", email);
        return "redirect:/users/all";
    }


}
