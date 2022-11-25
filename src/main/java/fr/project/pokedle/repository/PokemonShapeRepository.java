package fr.project.pokedle.repository;

import fr.project.pokedle.persistence.data.PokemonShape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PokemonShapeRepository extends JpaRepository<PokemonShape, Long> {

    PokemonShape findFirstByName(String name);
}
