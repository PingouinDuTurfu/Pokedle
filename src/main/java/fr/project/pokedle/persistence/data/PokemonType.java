package fr.project.pokedle.persistence.data;

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
        return linkIcon;
    }

    public void setLinkIcon(String linkIcon) {
        this.linkIcon = linkIcon;
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
