package fr.project.pokedle.init.loader;

public class PokemonItem {

    private String name_en;
    private String id;
    private String type1;
    private String type2;
    private double height;
    private double weight;
    private String spriteHQ;
    private String spriteLQ;
    private String icon;
    private String shape;
    private String color;
    private String name_fr;

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
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

    public String getSpriteHQ() {
        return spriteHQ;
    }

    public void setSpriteHQ(String spriteHQ) {
        this.spriteHQ = spriteHQ;
    }

    public String getSpriteLQ() {
        return spriteLQ;
    }

    public void setSpriteLQ(String spriteLQ) {
        this.spriteLQ = spriteLQ;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName_fr() {
        return name_fr;
    }

    public void setName_fr(String name_fr) {
        this.name_fr = name_fr;
    }

    @Override
    public String toString() {
        return "PokemonItem{" +
                "name_en='" + name_en + '\'' +
                ", id='" + id + '\'' +
                ", type1='" + type1 + '\'' +
                ", type2='" + type2 + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", spriteHQ='" + spriteHQ + '\'' +
                ", spriteLQ='" + spriteLQ + '\'' +
                ", icon='" + icon + '\'' +
                ", shape='" + shape + '\'' +
                ", color='" + color + '\'' +
                ", name_fr='" + name_fr + '\'' +
                '}';
    }
}
