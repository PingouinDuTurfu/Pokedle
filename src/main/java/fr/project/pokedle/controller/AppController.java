package fr.project.pokedle.controller;

import fr.project.pokedle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @GetMapping("")
    public String showRoot() {
        return "redirect:home";
    }

    @GetMapping("/home")
    public String showHome2() {
        return "home";
    }
}
