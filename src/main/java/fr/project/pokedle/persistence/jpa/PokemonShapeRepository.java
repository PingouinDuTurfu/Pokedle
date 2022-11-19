package fr.project.pokedle.persistence.jpa;

import fr.project.pokedle.persistence.PokemonShape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PokemonShapeRepository extends JpaRepository<PokemonShape, Long> {

    PokemonShape findFirstByName(String name);
}
