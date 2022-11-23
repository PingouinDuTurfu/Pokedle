package fr.project.pokedle.persistence.repository;

import fr.project.pokedle.persistence.PokemonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PokemonTypeRepository extends JpaRepository<PokemonType, Long> {

    PokemonType findFirstByName(String name);
}
