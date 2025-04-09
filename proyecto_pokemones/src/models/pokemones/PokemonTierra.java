package models.pokemones;
import models.ataques.Ataque;
import java.util.ArrayList;
import java.util.List;

public class PokemonTierra extends Pokemon {
    public PokemonTierra(String nombre, int puntos_de_salud, List<Ataque> ataques) {
        super(nombre, puntos_de_salud, TipoPokemon.TIERRA, ataques);
        
    }
}