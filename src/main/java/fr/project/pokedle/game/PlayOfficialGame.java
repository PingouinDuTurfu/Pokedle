package fr.project.pokedle.game;

import fr.project.pokedle.persistence.data.Pokemon;
import fr.project.pokedle.persistence.registration.User;
import fr.project.pokedle.persistence.classic.ClassicGame;
import fr.project.pokedle.persistence.classic.ClassicGamePlayer;
import fr.project.pokedle.persistence.classic.ClassicRound;
import fr.project.pokedle.repository.ClassicGamePlayerRepository;
import fr.project.pokedle.repository.ClassicGameRepository;
import fr.project.pokedle.repository.ClassicRoundRepository;
import fr.project.pokedle.repository.PokemonRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Autowired
    GameOfficialManager gameOfficialManager;


    public ClassicGame getClassicGameOfToday() {
        /* verfify if there is a pokemon to find */
        LocalDateTime now = LocalDateTime.now(); //current date and time
        LocalDateTime start = now.toLocalDate().atStartOfDay();
        LocalDateTime end = now.toLocalDate().atStartOfDay().plusDays(1);

        GameOfficialManager gameOfficialManager = new GameOfficialManager(pokemonRepository, classicGameRepository, classicGamePlayerRepository, classicRoundRepository);

        return classicGameRepository.findByDateBetween(
                Date.from(start.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(end.atZone(ZoneId.systemDefault()).toInstant())
        ).orElseGet(gameOfficialManager::createGame);
    }


    public ClassicGamePlayer getClassicGamePlayerOfToday(User user, ClassicGame classicGame) {
        return classicGamePlayerRepository.findByUserAndAndGameAnd(
                user,
                classicGame
        ).orElseGet(() -> gameOfficialManager.createGamePlayer(user, classicGame));
    }


    public JSONObject play(User user, String pokemonNameToTry) {
        JSONObject jsonObject = new JSONObject();

        if (user == null) {
            jsonObject.put("error", "invalid_user");
            return jsonObject;
        }

        ClassicGame classicGame = getClassicGameOfToday();

        ClassicGamePlayer classicGamePlayer = getClassicGamePlayerOfToday(user, classicGame);

        // if game is already finished => exit
        if (classicGamePlayer.isSuccess()) {
            jsonObject.put("error", "alredy_completed");
            return jsonObject;
        }

        /* verfify if the pokemon enter is correct */
        Pokemon pokemonToTry = pokemonRepository.findPokemonsByNameFr(pokemonNameToTry);

        if ((pokemonToTry == null)) {
            jsonObject.put("error", "pokemon_unknown");
            return jsonObject;
        }

        Pokemon pokemonToFind = classicGame.getPokemon();

        // compare pokemons
        GameOfficialTry gameOfficialTry = new GameOfficialTry(pokemonToTry, pokemonToFind);

        gameOfficialManager.createGameRound(classicGamePlayer, pokemonToTry);

        jsonObject.put("is_same", gameOfficialTry.isSame());
        jsonObject.put("pokemon", pokemonToTry.toJSON());
        jsonObject.put("difference", gameOfficialTry.toJSON());

        if (gameOfficialTry.isSame()) {
            classicGamePlayer.setSuccess(true);
            classicGamePlayer.setSuccessDate(new Date());
            // set score
            // classicGamePlayer.setScore(  // score function // ));
            classicGamePlayerRepository.save(classicGamePlayer);
        }
        return jsonObject;
    }
}
