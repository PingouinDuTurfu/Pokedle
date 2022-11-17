package fr.project.pokedle.persistence.jpa;

import fr.project.pokedle.persistence.PokemonType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonTypeRepository extends JpaRepository<PokemonType, Long> {

    PokemonType findFirstByName(String name);
}
