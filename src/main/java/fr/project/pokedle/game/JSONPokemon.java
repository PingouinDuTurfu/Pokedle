package fr.project.pokedle.game;

import fr.project.pokedle.persistence.Pokemon;

public class JSONPokemon {
    private final long id;
    private final String nameFr;
    private final String nameEn;
    private final String shape;
    private final String type1;
    private final String type2;
    private final double height;
    private final double weight;
    private final String linkIcon;
    private final String linkSmallSprite;
    private final String linkBigSprite;
    private final String color;



    public JSONPokemon(Pokemon pokemon) {
        this.id = pokemon.getId();
        this.nameFr = pokemon.getNameFr();
        this.nameEn = pokemon.getNameEn();
        this.shape = pokemon.getShape().getName();
        this.type1 = pokemon.getType1().getName();
        this.type2 = pokemon.getType2() != null ? pokemon.getType2().getName() : "";
        this.height = pokemon.getHeight();
        this.weight = pokemon.getWeight();
        this.linkIcon = pokemon.getLinkIcon();
        this.linkSmallSprite = pokemon.getLinkSmallSprite();
        this.linkBigSprite = pokemon.getLinkBigSprite();
        this.color = pokemon.getColor();
    }

    public long getId() {
        return id;
    }

    public String getNameFr() {
        return nameFr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getShape() {
        return shape;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public String getLinkIcon() {
        return linkIcon;
    }

    public String getLinkSmallSprite() {
        return linkSmallSprite;
    }

    public String getLinkBigSprite() {
        return linkBigSprite;
    }

    public String getColor() {
        return color;
    }
}
