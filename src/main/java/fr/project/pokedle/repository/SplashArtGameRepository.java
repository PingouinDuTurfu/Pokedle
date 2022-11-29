package fr.project.pokedle.repository;

import fr.project.pokedle.persistence.game.splashart.SplashArtGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SplashArtGameRepository extends JpaRepository<SplashArtGame, UUID> {
    Optional<SplashArtGame> findByDateBetween(Date dateStart, Date dateEnd);
}
