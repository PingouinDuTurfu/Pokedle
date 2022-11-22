package fr.project.pokedle;

import fr.project.pokedle.connection.CustomUserDetails;
import fr.project.pokedle.game.GameOfficialManager;
import fr.project.pokedle.game.GameOfficialTry;
import fr.project.pokedle.persistence.Pokemon;
import fr.project.pokedle.persistence.classic.ClassicGame;
import fr.project.pokedle.persistence.classic.ClassicGamePlayer;
import fr.project.pokedle.persistence.classic.ClassicRound;
import fr.project.pokedle.persistence.repository.ClassicGamePlayerRepository;
import fr.project.pokedle.persistence.repository.ClassicGameRepository;
import fr.project.pokedle.persistence.repository.ClassicRoundRepository;
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

    @PostMapping(value = "/play/official_try")
    public String tryPokemonOfficialGame(@Param("pokemonName") String pokemonName) {
        System.out.println(pokemonName);
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getUsername());

        /* verfify if there is a pokemon to find */
        LocalDateTime now = LocalDateTime.now(); //current date and time
        LocalDateTime start = now.toLocalDate().atStartOfDay();
        LocalDateTime end = now.toLocalDate().atStartOfDay().plusDays(1);

        GameOfficialManager gameOfficialManager = new GameOfficialManager(pokemonRepository, classicGameRepository, classicGamePlayerRepository, classicRoundRepository);

        ClassicGame classicGame = classicGameRepository.findByDateBetween(
                Date.from(start.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(end.atZone(ZoneId.systemDefault()).toInstant())
        ).orElseGet(gameOfficialManager::createGame);

        ClassicGamePlayer classicGamePlayer = classicGamePlayerRepository.findByUserAndCreationDateBetween(
                userDetails.getUser(),
                Date.from(start.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(end.atZone(ZoneId.systemDefault()).toInstant())
        ).orElseGet(() -> gameOfficialManager.createGamePlayer(userDetails.getUser(), classicGame));

        if (classicGamePlayer.isSuccess()) {
            System.out.println("already finish");
            return "home";
        }

        /* verfify if the pokemon enter is correct */
        Pokemon pokemonToTry = pokemonRepository.findPokemonsByNameFr(pokemonName).orElseThrow(RuntimeException::new);

        Pokemon pokemonToFind = classicGame.getPokemon();
        System.out.println(pokemonToFind.getNameFr());

        // comparaison des pokemon
        GameOfficialTry gameOfficialTry = new GameOfficialTry(pokemonToTry, pokemonToFind);

        ClassicRound classicRound = gameOfficialManager.createGameRound(classicGamePlayer, pokemonToTry);

        if (gameOfficialTry.isSame()) {
            classicGamePlayer.setSuccess(true);
            classicGamePlayer.setSuccessDate(new Date());
            classicGamePlayerRepository.save(classicGamePlayer);
            System.out.println("You found it !");
        } else {
            // do sthg
        }

        System.out.println(gameOfficialTry);
        System.out.println(classicRound);

        return "home";
    }
}
