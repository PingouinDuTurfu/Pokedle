package fr.project.pokedle.persistence.jpa;

import fr.project.pokedle.persistence.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

}
