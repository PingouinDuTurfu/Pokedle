package fr.project.pokedle.persistence.registration;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity(name = "Users")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column
    private String role;
}
