package fr.project.pokedle.persistence;


import javax.persistence.*;
import java.util.List;

@Entity(name = "PokemonShapes")
public class PokemonShape {

    @Id
    private long id;

    @Column
    private String name_en;

    @Column
    private String name_fr;

    @Column
    private String linkIcon;


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

    public String getLinkIcon() {
        return linkIcon;
    }

    public void setLinkIcon(String linkIcon) {
        this.linkIcon = linkIcon;
    }

}
