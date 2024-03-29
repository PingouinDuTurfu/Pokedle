package fr.project.pokedle.game.classic_game;

import fr.project.pokedle.persistence.data.Pokemon;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClassicGameTry {
    private final boolean same;
    private final Map<String, Column> mapCompare;

    public ClassicGameTry(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        this.mapCompare = new HashMap<>();
        this.same = (pokemonToTry.getId() == pokemonToFind.getId());
        this.mapCompare.put("type", compareType(pokemonToTry, pokemonToFind));
        this.mapCompare.put("shape", compareShape(pokemonToTry, pokemonToFind));
        this.mapCompare.put("color", compareColor(pokemonToTry, pokemonToFind));
        this.mapCompare.put("weight", compareWeight(pokemonToTry, pokemonToFind));
        this.mapCompare.put("height", compareHeight(pokemonToTry, pokemonToFind));
        this.mapCompare.put("generation", compareGeneration(pokemonToTry, pokemonToFind));
    }

    public Column compareType(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        if (pokemonToFind.getTypes().equals(pokemonToTry.getTypes()))
            return Column.VALID;
        if (pokemonToTry.getTypes().stream().anyMatch(pokemonToFind.getTypes()::contains))
            return Column.PARTIAL;
        return Column.INVALID;
    }

    public Column compareShape(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        if (pokemonToTry.getShape().equals(pokemonToFind.getShape()))
            return Column.VALID;
        return Column.INVALID;
    }

    public Column compareColor(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        if (pokemonToTry.getColor().equals(pokemonToFind.getColor()))
            return Column.VALID;
        return Column.INVALID;
    }

    public Column compareWeight(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        if (pokemonToTry.getWeight() > pokemonToFind.getWeight())
            return Column.LOWER;
        if (pokemonToTry.getWeight() < pokemonToFind.getWeight())
            return Column.UPPER ;
        return Column.VALID;
    }

    public Column compareHeight(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        if (pokemonToTry.getHeight() > pokemonToFind.getHeight())
            return Column.LOWER;
        if (pokemonToTry.getHeight() < pokemonToFind.getHeight())
            return Column.UPPER;
        return Column.VALID;
    }

    public Column compareGeneration(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        if(pokemonToTry.getGeneration() == pokemonToFind.getGeneration())
            return Column.VALID;
        return Column.INVALID;
    }

    public boolean isSame() {
        return same;
    }

    public JSONObject toJSON() {
        return new JSONObject(mapCompare);
    }
}
