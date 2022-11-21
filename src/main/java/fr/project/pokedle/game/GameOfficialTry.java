package fr.project.pokedle.game;

import fr.project.pokedle.persistence.Pokemon;

import java.util.HashMap;
import java.util.Map;

public class GameOfficialTry {
    Pokemon pokemonToTry;
    Pokemon pokemonToFind;

    Map<PokemonFeature, Column> comparePokemon;

    boolean isSame;



    public GameOfficialTry(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        this.pokemonToTry = pokemonToTry;
        this.pokemonToFind = pokemonToFind;
        compare();

    }

    public void compare() {
        isSame = (pokemonToTry.getId() == pokemonToFind.getId());

        comparePokemon = new HashMap<>();

        comparePokemon.put(PokemonFeature.TYPE, compareType());
        comparePokemon.put(PokemonFeature.SHAPE, compareShape());
        comparePokemon.put(PokemonFeature.COLOR, compareColor());
        comparePokemon.put(PokemonFeature.WEIGHT, compareWeight());
        comparePokemon.put(PokemonFeature.HEIGHT, compareHeight());
    }

    public Column compareType() {
        Column resultat = Column.INVALIDE;
        if (pokemonToTry.getType1().equals(pokemonToFind.getType1()) || pokemonToTry.getType1().equals(pokemonToFind.getType2()))
            resultat = Column.PARTIAL;
        if (pokemonToFind.getType2() == null) {
            if (resultat.equals(Column.PARTIAL))
                resultat = Column.VALIDE;
        } else {
            if (pokemonToTry.getType2().equals(pokemonToFind.getType1()) || pokemonToTry.getType2().equals(pokemonToFind.getType2()))
                resultat = Column.VALIDE;
        }
        return resultat;
    }

    public Column compareShape() {
        if (pokemonToTry.getShape().equals(pokemonToFind.getShape()))
            return Column.VALIDE;
        else
            return Column.INVALIDE;
    }

    public Column compareColor() {
        if (pokemonToTry.getColor().equals(pokemonToFind.getColor()))
            return Column.VALIDE;
        else
            return Column.INVALIDE;
    }

    public Column compareWeight() {
        if (pokemonToTry.getWeight() > pokemonToFind.getWeight())
            return Column.LOWER;
        else if (pokemonToTry.getWeight() < pokemonToFind.getWeight())
            return Column.UPPER;
        else
            return Column.VALIDE;
    }

    public Column compareHeight() {
        if (pokemonToTry.getHeight() > pokemonToFind.getHeight())
            return Column.LOWER;
        else if (pokemonToTry.getHeight() < pokemonToFind.getHeight())
            return Column.UPPER;
        else
            return Column.VALIDE;
    }

    @Override
    public String toString() {
        return "GameOfficialTry{" + comparePokemon + '}';
    }
}
