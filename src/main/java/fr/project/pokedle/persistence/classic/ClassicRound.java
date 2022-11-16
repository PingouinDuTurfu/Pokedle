package fr.project.pokedle.persistence.classic;

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
    private double round;

    public UUID getId() {
        return id;
    }

    public double getRound() {
        return round;
    }

    public void setRound(double round) {
        this.round = round;
    }
}
