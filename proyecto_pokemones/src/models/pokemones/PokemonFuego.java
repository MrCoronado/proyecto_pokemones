package models.pokemones;
import java.util.ArrayList;
import java.util.List;
import models.ataques.Ataque;

public class PokemonFuego extends Pokemon {
    public PokemonFuego(String nombre, int puntos_de_salud, List<Ataque> ataques) {
        super(nombre, puntos_de_salud, TipoPokemon.FUEGO, ataques);
    }
}
