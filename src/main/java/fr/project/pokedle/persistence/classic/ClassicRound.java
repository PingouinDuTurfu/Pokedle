package fr.project.pokedle.persistence.classic;

import fr.project.pokedle.persistence.Pokemon;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

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
    private ClassicGamePlayer game;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pokemon", nullable = false)
    private Pokemon pokemon;



    public UUID getId() {
        return id;
    }

    public long getRound() {
        return round;
    }

    public void setRound(long round) {
        this.round = round;
    }
}
