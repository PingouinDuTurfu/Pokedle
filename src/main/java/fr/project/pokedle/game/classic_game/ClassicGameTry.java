package fr.project.pokedle.game.classic_game;

import fr.project.pokedle.persistence.data.Pokemon;
import fr.project.pokedle.persistence.data.PokemonType;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClassicGameTry {
    private boolean same;

    private final Map<String, Column> mapCompare;


    public ClassicGameTry(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        this.mapCompare = new HashMap<>();
        this.same = (pokemonToTry.getId() == pokemonToFind.getId());
        this.mapCompare.put("type", compareType(pokemonToTry, pokemonToFind));
        this.mapCompare.put("shape", compareShape(pokemonToTry, pokemonToFind));
        this.mapCompare.put("color", compareColor(pokemonToTry, pokemonToFind));
        this.mapCompare.put("weight", compareWeight(pokemonToTry, pokemonToFind));
        this.mapCompare.put("height", compareHeight(pokemonToTry, pokemonToFind));
    }

    public Column compareType(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        Set<PokemonType> typesToTry = new HashSet<>(Set.of(
                pokemonToTry.getType1()
        ));
        if(pokemonToTry.getType2() != null)
            typesToTry.add(pokemonToTry.getType2());

        Set<PokemonType> typesToFind = new HashSet<>(Set.of(
                pokemonToFind.getType1()
        ));
        if(pokemonToFind.getType2() != null)
            typesToFind.add(pokemonToFind.getType2());

        if (typesToFind.containsAll(typesToTry))
            return Column.VALID;
        if (typesToTry.stream().anyMatch(typesToFind::contains))
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
