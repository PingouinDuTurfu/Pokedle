package fr.project.pokedle.persistence.jpa;

import fr.project.pokedle.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findFirstByUsername(String username);
}
