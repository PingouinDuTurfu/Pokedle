package fr.project.pokedle.controller;

import fr.project.pokedle.model.UserDetailsForm;
import fr.project.pokedle.persistence.registration.User;
import fr.project.pokedle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;


    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "connect/register";
    }

    @PostMapping("/register/processing")
    public RedirectView registerUser(UserDetailsForm userDetailsForm) {
        System.out.println(1);
        User user = userService.registerUser(userDetailsForm);
        System.out.println(2);
        return new RedirectView("../home");
    }
}
