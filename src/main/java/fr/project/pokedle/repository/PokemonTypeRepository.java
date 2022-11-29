package fr.project.pokedle.repository;

import fr.project.pokedle.persistence.data.PokemonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonTypeRepository extends JpaRepository<PokemonType, Long> {

    PokemonType findFirstByName(String name);
}
