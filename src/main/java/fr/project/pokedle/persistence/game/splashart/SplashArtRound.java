package fr.project.pokedle.persistence.game.splashart;

import fr.project.pokedle.persistence.data.Pokemon;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@Entity(name = "SplashArtRound")
public class SplashArtRound {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "splash_art_round_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @Column
    private long round;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "classic_official", nullable = false)
    private SplashArtGamePlayer gamePlayer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pokemon", nullable = false)
    private Pokemon pokemon;

}
