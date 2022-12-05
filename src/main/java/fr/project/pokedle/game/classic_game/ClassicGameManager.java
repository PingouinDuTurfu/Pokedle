package fr.project.pokedle.game.classic_game;

import fr.project.pokedle.game.GameManager;
import fr.project.pokedle.persistence.data.Pokemon;
import fr.project.pokedle.persistence.game.classic.ClassicGame;
import fr.project.pokedle.persistence.game.classic.ClassicGamePlayer;
import fr.project.pokedle.persistence.game.classic.ClassicRound;
import fr.project.pokedle.persistence.registration.User;
import fr.project.pokedle.repository.ClassicGamePlayerRepository;
import fr.project.pokedle.repository.ClassicGameRepository;
import fr.project.pokedle.repository.ClassicRoundRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class ClassicGameManager {
    @Autowired
    private ClassicGameRepository classicGameRepository;
    @Autowired
    private ClassicGamePlayerRepository classicGamePlayerRepository;
    @Autowired
    private ClassicRoundRepository classicRoundRepository;
    @Autowired
    private GameManager gameManager;

    public ClassicGame createClassicGame() {
        Pokemon pokemon = gameManager.getRandomPokemon();

        // create game for the day
        ClassicGame classicGame = new ClassicGame();
        classicGame.setPokemon(pokemon);
        classicGame.setDate(new Date());
        classicGameRepository.save(classicGame);
        return classicGame;
    }

    public ClassicGamePlayer createClassicGamePlayer(User user, ClassicGame classicGame) {
        ClassicGamePlayer classicGamePlayer = new ClassicGamePlayer();
        classicGamePlayer.setUser(user);
        classicGamePlayer.setGame(classicGame);
        classicGamePlayer.setSuccess(false);
        classicGamePlayer.setCreationDate(new Date());
        classicGamePlayerRepository.save(classicGamePlayer);
        return classicGamePlayer;
    }

    public ClassicRound createClassicRound(ClassicGamePlayer classicGamePlayer, Pokemon pokemon) {
        ClassicRound classicRound = new ClassicRound();
        classicRound.setGamePlayer(classicGamePlayer);
        classicRound.setPokemon(pokemon);

        if (classicGamePlayer.getRounds().size() > 0) {
            List<Long> indexRounds = new ArrayList<>(classicGamePlayer.getRounds().stream().map(ClassicRound::getRound).toList());
            Collections.sort(indexRounds);
            classicRound.setRound(1 + indexRounds.get(indexRounds.size() - 1));
        } else
            classicRound.setRound(0);
        classicRoundRepository.save(classicRound);
        return classicRound;
    }

    public ClassicGame getClassicGameOfDay(Date date) {
        /* verfify if there is a pokemon to find */
        return classicGameRepository.findByDateBetween(
                gameManager.startOfDay(date),
                gameManager.endOfDay(date)
        ).orElseGet(this::createClassicGame);
    }

    public ClassicGamePlayer getClassicGamePlayer(User user, ClassicGame classicGame) {
        return classicGamePlayerRepository.findByUserAndGame(
                user,
                classicGame
        ).orElseGet(() -> createClassicGamePlayer(user, classicGame));
    }

    public List<ClassicRound> getPreviousRounds(User user) {
        ClassicGamePlayer classicGamePlayer = getClassicGamePlayer(
                user,
                getClassicGameOfDay(new Date())
        );
        List<ClassicRound> rounds = classicGamePlayer.getRounds();
        if (rounds != null && rounds.size() > 0)
            Collections.sort(rounds, (o1, o2) -> (int) (o1.getRound() - o2.getRound()));
        return rounds;
    }

    public JSONArray getPreviousRoundsJSON(User user) {
        List<ClassicRound> rounds = getPreviousRounds(user);
        JSONArray json = new JSONArray();

        if (rounds != null && rounds.size() > 0) {
            Pokemon pokemonToFind = rounds.get(0).getGamePlayer().getGame().getPokemon();
            rounds.forEach(round -> {
                ClassicGameTry classicGameTry = new ClassicGameTry(round.getPokemon(), pokemonToFind);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("is_same", classicGameTry.isSame());
                jsonObject.put("pokemon", round.getPokemon().toJSON());
                jsonObject.put("difference", classicGameTry.toJSON());

                json.add(jsonObject);
            });
        }
        return json;
    }
}
