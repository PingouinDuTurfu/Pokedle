package fr.project.pokedle.persistence;


import javax.persistence.*;

@Entity(name = "PokemonShapes")
public class PokemonShape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "shape_id")
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

    @Override
    public String toString() {
        return "PokemonShape{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", linkIcon='" + linkIcon + '\'' +
                '}';
    }
}
