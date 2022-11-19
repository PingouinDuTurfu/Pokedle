package fr.project.pokedle;

import fr.project.pokedle.game.GameOfficialManager;
import fr.project.pokedle.game.GameOfficialTry;
import fr.project.pokedle.persistence.Pokemon;
import fr.project.pokedle.persistence.classic.ClassicGame;
import fr.project.pokedle.persistence.jpa.ClassicGameRepository;
import fr.project.pokedle.persistence.jpa.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Controller
public class GameController {

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    ClassicGameRepository classicGameRepository;

    @GetMapping("/play/official")
    public String showOfficialGame() {
        return "play_official";
    }

    @PostMapping("/play/official_try")
    public String tryPokemonOfficialGame(long pokemon_id) {
        /* verfify if the pokemon enter is correct */
        Optional<Pokemon> pokemonToTryOptional = pokemonRepository.findById(pokemon_id);
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

        Pokemon pokemonToFind = classicGame.getPokemon();

        GameOfficialTry GameOfficialTry = new GameOfficialTry(pokemonToTry, pokemonToFind);


        return "play_official";
    }
}
