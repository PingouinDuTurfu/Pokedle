package fr.project.pokedle.game.classic_game;

import fr.project.pokedle.game.GameManager;
import fr.project.pokedle.persistence.data.Pokemon;
import fr.project.pokedle.persistence.game.classic.ClassicGame;
import fr.project.pokedle.persistence.game.classic.ClassicGamePlayer;
import fr.project.pokedle.persistence.game.classic.ClassicRound;
import fr.project.pokedle.persistence.registration.User;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PlayClassicGame {
   @Autowired
    private ClassicGameManager classicGameManager;
    @Autowired
    private GameManager gameManager;

    public JSONObject play(User user, String pokemonNameToTry) {
        JSONObject jsonObject = new JSONObject();

        if (user == null) {
            jsonObject.put("error", "invalid_user");
            return jsonObject;
        }

        ClassicGame classicGame = classicGameManager.getClassicGameOfDay(new Date());
        ClassicGamePlayer classicGamePlayer = classicGameManager.getClassicGamePlayer(user, classicGame);
        // if game is already finished => exit
        if (classicGamePlayer.isSuccess()) {
            jsonObject.put("error", "alredy_completed");
            return jsonObject;
        }

        /* verfify if the pokemon enter is correct */
        Pokemon pokemonToTry = gameManager.getPokemonByName(pokemonNameToTry);
        if ((pokemonToTry == null)) {
            jsonObject.put("error", "pokemon_unknown");
            return jsonObject;
        }

        if (classicGameManager.getPreviousRounds(user).stream().map(ClassicRound::getPokemon).toList().contains(pokemonToTry)) {
            jsonObject.put("error", "pokemon_already_tried");
            return jsonObject;
        }

        Pokemon pokemonToFind = classicGame.getPokemon();

        // compare pokemons
        ClassicGameTry classicGameTry = new ClassicGameTry(pokemonToTry, pokemonToFind);

        classicGameManager.createClassicRound(classicGamePlayer, pokemonToTry);

        jsonObject.put("is_same", classicGameTry.isSame());
        jsonObject.put("pokemon", pokemonToTry.toJSON());
        jsonObject.put("difference", classicGameTry.toJSON());

        if (classicGameTry.isSame())
            jsonObject.put("score", classicGameManager.saveGamePlayerOnCompletion(classicGamePlayer));
        return jsonObject;
    }
}
