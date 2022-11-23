package fr.project.pokedle.game;

import fr.project.pokedle.persistence.Pokemon;

public class PokemonsDifferences {

    private final Column compareType;
    private final Column compareShape;
    private final Column  compareColor;
    private final Column  compareWeight;
    private final Column  compareHeight;

    public PokemonsDifferences(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        this.compareType = compareType(pokemonToTry, pokemonToFind);
        this.compareShape = compareShape(pokemonToTry, pokemonToFind);
        this.compareColor = compareColor(pokemonToTry, pokemonToFind);
        this.compareWeight = compareWeight(pokemonToTry, pokemonToFind);
        this.compareHeight = compareHeight(pokemonToTry, pokemonToFind);
    }


    public Column compareType(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        Column resultat;
        if (pokemonToTry.getType1().equals(pokemonToFind.getType1()) || (pokemonToFind.getType2() != null && pokemonToTry.getType1().equals(pokemonToFind.getType2())))
            resultat = Column.PARTIAL;
        else
            resultat = Column.INVALIDE;
        if (pokemonToTry.getType2() == null) {
            if (resultat.equals(Column.PARTIAL))
                resultat = Column.VALIDE;
        } else if (pokemonToTry.getType2().equals(pokemonToFind.getType1()) || (pokemonToFind.getType2() != null && pokemonToTry.getType2().equals(pokemonToFind.getType2())))
                resultat = Column.VALIDE;
        return resultat;
    }

    public Column compareShape(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        if (pokemonToTry.getShape().equals(pokemonToFind.getShape()))
            return Column.VALIDE;
        else
            return Column.INVALIDE;
    }

    public Column compareColor(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        if (pokemonToTry.getColor().equals(pokemonToFind.getColor()))
            return Column.VALIDE;
        else
            return Column.INVALIDE;
    }

    public Column compareWeight(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        if (pokemonToTry.getWeight() > pokemonToFind.getWeight())
            return Column.LOWER;
        else if (pokemonToTry.getWeight() < pokemonToFind.getWeight())
            return Column.UPPER ;
        else
            return Column.VALIDE;
    }

    public Column compareHeight(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        if (pokemonToTry.getHeight() > pokemonToFind.getHeight())
            return Column.LOWER;
        else if (pokemonToTry.getHeight() < pokemonToFind.getHeight())
            return Column.UPPER;
        else
            return Column.VALIDE;
    }

    public Column getCompareType() {
        return compareType;
    }

    public Column getCompareShape() {
        return compareShape;
    }

    public Column getCompareColor() {
        return compareColor;
    }

    public Column getCompareWeight() {
        return compareWeight;
    }

    public Column getCompareHeight() {
        return compareHeight;
    }
}
