package fr.project.pokedle.game.splashart_game;

import fr.project.pokedle.game.GameManager;
import fr.project.pokedle.persistence.data.Pokemon;
import fr.project.pokedle.persistence.game.splashart.SplashArtGame;
import fr.project.pokedle.persistence.game.splashart.SplashArtGamePlayer;
import fr.project.pokedle.persistence.game.splashart.SplashArtRound;
import fr.project.pokedle.persistence.registration.User;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PlaySplashArtGame {
    @Autowired
    private SplashArtGameManager splashArtGameManager;
    @Autowired
    private GameManager gameManager;

    public JSONObject play(User user, String pokemonNameToTry) {
        JSONObject jsonObject = new JSONObject();

        if (user == null) {
            jsonObject.put("error", "invalid_user");
            return jsonObject;
        }

        SplashArtGame splashArtGame = splashArtGameManager.getSplashArtGameOfDay(new Date());
        SplashArtGamePlayer splashArtGamePlayer = splashArtGameManager.getSplashArtGamePlayer(user, splashArtGame);
        // if game is already finished => exit
        if (splashArtGamePlayer.isSuccess()) {
            jsonObject.put("error", "alredy_completed");
            return jsonObject;
        }

        /* verfify if the pokemon enter is correct */
        Pokemon pokemonToTry = gameManager.getPokemonByName(pokemonNameToTry);
        if ((pokemonToTry == null)) {
            jsonObject.put("error", "pokemon_unknown");
            return jsonObject;
        }

        if (splashArtGameManager.getPreviousRounds(user).stream().map(SplashArtRound::getPokemon).toList().contains(pokemonToTry)) {
            jsonObject.put("error", "pokemon_already_tried");
            return jsonObject;
        }

        Pokemon pokemonToFind = splashArtGame.getPokemon();

        splashArtGameManager.createSplashArtRound(splashArtGamePlayer, pokemonToTry);

        // compare pokemons
        jsonObject.put("is_same", pokemonToTry.equals(pokemonToFind));
        jsonObject.put("pokemon", pokemonToTry.toJSON());

        if (pokemonToTry.equals(pokemonToFind))
            jsonObject.put("score", splashArtGameManager.saveGamePlayerOnCompletion(splashArtGamePlayer));
        return jsonObject;
    }
}
