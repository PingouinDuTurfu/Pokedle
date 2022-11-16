package fr.project.pokedle.persistence;

public enum PokemonType {
    STEEL("Steel","Acier"),
    GHOST("Ghost","Spectre"),
    DRAGON("Dragon","Dragon"),
    DARK("Dark","Ténèbres"),
    FAIRY("Fairy","Fée"),
    PSYCHIC("Psychic","Psy"),
    FIGHTING("Fighting","Combat"),
    ROCK("Rock","Roche"),
    GROUND("Ground","Sol"),
    ICE("Ice","Glace"),
    FLYING("Flying","Vol"),
    WATER("Water","Eau"),
    GRASS("Grass","Plante"),
    ELECTRIC("Electric","Electrik"),
    POISON("Poison","Poison"),
    BUG("Bug","Insecte"),
    FIRE("Fire","Feu"),
    NORMAL("Normal","Normal");

    private final String english;
    private final String french;

    PokemonType(String english, String french) {
        this.english = english;
        this.french = french;
    }

    public String getEnglish() {
        return english;
    }

    public String getFrench() {
        return french;
    }
}
