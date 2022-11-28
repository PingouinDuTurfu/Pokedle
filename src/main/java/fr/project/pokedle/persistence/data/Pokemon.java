package fr.project.pokedle.persistence.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Map;
import java.util.function.Function;



@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Pokemons")
public class Pokemon {

    private static final Map<String, Function<Object, String>> functionMap = Map.of(
            PokemonType.class.toString(), o -> ((PokemonType) o).getName(),
            PokemonShape.class.toString(), o -> ((PokemonShape) o).getName()
    );

    @Id
    long id;

    @Column
    private String nameFr;

    @Column
    private String nameEn;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shape_id", nullable = false)
    private PokemonShape shape;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_1_id", nullable = false)
    private PokemonType type1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_2_id")
    private PokemonType type2;

    @Column
    private String color;

    @Column
    private double height;

    @Column
    private double weight;

    @Column
    private String linkIcon;

    @Column
    private String linkSmallSprite;

    @Column
    private String linkBigSprite;

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("id", getId());
        json.put("nameFr", getNameFr());
        json.put("nameEn", getNameEn());
        json.put("shape", getShape().toJSON());
        json.put("type1", getType1().toJSON());
        json.put("type2", (getType2() != null ? getType2().toJSON() : "null"));
        json.put("color", getColor());
        json.put("height", getHeight());
        json.put("weight", getWeight());
        json.put("linkIcon", getLinkIcon());
        json.put("linkSmallSprite", getLinkSmallSprite());
        json.put("linkBigSprite", getLinkBigSprite());
        return new JSONObject(json);
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

        public Pokemon build() {
            return pokemon;
        }
    }
}
