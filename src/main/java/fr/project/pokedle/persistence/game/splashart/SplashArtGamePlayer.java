package fr.project.pokedle.persistence.game.splashart;

import fr.project.pokedle.persistence.registration.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter @Getter
@Entity(name = "SplashArtGamesPlayer")
public class SplashArtGamePlayer {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @Column
    private double score;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date successDate;

    @Column
    private boolean success;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private SplashArtGame game;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SplashArtRound> rounds;
}
