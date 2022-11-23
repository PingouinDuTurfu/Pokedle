package fr.project.pokedle.persistence;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "Pokemons")
public class Pokemon {

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


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public PokemonType getType1() {
        return type1;
    }

    public void setType1(PokemonType type1) {
        this.type1 = type1;
    }

    public PokemonType getType2() {
        return type2;
    }

    public void setType2(PokemonType type2) {
        this.type2 = type2;
    }

    public PokemonShape getShape() {
        return shape;
    }

    public void setShape(PokemonShape shape) {
        this.shape = shape;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getLinkIcon() {
        return linkIcon;
    }

    public void setLinkIcon(String linkIcon) {
        this.linkIcon = linkIcon;
    }

    public String getLinkSmallSprite() {
        return linkSmallSprite;
    }

    public void setLinkSmallSprite(String linkSmallSprite) {
        this.linkSmallSprite = linkSmallSprite;
    }

    public String getLinkBigSprite() {
        return linkBigSprite;
    }

    public void setLinkBigSprite(String linkBigSprite) {
        this.linkBigSprite = linkBigSprite;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("nameFr", getNameFr());
            jsonObject.put("nameEn", getNameEn());
            jsonObject.put("shape", getShape().getName());
            jsonObject.put("type1", getType1().getName());
            jsonObject.put("type2", (getType2() != null ? getType2().getName() : "null"));
            jsonObject.put("color", getColor());
            jsonObject.put("height", getHeight());
            jsonObject.put("weight", getWeight());
            jsonObject.put("linkIcon", getLinkIcon());
            jsonObject.put("linkSmallSprite", getLinkSmallSprite());
            jsonObject.put("linkBigSprite", getLinkBigSprite());
        } catch (JSONException e) {
            e.printStackTrace();
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
