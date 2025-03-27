package models.pokemones;
import models.ataques.Ataque;
import java.util.ArrayList;
import java.util.List;

public class PokemonPlanta extends Pokemon {
    public PokemonPlanta(String nombre, int puntos_de_salud, List<Ataque> ataques) {
        super(nombre, puntos_de_salud, TipoPokemon.PLANTA, ataques);
    }
}
