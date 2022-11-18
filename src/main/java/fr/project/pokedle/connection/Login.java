package fr.project.pokedle.connection;



import fr.project.pokedle.persistence.User;
import fr.project.pokedle.persistence.classic.ClassicGame;
import fr.project.pokedle.persistence.jpa.ClassicGameRepository;
import fr.project.pokedle.persistence.jpa.PokemonRepository;
import fr.project.pokedle.persistence.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Controller
@RequestMapping("/")
@RestController
public class Login {

    @GetMapping("/login")
    public String customersPage(HttpServletRequest request, Model model) {

        int page = 0; //default page number is 0 (yes it is weird)
        int size = 10; //default page size is 10

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        model.addAttribute("customers", .findAll(PageRequest.of(page, size)));
        return "customers";
    }


    }
}