package models.pokemones;
import models.ataques.Ataque;
import java.util.List;

public class PokemonPlanta extends Pokemon {
    public PokemonPlanta(String nombre, int puntos_de_salud, List<Ataque> ataques, int ataque, int defensa, int ataqueEspecial, int defensaEspecial, int velocidad) {
        super(nombre, puntos_de_salud, TipoPokemon.PLANTA, ataques, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad);
    }
}
