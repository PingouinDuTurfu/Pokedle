package fr.project.pokedle;

import fr.project.pokedle.connection.CustomUserDetails;
import fr.project.pokedle.game.JSONPokemon;
import fr.project.pokedle.game.PlayOfficialGame;
import fr.project.pokedle.persistence.Pokemon;
import fr.project.pokedle.persistence.User;
import fr.project.pokedle.persistence.repository.ClassicGamePlayerRepository;
import fr.project.pokedle.persistence.repository.ClassicGameRepository;
import fr.project.pokedle.persistence.repository.ClassicRoundRepository;
import fr.project.pokedle.persistence.repository.PokemonRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

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
    @ResponseBody
    public ResponseEntity tryPokemonOfficialGame(@Param("pokemonName") String pokemonName){
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        PlayOfficialGame playOfficialGame = new PlayOfficialGame(pokemonRepository, classicGameRepository, classicGamePlayerRepository, classicRoundRepository);
        playOfficialGame.play(user, pokemonName);
        System.out.println(playOfficialGame.getResult());

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(playOfficialGame.getResult());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ResponseEntity(
                new JSONPokemon(playOfficialGame.getPokemonToTry())
                , HttpStatus.OK);

    }
}
