package fr.project.pokedle.persistence;

import javax.persistence.*;

@Entity(name = "Pokemons")
public class Pokemon {

    @Id
    @Column(name = "pokemon_id", updatable = false, nullable = false)
    private long id;

    @Column
    private String name_en;

    @Column
    private String name_fr;


    @ManyToOne
    @JoinColumn(name="type_1_id", nullable=false)
    private PokemonType type1;


    @ManyToOne
    @JoinColumn(name="type_2_id", nullable = false, insertable = false, updatable = false)
    private PokemonType type2;

    @ManyToOne
    @JoinColumn(name="shape_id", nullable = false, insertable = false, updatable = false)
    private PokemonType shape;


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

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getName_fr() {
        return name_fr;
    }

    public void setName_fr(String name_fr) {
        this.name_fr = name_fr;
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

    public PokemonType getShape() {
        return shape;
    }

    public void setShape(PokemonType shape) {
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
}
