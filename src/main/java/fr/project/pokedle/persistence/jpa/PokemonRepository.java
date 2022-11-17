package fr.project.pokedle.persistence.jpa;

import fr.project.pokedle.persistence.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

}