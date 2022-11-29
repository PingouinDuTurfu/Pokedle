package fr.project.pokedle.game;

import fr.project.pokedle.persistence.classic.ClassicGamePlayer;
import fr.project.pokedle.persistence.classic.ClassicRound;
import fr.project.pokedle.persistence.data.Pokemon;
import fr.project.pokedle.persistence.registration.User;
import fr.project.pokedle.repository.ClassicRoundRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PreviousRound {

    @Autowired
    PlayOfficialGame playOfficialGame;

    @Autowired
    ClassicRoundRepository classicRoundRepository;


    public List<ClassicRound> getPreviousRounds(User user) {
        ClassicGamePlayer classicGamePlayer = playOfficialGame.getClassicGamePlayerOfToday(
                user,
                playOfficialGame.getClassicGameOfToday()
        );
        List<ClassicRound> rounds = new ArrayList<>(classicGamePlayer.getRounds());
        Collections.sort(rounds, (o1, o2) -> (int) (o1.getRound() - o2.getRound()));

        return rounds;
    }

    public JSONArray getPreviousRoundsJSON(User user) {
        List<ClassicRound> rounds = getPreviousRounds(user);

        if (rounds.size() == 0)
            return new JSONArray();

        JSONArray json = new JSONArray();

        Pokemon pokemonToFind = rounds.get(0).getGamePlayer().getGame().getPokemon();
        rounds.forEach(round -> {
            GameOfficialTry gameOfficialTry = new GameOfficialTry(round.getPokemon(), pokemonToFind);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("is_same", gameOfficialTry.isSame());
            jsonObject.put("pokemon", round.getPokemon().toJSON());
            jsonObject.put("difference", gameOfficialTry.toJSON());

            json.add(jsonObject);
        });

        return json;
    }


}
