package models.pokemones;
import models.ataques.Ataque;
import java.util.List;

public class PokemonHielo extends Pokemon {
    public PokemonHielo(String nombre, int puntos_de_salud, List<Ataque> ataques, int ataque, int defensa, int ataqueEspecial, int defensaEspecial, int velocidad) {
        super(nombre, puntos_de_salud, TipoPokemon.HIELO, ataques,ataque, defensa, ataqueEspecial, defensaEspecial, velocidad);

    }
}