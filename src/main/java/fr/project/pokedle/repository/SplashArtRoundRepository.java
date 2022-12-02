package fr.project.pokedle.repository;

import fr.project.pokedle.persistence.game.splashart.SplashArtRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SplashArtRoundRepository extends JpaRepository<SplashArtRound, UUID> {
}
