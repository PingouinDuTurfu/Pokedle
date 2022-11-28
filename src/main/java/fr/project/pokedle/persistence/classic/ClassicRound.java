package fr.project.pokedle.persistence.classic;

import fr.project.pokedle.persistence.data.Pokemon;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@Entity(name = "ClassicRounds")
public class ClassicRound {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "classic_round_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @Column
    private long round;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "classic_official", nullable = false)
    private ClassicGamePlayer gamePlayer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pokemon", nullable = false)
    private Pokemon pokemon;

}
