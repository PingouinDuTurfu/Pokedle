package fr.project.pokedle.repository;

import fr.project.pokedle.persistence.game.classic.ClassicRound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClassicRoundRepository extends JpaRepository<ClassicRound, UUID> {
}