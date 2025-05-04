package models.pokemones;
import java.util.List;
import models.ataques.Ataque;

public class PokemonElectrico extends Pokemon {
    public PokemonElectrico(String nombre, int puntos_de_salud, List<Ataque> ataques,int ataque, int defensa, int ataqueEspecial, int defensaEspecial, int velocidad) {
        super(nombre, puntos_de_salud, TipoPokemon.ELECTRICO, ataques,ataque, defensa, ataqueEspecial, defensaEspecial, velocidad);
    }
}

   