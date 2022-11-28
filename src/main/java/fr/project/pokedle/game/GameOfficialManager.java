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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class GameOfficialManager {

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

        List<ClassicRound> rounds = classicRoundRepository.findAllByGamePlayer(classicGamePlayer);

        long indexRound = rounds.stream().map(ClassicRound::getRound).max((o1, o2) -> Math.toIntExact(o1 > o2 ? o1 : o2)).orElse(0L) + 1;
        classicRound.setRound(indexRound);

        classicRoundRepository.save(classicRound);

        return classicRound;
    }
}
