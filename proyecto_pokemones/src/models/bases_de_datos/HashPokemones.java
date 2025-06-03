package models.bases_de_datos;

import java.util.HashMap;
import models.pokemones.Pokemon;

public class HashPokemones {
    private HashMap<String, Pokemon> mapaPorNombre;

    public HashPokemones() {
        this.mapaPorNombre = new HashMap<>();
    }

    public void agregarPokemon(Pokemon pokemon) {
        mapaPorNombre.put(pokemon.getNombre(), pokemon);
    }

    public Pokemon buscarPorNombre(String nombre) {
        return mapaPorNombre.get(nombre);
    }

    public boolean contienePokemon(String nombre) {
        return mapaPorNombre.containsKey(nombre);
    }

    public void eliminarPokemon(String nombre) {
        mapaPorNombre.remove(nombre);
    }

    public void limpiarMapa() {
        mapaPorNombre.clear();
    }
}
