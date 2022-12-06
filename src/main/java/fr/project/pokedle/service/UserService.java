package fr.project.pokedle.service;

import fr.project.pokedle.exception.ConfirmPasswordInvalidException;
import fr.project.pokedle.exception.UserAlreadyExistException;
import fr.project.pokedle.model.UserDetailsForm;
import fr.project.pokedle.persistence.registration.User;
import fr.project.pokedle.repository.UserRepository;
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

    public User registerUser(UserDetailsForm userDetailsForm) throws Exception {
        if (!userDetailsForm.getPassword().equals(userDetailsForm.getConfirmPassword()))
            throw new ConfirmPasswordInvalidException();

        if (userRepository.findFirstByUsername(userDetailsForm.getUsername()).isPresent())
            throw new UserAlreadyExistException();

        User user = new User();
        user.setUsername(userDetailsForm.getUsername());
        user.setPassword(passwordEncoder.encode(userDetailsForm.getPassword()));
        user.setCreationDate(new Date());
        user.setRole("USER");

        userRepository.save(user);

        return user;
    }
}
