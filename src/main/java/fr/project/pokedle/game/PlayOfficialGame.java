package fr.project.pokedle.game;

import fr.project.pokedle.persistence.Pokemon;
import fr.project.pokedle.persistence.User;
import fr.project.pokedle.persistence.classic.ClassicGame;
import fr.project.pokedle.persistence.classic.ClassicGamePlayer;
import fr.project.pokedle.persistence.classic.ClassicRound;
import fr.project.pokedle.persistence.repository.ClassicGamePlayerRepository;
import fr.project.pokedle.persistence.repository.ClassicGameRepository;
import fr.project.pokedle.persistence.repository.ClassicRoundRepository;
import fr.project.pokedle.persistence.repository.PokemonRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class PlayOfficialGame {
    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    ClassicGameRepository classicGameRepository;

    @Autowired
    ClassicGamePlayerRepository classicGamePlayerRepository;

    @Autowired
    ClassicRoundRepository classicRoundRepository;



    public JSONObject play(User user, String pokemonNameToTry) {
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
                user,
                Date.from(start.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(end.atZone(ZoneId.systemDefault()).toInstant())
        ).orElseGet(() -> gameOfficialManager.createGamePlayer(user, classicGame));

        if (classicGamePlayer.isSuccess()) {
            System.out.println("already finished");
            return new JSONObject();
        }

        /* verfify if the pokemon enter is correct */
        Pokemon pokemonToTry = pokemonRepository.findPokemonsByNameFr(pokemonNameToTry).orElseThrow(RuntimeException::new);

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
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pokemon", pokemonToTry.toJSON());
            jsonObject.put("difference", gameOfficialTry.toJSON());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
