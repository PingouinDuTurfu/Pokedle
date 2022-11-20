package fr.project.pokedle.game;

import fr.project.pokedle.persistence.Pokemon;
import fr.project.pokedle.persistence.classic.ClassicGame;
import fr.project.pokedle.persistence.repository.ClassicGameRepository;
import fr.project.pokedle.persistence.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class GameOfficialManager {
    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    ClassicGameRepository classicGameRepository;

    public GameOfficialManager() {}

    public ClassicGame createOfficialGame() {
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
}
