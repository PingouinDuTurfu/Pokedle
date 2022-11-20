package fr.project.pokedle;

import fr.project.pokedle.connection.CustomUserDetails;
import fr.project.pokedle.game.GameOfficialManager;
import fr.project.pokedle.persistence.Pokemon;
import fr.project.pokedle.persistence.classic.ClassicGame;
import fr.project.pokedle.persistence.repository.ClassicGameRepository;
import fr.project.pokedle.persistence.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class GameController {

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    ClassicGameRepository classicGameRepository;

    @GetMapping("/play/official")
    public String showOfficialGame(Model model) {
        List<Pokemon> pokemonList = pokemonRepository.findAll();


        model.addAttribute("pokemonList", pokemonList);

        return "play_official";
    }

    @PostMapping(value = "/play/official_try")
    public String tryPokemonOfficialGame(@Param("pokemonName") String pokemonName) {
        System.out.println(pokemonName);
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getUsername());

        /* verfify if the pokemon enter is correct */
        Optional<Pokemon> pokemonToTryOptional = pokemonRepository.findPokemonsByNameFr(pokemonName);
        if (pokemonToTryOptional.isEmpty())
            throw new RuntimeException();
        Pokemon pokemonToTry = pokemonToTryOptional.get();

        /* verfify if there is a pokemon to find */
        LocalDateTime now = LocalDateTime.now(); //current date and time
        LocalDateTime start = now.toLocalDate().atStartOfDay();
        LocalDateTime end = now.toLocalDate().atStartOfDay().plusDays(1);

        Optional<ClassicGame> classicGameOptional = classicGameRepository.findByDateBetween(
                Date.from(start.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(end.atZone(ZoneId.systemDefault()).toInstant())
        );

        ClassicGame classicGame;
        if (classicGameOptional.isPresent()) {
            classicGame = classicGameOptional.get();
        } else {
            GameOfficialManager gameManager = new GameOfficialManager();
            classicGame = gameManager.createOfficialGame();
        }
//
//        Pokemon pokemonToFind = classicGame.getPokemon();
//
//        GameOfficialTry GameOfficialTry = new GameOfficialTry(pokemonToTry, pokemonToFind);


        return "home";
    }
}
