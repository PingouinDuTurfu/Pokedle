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
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GameOfficialManager {
    PokemonRepository pokemonRepository;
    ClassicGameRepository classicGameRepository;
    ClassicGamePlayerRepository classicGamePlayerRepository;
    ClassicRoundRepository classicRoundRepository;

    public GameOfficialManager(PokemonRepository pokemonRepository, ClassicGameRepository classicGameRepository, ClassicGamePlayerRepository classicGamePlayerRepository, ClassicRoundRepository classicRoundRepository) {
        this.pokemonRepository = pokemonRepository;
        this.classicGameRepository = classicGameRepository;
        this.classicGamePlayerRepository = classicGamePlayerRepository;
        this.classicRoundRepository = classicRoundRepository;
    }

    public ClassicGame createGame() {
        long numberPokemon = pokemonRepository.count();

        // generate random number for id
        long id = (long) Math.floor(1 + numberPokemon * Math.random());

        /* verfify if the pokemon is correct */
        Optional<Pokemon> pokemonOptional = pokemonRepository.findById(id);
        if (pokemonOptional.isEmpty())
            throw new RuntimeException();
        Pokemon pokemon = pokemonOptional.get();

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
        classicRound.setGame(classicGamePlayer);
        classicRound.setPokemon(pokemon);

        List<ClassicRound> rounds = classicRoundRepository.findAllByGame(classicGamePlayer);

        long indexRound = rounds.stream().map(ClassicRound::getRound).max((o1, o2) -> Math.toIntExact(o1 > o2 ? o1 : o2)).orElse(0L) + 1;
        classicRound.setRound(indexRound);

        classicRoundRepository.save(classicRound);

        return classicRound;
    }
}
