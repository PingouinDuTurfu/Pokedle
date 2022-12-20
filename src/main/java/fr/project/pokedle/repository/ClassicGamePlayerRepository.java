package fr.project.pokedle.repository;

import fr.project.pokedle.persistence.game.classic.ClassicGame;
import fr.project.pokedle.persistence.game.classic.ClassicGamePlayer;
import fr.project.pokedle.persistence.registration.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassicGamePlayerRepository extends JpaRepository<ClassicGamePlayer, UUID> {
    Optional<ClassicGamePlayer> findByUserAndGame(User user, ClassicGame classicGame);
    List<ClassicGamePlayer> findAllByCreationDateBetween(Date dateStart, Date dateEnd);
}
