package fr.project.pokedle.persistence.data;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

import javax.persistence.*;

@Getter @Setter
@Entity(name = "PokemonTypes")
public class PokemonType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String linkIcon;

    public String getLinkIcon() {
        return linkIcon.split("asset")[1].substring(2);
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("id", getId());
        json.put("name", getName());
        json.put("linkIcon", getLinkIcon());
        return json;
    }

    public static class Builder {
        private final PokemonType pokemonType;

        public Builder() {
            pokemonType = new PokemonType();
        }

        public Builder setName(String name) {
            pokemonType.setName(name);
            return this;
        }

        public Builder setLinkIcon(String linkIcon) {
            pokemonType.setLinkIcon(linkIcon);
            return this;
        }

        public PokemonType build() {
            return pokemonType;
        }
    }
}
