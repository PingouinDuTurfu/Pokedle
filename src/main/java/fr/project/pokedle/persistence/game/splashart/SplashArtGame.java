package fr.project.pokedle.persistence.game.splashart;

import fr.project.pokedle.persistence.game.classic.ClassicGamePlayer;
import fr.project.pokedle.persistence.data.Pokemon;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity(name = "SplashArtGames")
public class SplashArtGame {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "splash_art_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pokemon", nullable = false)
    private Pokemon pokemon;

    @Column
    private double center_x;

    @Column
    private double center_y;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<ClassicGamePlayer> gamePlayers;
}
