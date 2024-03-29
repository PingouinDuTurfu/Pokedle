package fr.project.pokedle.game;

import fr.project.pokedle.persistence.data.Pokemon;
import fr.project.pokedle.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Component
public class GameManager {
    @Autowired
    private PokemonRepository pokemonRepository;

    public Pokemon getRandomPokemon() {
        long numberPokemon = pokemonRepository.count();
        long id = (long) Math.floor(1 + numberPokemon * Math.random());
        return pokemonRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Pokemon> getPokemonList() {
        return pokemonRepository.findAll().stream().sorted(Comparator.comparing(Pokemon::getNameFr)).toList();
    }

    public Pokemon getPokemonByName(String pokemonName) {
        return pokemonRepository.findByNameEn(pokemonName)
                .orElse(pokemonRepository.findByNameFr(pokemonName)
                        .orElse(null));
    }

    public Date startOfDay(Date date) {
        return Date.from(
                LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                        .toLocalDate().atStartOfDay()
                        .atZone(ZoneId.systemDefault()).toInstant()
        );
    }

    public Date endOfDay(Date date) {
        return Date.from(
                LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                        .toLocalDate().atStartOfDay().plusDays(1).minusSeconds(1)
                        .atZone(ZoneId.systemDefault()).toInstant()
        );
    }
}
