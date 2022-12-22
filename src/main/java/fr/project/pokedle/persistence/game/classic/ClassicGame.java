package fr.project.pokedle.persistence.game.classic;

import fr.project.pokedle.persistence.data.Pokemon;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter @Getter
@Entity(name = "ClassicGames")
public class ClassicGame {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Pokemon pokemon;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ClassicGamePlayer> gamePlayers;

    public List<ClassicGamePlayer> getGamePlayers() {
        if (gamePlayers != null)
            return gamePlayers;
        return new ArrayList<>();
    }
}
