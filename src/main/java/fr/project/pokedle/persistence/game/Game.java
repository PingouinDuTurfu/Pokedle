package fr.project.pokedle.persistence.game;

import fr.project.pokedle.persistence.data.Pokemon;
import fr.project.pokedle.persistence.game.classic.ClassicGamePlayer;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter @Getter
public abstract class Game {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    protected UUID id;

    @Column
    @Temporal(TemporalType.DATE)
    protected Date date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    protected Pokemon pokemon;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<ClassicGamePlayer> gamePlayers;
}
