package fr.project.pokedle.repository;

import fr.project.pokedle.persistence.data.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    Pokemon findPokemonById(long pokemon_id);
    Pokemon findPokemonsByNameFr(String name_fr);
}
