package fr.project.pokedle.persistence.classic;

import fr.project.pokedle.persistence.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity(name = "ClassicOfficials")
public class ClassicOfficial {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "classic_official_id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "classic_game_id", nullable = false, insertable = false, updatable = false)
    private ClassicGame game;

    public UUID getId() {
        return id;
    }


    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getSuccessDate() {
        return successDate;
    }

    public void setSuccessDate(Date successDate) {
        this.successDate = successDate;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ClassicGame getGame() {
        return game;
    }

    public void setGame(ClassicGame game) {
        this.game = game;
    }
}