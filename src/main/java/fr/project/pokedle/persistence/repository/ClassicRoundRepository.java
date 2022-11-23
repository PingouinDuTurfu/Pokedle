package fr.project.pokedle.persistence.repository;

import fr.project.pokedle.persistence.classic.ClassicGamePlayer;
import fr.project.pokedle.persistence.classic.ClassicRound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClassicRoundRepository extends JpaRepository<ClassicRound, UUID> {
    List<ClassicRound> findAllByGame(ClassicGamePlayer classicGamePlayer);
}
