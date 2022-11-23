package fr.project.pokedle.persistence.repository;

import fr.project.pokedle.persistence.User;
import fr.project.pokedle.persistence.classic.ClassicGamePlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassicGamePlayerRepository extends JpaRepository<ClassicGamePlayer, UUID> {
    Optional<ClassicGamePlayer> findByUserAndCreationDateBetween(User user, Date creationDate, Date creationDate2);
}
