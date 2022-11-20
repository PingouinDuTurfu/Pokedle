package fr.project.pokedle.persistence.repository;

import fr.project.pokedle.persistence.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    Optional<Pokemon> findPokemonById(long pokemon_id);
    Optional<Pokemon> findPokemonsByNameFr(String name_fr);
}
