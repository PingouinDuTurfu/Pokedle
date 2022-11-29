package fr.project.pokedle.game;

import fr.project.pokedle.persistence.data.Pokemon;
import fr.project.pokedle.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameManager {
    @Autowired
    private PokemonRepository pokemonRepository;

    public Pokemon getRandomPokemon() {
        long numberPokemon = pokemonRepository.count();

        // generate random number for id
        long id = (long) Math.floor(1 + numberPokemon * Math.random());

        /* verfify if the pokemon is correct */
        return pokemonRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
