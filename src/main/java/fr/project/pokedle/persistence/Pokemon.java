package fr.project.pokedle.persistence;

import javax.persistence.*;

@Entity(name = "Pokemons")
public class Pokemon {

    @Id
    @Column(name="pokemon_id")
    private long id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private PokemonType type1;

    @Column
    @Enumerated(EnumType.STRING)
    private PokemonType type2;

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

    public long getIdentifier() {
        return id;
    }

    public void setIdentifier(long identifier) {
        this.id = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
