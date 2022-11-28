package fr.project.pokedle.persistence.data;

import org.json.simple.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "PokemonTypes")
public class PokemonType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String linkIcon;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkIcon() {
        return linkIcon.split("asset")[1].substring(2);
    }

    public void setLinkIcon(String linkIcon) {
        this.linkIcon = linkIcon;
    }

    public JSONObject toJson() {
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
