package models.pokemones;
import models.ataques.Ataque;
import java.util.ArrayList;
import java.util.List;

public class PokemonHielo extends Pokemon {
    public PokemonHielo(String nombre, int puntos_de_salud, List<Ataque> ataques) {
        super(nombre, puntos_de_salud, TipoPokemon.HIELO, ataques);

    }
}