package fr.project.pokedle.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserDetailsForm {
    private String avatar;
    private String username;
    private String password;
    private String confirmPassword;
}
