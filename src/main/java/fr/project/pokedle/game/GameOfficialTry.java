package fr.project.pokedle.game;

import fr.project.pokedle.persistence.data.Pokemon;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GameOfficialTry {
    private boolean same;

    private final Map<String, Column> mapCompare;


    public GameOfficialTry(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        this.mapCompare = new HashMap<>();
        this.same = (pokemonToTry.getId() == pokemonToFind.getId());
        this.mapCompare.put("type", compareType(pokemonToTry, pokemonToFind));
        this.mapCompare.put("shape", compareShape(pokemonToTry, pokemonToFind));
        this.mapCompare.put("color", compareColor(pokemonToTry, pokemonToFind));
        this.mapCompare.put("weight", compareWeight(pokemonToTry, pokemonToFind));
        this.mapCompare.put("height", compareHeight(pokemonToTry, pokemonToFind));
    }

    public Column compareType(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        boolean type1Correct = (pokemonToTry.getType1().equals(pokemonToFind.getType1()) || pokemonToTry.getType1().equals(pokemonToFind.getType2()));
        if (pokemonToTry.getType2() == null) {
            if (type1Correct)
                return Column.VALIDE;
            return Column.INVALIDE;
        }
        boolean type2Correct = (pokemonToTry.getType2().equals(pokemonToFind.getType1()) || pokemonToTry.getType2().equals(pokemonToFind.getType2()));

        if (type1Correct && type2Correct)
            return Column.VALIDE;
        if (type1Correct || type2Correct)
            return Column.PARTIAL;
        return Column.INVALIDE;
    }

    public Column compareShape(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        if (pokemonToTry.getShape().equals(pokemonToFind.getShape()))
            return Column.VALIDE;
        return Column.INVALIDE;
    }

    public Column compareColor(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        if (pokemonToTry.getColor().equals(pokemonToFind.getColor()))
            return Column.VALIDE;
        return Column.INVALIDE;
    }

    public Column compareWeight(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        if (pokemonToTry.getWeight() > pokemonToFind.getWeight())
            return Column.LOWER;
        if (pokemonToTry.getWeight() < pokemonToFind.getWeight())
            return Column.UPPER ;
        return Column.VALIDE;
    }

    public Column compareHeight(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        if (pokemonToTry.getHeight() > pokemonToFind.getHeight())
            return Column.LOWER;
        if (pokemonToTry.getHeight() < pokemonToFind.getHeight())
            return Column.UPPER;
        return Column.VALIDE;
    }

    public boolean isSame() {
        return same;
    }

    public Map<String, Column> getMapCompare() {
        return mapCompare;
    }

    public JSONObject toJSON() {
        return new JSONObject(mapCompare);
    }
}
