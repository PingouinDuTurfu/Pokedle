package fr.project.pokedle.init.loader;

import java.util.List;

public class PokemonList {

    private List<PokemonItem> pokemons;

    public List<PokemonItem> getPokemon() {
        return pokemons;
    }

    public void setPokemon(List<PokemonItem> pokemon) {
        this.pokemons = pokemon;
    }

    public PokemonItem getPokemon(int id) {
        return pokemons.get(id);
    }
}
