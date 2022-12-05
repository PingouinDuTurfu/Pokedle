package fr.project.pokedle.persistence.data;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
@Entity(name = "Pokemons")
public class Pokemon {
    @Id
    long id;

    @Column
    private String nameFr;

    @Column
    private String nameEn;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private PokemonShape shape;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private PokemonType type1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private PokemonType type2;

    @Column
    private String color;

    @Column
    private double height;

    @Column
    private double weight;

    @Column
    private long generation;

    @Column
    private String linkIcon;

    @Column
    private String linkSmallSprite;

    @Column
    private String linkBigSprite;

    public String getLinkIcon() {
        return linkIcon.split("assets/")[1];
    }

    public String getLinkBigSprite() {
        // !!!!
        return "http://***REMOVED***/pokedle/imagesHQ/" + linkBigSprite.split("imgagesHQ/")[1];
    }

    public Set<PokemonType> getTypes() {
        Set<PokemonType> types = new HashSet<>();
        types.add(type1);
        if (type2 != null)
            types.add(type2);
        return types;
    }

    public JSONObject toJSON() {
       JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("nameFr", getNameFr());
            jsonObject.put("nameEn", getNameEn());
            jsonObject.put("shape", getShape().toJSON());
            jsonObject.put("type1", getType1().toJSON());
            jsonObject.put("type2", (getType2() != null ? getType2().toJSON() : "null"));
            jsonObject.put("color", getColor());
            jsonObject.put("height", getHeight());
            jsonObject.put("weight", getWeight());
            jsonObject.put("generation", getGeneration());
            jsonObject.put("linkIcon", getLinkIcon());
            jsonObject.put("linkSmallSprite", getLinkSmallSprite());
            jsonObject.put("linkBigSprite", getLinkBigSprite());
            jsonObject.put("generation", getGeneration());
            } catch (Exception e) {
                throw new RuntimeException(e);
        }
        return jsonObject;
    }

    public static class Builder {
        private final Pokemon pokemon;

        public Builder() {
            pokemon = new Pokemon();
        }

        public Builder setId(long id) {
            pokemon.id = id;
            return this;
        }

        public Builder setNameEn(String nameEn) {
            pokemon.setNameEn(nameEn);
            return this;
        }

        public Builder setNameFr(String nameFr) {
            pokemon.setNameFr(nameFr);
            return this;
        }

        public Builder setType1(PokemonType type1) {
            pokemon.setType1(type1);
            return this;
        }

        public Builder setType2(PokemonType type2) {
            pokemon.setType2(type2);
            return this;
        }

        public Builder setShape(PokemonShape shape) {
            pokemon.setShape(shape);
            return this;
        }

        public Builder setHeight(double height) {
            pokemon.setHeight(height);
            return this;
        }

        public Builder setWeight(double weight) {
            pokemon.setWeight(weight);
            return this;
        }

        public Builder setGeneration(long generation) {
            pokemon.setGeneration(generation);
            return this;
        }

        public Builder setLinkIcon(String linkIcon) {
            pokemon.setLinkIcon(linkIcon);
            return this;
        }

        public Builder setLinkSmallSprite(String linkSmallSprite) {
            pokemon.setLinkSmallSprite(linkSmallSprite);
            return this;
        }

        public Builder setLinkBigSprite(String linkBigSprite) {
            pokemon.setLinkBigSprite(linkBigSprite);
            return this;
        }

        public Builder setColor(String color) {
            pokemon.setColor(color);
            return this;
        }

        public Builder setGeneration(long generation) {
            pokemon.setGeneration(generation);
            return this;
        }

        public Pokemon build() {
            return pokemon;
        }
    }
}
