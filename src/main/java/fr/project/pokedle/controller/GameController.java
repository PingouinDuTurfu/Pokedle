package fr.project.pokedle.controller;

import fr.project.pokedle.service.UserDetailsImpl;
import fr.project.pokedle.game.PlayOfficialGame;
import fr.project.pokedle.persistence.data.Pokemon;
import fr.project.pokedle.repository.PokemonRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private PlayOfficialGame playOfficialGame;

    @GetMapping("/play/official")
    public String showOfficialGame(Model model) {
        List<Pokemon> pokemonList = pokemonRepository.findAll();


        model.addAttribute("pokemonList", pokemonList);

        return "play/classic";
    }

    @PostMapping(value = "/play/official_try", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> tryPokemonOfficialGame(@Param("pokemonName") String pokemonName, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        JSONObject jsonObject = playOfficialGame.play(userDetails.getUser(), pokemonName);

        return ResponseEntity.status(HttpStatus.OK).body(jsonObject);
    }
}
