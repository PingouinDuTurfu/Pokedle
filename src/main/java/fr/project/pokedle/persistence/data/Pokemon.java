package fr.project.pokedle.persistence.data;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Entity
@Getter @Setter
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

    public String getLinkIcon() {
        return linkIcon.split("asset")[1].substring(2);
    }

    public JSONObject toJSON() {
        /*
        Map<String, String> jsonMap = new HashMap<>();

        try {
            for (Method m : this.getClass().getMethods()) {
                if (!m.getName().equals("getClass") && m.getName().startsWith("get") && m.getParameterTypes().length == 0) {
                    final Object o = m.invoke(this);
                    if (o != null) {
                        jsonMap.put(
                                StringUtils.uncapitalize(m.getName().substring(3)),
                                // If the map contains the class of the object, we apply the function
                                // Else, we just return the object as a string
                                // We need to split the class name because the class name contains the lazy creation of Hibernate
                                functionMap
                                        .getOrDefault(o.getClass().toString().split("\\$")[0], Object::toString)
                                        .apply(o)
                        );
                    } else
                        jsonMap.put(StringUtils.uncapitalize(m.getName().substring(3)), "null");
                }
            }
            //System.out.println(new JSONObject(jsonMap));
            return new JSONObject(jsonMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        */

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", getId());
            jsonObject.put("nameFr", getNameFr());
            jsonObject.put("nameEn", getNameEn());
            jsonObject.put("shape", getShape().toJson());
            jsonObject.put("type1", getType1().toJson());
            jsonObject.put("type2", (getType2() != null ? getType2().toJson() : "null"));
            jsonObject.put("color", getColor());
            jsonObject.put("height", getHeight());
            jsonObject.put("weight", getWeight());
            jsonObject.put("linkIcon", getLinkIcon());
            jsonObject.put("linkSmallSprite", getLinkSmallSprite());
            jsonObject.put("linkBigSprite", getLinkBigSprite());
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
