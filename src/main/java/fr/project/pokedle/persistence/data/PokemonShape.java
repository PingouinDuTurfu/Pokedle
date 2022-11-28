package fr.project.pokedle.persistence.data;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
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

    public String getLinkIcon() {
        return linkIcon.split("asset")[1].substring(2);
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("id", getId());
        json.put("name", getName());
        json.put("linkIcon", getLinkIcon());
        return new JSONObject(json);
    }

    public static class Builder {

        private final PokemonShape pokemonShape;

        public Builder() {
            pokemonShape = new PokemonShape();
        }

        public Builder setName(String name) {
            pokemonShape.setName(name);
            return this;
        }

        public Builder setLinkIcon(String linkIcon) {
            pokemonShape.setLinkIcon(linkIcon);
            return this;
        }

        public PokemonShape build() {
            return pokemonShape;
        }
    }
}
