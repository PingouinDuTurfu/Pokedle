package fr.project.pokedle.game;

import fr.project.pokedle.persistence.classic.ClassicRound;
import fr.project.pokedle.persistence.data.Pokemon;
import fr.project.pokedle.persistence.registration.User;
import fr.project.pokedle.persistence.classic.ClassicGame;
import fr.project.pokedle.persistence.classic.ClassicGamePlayer;
import fr.project.pokedle.repository.ClassicGamePlayerRepository;
import fr.project.pokedle.repository.PokemonRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class PlayOfficialGame {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private ClassicGamePlayerRepository classicGamePlayerRepository;

    @Autowired
    private GameManager gameManager;


    public JSONObject play(User user, String pokemonNameToTry) {
        JSONObject jsonObject = new JSONObject();

        if (user == null) {
            jsonObject.put("error", "invalid_user");
            return jsonObject;
        }

        ClassicGame classicGame = gameManager.getClassicGameOfToday();

        ClassicGamePlayer classicGamePlayer = gameManager.getClassicGamePlayerOfToday(user, classicGame);

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

        if (gameManager.getPreviousRounds(user).stream().map(ClassicRound::getPokemon).toList().contains(pokemonToTry)) {
            jsonObject.put("error", "pokemon_already_tried");
            return jsonObject;
        }

        Pokemon pokemonToFind = classicGame.getPokemon();

        // compare pokemons
        GameOfficialTry gameOfficialTry = new GameOfficialTry(pokemonToTry, pokemonToFind);

        gameManager.createGameRound(classicGamePlayer, pokemonToTry);

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
