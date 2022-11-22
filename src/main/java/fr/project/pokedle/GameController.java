package fr.project.pokedle;

import fr.project.pokedle.connection.CustomUserDetails;
import fr.project.pokedle.game.PlayOfficialGame;
import fr.project.pokedle.persistence.Pokemon;
import fr.project.pokedle.persistence.User;
import fr.project.pokedle.persistence.repository.ClassicGamePlayerRepository;
import fr.project.pokedle.persistence.repository.ClassicGameRepository;
import fr.project.pokedle.persistence.repository.ClassicRoundRepository;
import fr.project.pokedle.persistence.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class GameController {

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    ClassicGameRepository classicGameRepository;

    @Autowired
    ClassicGamePlayerRepository classicGamePlayerRepository;

    @Autowired
    ClassicRoundRepository classicRoundRepository;

    @GetMapping("/play/official")
    public String showOfficialGame(Model model) {
        List<Pokemon> pokemonList = pokemonRepository.findAll();


        model.addAttribute("pokemonList", pokemonList);

        return "play_official";
    }

    @PostMapping(value = "/play/official_try", produces = MediaType.APPLICATION_JSON_VALUE)
    public String tryPokemonOfficialGame(@Param("pokemonName") String pokemonName) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        PlayOfficialGame playOfficialGame = new PlayOfficialGame(pokemonRepository, classicGameRepository, classicGamePlayerRepository, classicRoundRepository);
        playOfficialGame.play(user, pokemonName);
        System.out.println(playOfficialGame.getResult());

        return "pokemon_info.json");
    }
}
