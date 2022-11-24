package fr.project.pokedle.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsForm {
    private String username;
    private String password;
    private String machingPassword;
}
