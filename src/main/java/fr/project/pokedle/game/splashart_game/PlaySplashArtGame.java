package fr.project.pokedle.game.splashart_game;

import fr.project.pokedle.game.classic_game.ClassicGameTry;
import fr.project.pokedle.persistence.data.Pokemon;
import fr.project.pokedle.persistence.game.splashart.SplashArtGame;
import fr.project.pokedle.persistence.game.splashart.SplashArtGamePlayer;
import fr.project.pokedle.persistence.game.splashart.SplashArtRound;
import fr.project.pokedle.persistence.registration.User;
import fr.project.pokedle.repository.PokemonRepository;
import fr.project.pokedle.repository.SplashArtGamePlayerRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PlaySplashArtGame {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private SplashArtGameManager splashArtGameManager;

    @Autowired
    private SplashArtGamePlayerRepository splashArtGamePlayerRepository;

    public JSONObject play(User user, String pokemonNameToTry) {
        JSONObject jsonObject = new JSONObject();

        if (user == null) {
            jsonObject.put("error", "invalid_user");
            return jsonObject;
        }

        SplashArtGame splashArtGame = splashArtGameManager.getSplashArtGameOfToday();

        SplashArtGamePlayer splashArtGamePlayer = splashArtGameManager.getSplashArtGamePlayerOfToday(user, splashArtGame);

        // if game is already finished => exit
        if (splashArtGamePlayer.isSuccess()) {
            jsonObject.put("error", "alredy_completed");
            return jsonObject;
        }

        /* verfify if the pokemon enter is correct */
        Pokemon pokemonToTry = pokemonRepository.findPokemonsByNameFr(pokemonNameToTry);

        if ((pokemonToTry == null)) {
            jsonObject.put("error", "pokemon_unknown");
            return jsonObject;
        }

        if (splashArtGameManager.getPreviousRounds(user).stream().map(SplashArtRound::getPokemon).toList().contains(pokemonToTry)) {
            jsonObject.put("error", "pokemon_already_tried");
            return jsonObject;
        }

        Pokemon pokemonToFind = splashArtGame.getPokemon();

        // compare pokemons
        ClassicGameTry classicGameTry = new ClassicGameTry(pokemonToTry, pokemonToFind);

        splashArtGameManager.createSplashArtRound(splashArtGamePlayer, pokemonToTry);

        jsonObject.put("is_same", classicGameTry.isSame());
        jsonObject.put("pokemon", pokemonToTry.toJSON());
        jsonObject.put("difference", classicGameTry.toJSON());

        if (classicGameTry.isSame()) {
            splashArtGamePlayer.setSuccess(true);
            splashArtGamePlayer.setSuccessDate(new Date());
            // set score
            // splashArtGamePlayer.setScore(  // score function // ));
            splashArtGamePlayerRepository.save(splashArtGamePlayer);
        }
        return jsonObject;
    }
}
