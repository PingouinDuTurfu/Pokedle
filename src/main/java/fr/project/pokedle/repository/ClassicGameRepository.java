package fr.project.pokedle.repository;

import fr.project.pokedle.persistence.classic.ClassicGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface ClassicGameRepository extends JpaRepository<ClassicGame, UUID> {

    Optional<ClassicGame> findByDateBetween(Date dateStart, Date dateEnd);

}
