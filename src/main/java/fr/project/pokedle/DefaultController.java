package fr.project.pokedle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/classic")
    public String classic() {
        return "classicGame";
    }
}
