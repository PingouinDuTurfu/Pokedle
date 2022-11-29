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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class GameManager {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private ClassicGameRepository classicGameRepository;

    @Autowired
    private ClassicGamePlayerRepository classicGamePlayerRepository;

    @Autowired
    private ClassicRoundRepository classicRoundRepository;


    public ClassicGame createGame() {
        long numberPokemon = pokemonRepository.count();

        // generate random number for id
        long id = (long) Math.floor(1 + numberPokemon * Math.random());

        /* verfify if the pokemon is correct */
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(RuntimeException::new);

        // create game for the day
        ClassicGame classicGame = new ClassicGame();
        classicGame.setPokemon(pokemon);
        classicGame.setDate(new Date());

        classicGameRepository.save(classicGame);

        return classicGame;
    }

    public ClassicGamePlayer createGamePlayer(User user, ClassicGame classicGame) {
        ClassicGamePlayer classicGamePlayer = new ClassicGamePlayer();
        classicGamePlayer.setUser(user);
        classicGamePlayer.setGame(classicGame);
        classicGamePlayer.setSuccess(false);
        classicGamePlayer.setCreationDate(new Date());

        classicGamePlayerRepository.save(classicGamePlayer);

        return classicGamePlayer;
    }

    public ClassicRound createGameRound(ClassicGamePlayer classicGamePlayer, Pokemon pokemon) {
        ClassicRound classicRound = new ClassicRound();
        classicRound.setGamePlayer(classicGamePlayer);
        classicRound.setPokemon(pokemon);

        if (classicGamePlayer.getRounds().size() > 0) {
            List<Long> indexRounds = new ArrayList<>(classicGamePlayer.getRounds().stream().map(ClassicRound::getRound).toList());
            Collections.sort(indexRounds);
            classicRound.setRound(1 + indexRounds.get(indexRounds.size() - 1));
        } else {
            classicRound.setRound(0);
        }

        classicRoundRepository.save(classicRound);

        return classicRound;
    }

    public ClassicGame getClassicGameOfToday() {
        /* verfify if there is a pokemon to find */
        LocalDateTime now = LocalDateTime.now(); //current date and time
        LocalDateTime start = now.toLocalDate().atStartOfDay();
        LocalDateTime end = now.toLocalDate().atStartOfDay().plusDays(1);

        return classicGameRepository.findByDateBetween(
                Date.from(start.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(end.atZone(ZoneId.systemDefault()).toInstant())
        ).orElseGet(this::createGame);
    }


    public ClassicGamePlayer getClassicGamePlayerOfToday(User user, ClassicGame classicGame) {
        return classicGamePlayerRepository.findByUserAndGame(
                user,
                classicGame
        ).orElseGet(() -> createGamePlayer(user, classicGame));
    }

    public List<ClassicRound> getPreviousRounds(User user) {
        ClassicGamePlayer classicGamePlayer = getClassicGamePlayerOfToday(
                user,
                getClassicGameOfToday()
        );
        List<fr.project.pokedle.persistence.classic.ClassicRound> rounds = classicGamePlayer.getRounds();
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
