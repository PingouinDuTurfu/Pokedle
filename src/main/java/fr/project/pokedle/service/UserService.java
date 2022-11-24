package fr.project.pokedle.service;

import fr.project.pokedle.model.UserDetailsForm;
import fr.project.pokedle.persistence.User;
import fr.project.pokedle.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserDetailsForm userDetailsForm) {
        User user = new User();
        user.setUsername(userDetailsForm.getUsername());
        user.setPassword(passwordEncoder.encode(userDetailsForm.getPassword()));
        user.setCreationDate(new Date());
        user.setRole("USER");

        userRepository.save(user);

        return user;
    }
}
