package fr.project.pokedle.repository;

import fr.project.pokedle.persistence.game.splashart.SplashArtGame;
import fr.project.pokedle.persistence.game.splashart.SplashArtGamePlayer;
import fr.project.pokedle.persistence.registration.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SplashArtGamePlayerRepository extends JpaRepository<SplashArtGamePlayer, UUID> {
        Optional<SplashArtGamePlayer> findByUserAndGame(User user, SplashArtGame splashArtGame);
}
