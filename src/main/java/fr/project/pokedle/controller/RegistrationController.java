package fr.project.pokedle.controller;

import fr.project.pokedle.exception.AvatarInvalidException;
import fr.project.pokedle.exception.ConfirmPasswordInvalidException;
import fr.project.pokedle.exception.UserAlreadyExistException;
import fr.project.pokedle.game.GameManager;
import fr.project.pokedle.model.UserDetailsForm;
import fr.project.pokedle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private GameManager gameManager;

    @GetMapping("/login")
    public String login() {
        return "registration/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("pokemonList", gameManager.getPokemonList());
        model.addAttribute("user", new UserDetailsForm());
        return "registration/register";
    }

    @PostMapping("/register")
    public String registerUser(UserDetailsForm userDetailsForm, HttpServletRequest request) throws Exception {
        try {
            System.out.println(userDetailsForm.getUsername());
            System.out.println(userDetailsForm.getPassword());
            System.out.println(userDetailsForm.getAvatar());

            userService.registerUser(userDetailsForm);
            request.login(userDetailsForm.getUsername(), userDetailsForm.getPassword());
        } catch (ConfirmPasswordInvalidException e) {
            return "redirect:/register?errorNotMatchingPassword";
        } catch (UserAlreadyExistException e) {
            return "redirect:/register?errorUserAlreadyExist";
        } catch (AvatarInvalidException e) {
            return "redirect:/register?errorAvatarInvalid";
        }
        return "redirect:/home?registration_complete";
    }
}
