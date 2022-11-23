package fr.project.pokedle.game;


public class JSONPokemonDifference {
    private final String compareType;
    private final String compareShape;
    private final String  compareColor;
    private final String  compareWeight;
    private final String  compareHeight;

    public JSONPokemonDifference(PokemonsDifferences pokemonsDifferences) {
        this.compareType = pokemonsDifferences.getCompareType().name();
        this.compareShape = pokemonsDifferences.getCompareShape().name();
        this.compareColor = pokemonsDifferences.getCompareColor().name();
        this.compareWeight = pokemonsDifferences.getCompareWeight().name();
        this.compareHeight = pokemonsDifferences.getCompareHeight().name();
    }

    public String getCompareType() {
        return compareType;
    }

    public String getCompareShape() {
        return compareShape;
    }

    public String getCompareColor() {
        return compareColor;
    }

    public String getCompareWeight() {
        return compareWeight;
    }

    public String getCompareHeight() {
        return compareHeight;
    }
}
