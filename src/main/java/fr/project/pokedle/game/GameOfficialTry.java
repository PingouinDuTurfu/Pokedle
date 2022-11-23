package fr.project.pokedle.game;

import fr.project.pokedle.persistence.Pokemon;
import org.json.JSONException;
import org.json.JSONObject;

public class GameOfficialTry {
    boolean same;

    private final Column compareType;
    private final Column compareShape;
    private final Column compareColor;
    private final Column compareWeight;
    private final Column compareHeight;


    public GameOfficialTry(Pokemon pokemonToTry, Pokemon pokemonToFind) {
        this.same = (pokemonToTry.getId() == pokemonToFind.getId());
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

    public boolean isSame() {
        return same;
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

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("isSame", isSame());
            jsonObject.put("compareType", getCompareType().name());
            jsonObject.put("compareShape", getCompareShape().name());
            jsonObject.put("compareColor", getCompareColor().name());
            jsonObject.put("compareWeight", getCompareWeight().name());
            jsonObject.put("compareHeight", getCompareHeight().name());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
