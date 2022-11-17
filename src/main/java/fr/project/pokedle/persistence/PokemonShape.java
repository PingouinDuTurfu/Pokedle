package fr.project.pokedle.persistence;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PokemonShapes")
public class PokemonShape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String linkIcon;

    @OneToMany(mappedBy = "shape", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Pokemon> pokemons;

    public PokemonShape() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkIcon() {
        return linkIcon;
    }

    public void setLinkIcon(String linkIcon) {
        this.linkIcon = linkIcon;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }


    @Override
    public String toString() {
        return "PokemonShape{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", linkIcon='" + linkIcon + '\'' +
                '}';
    }
}
