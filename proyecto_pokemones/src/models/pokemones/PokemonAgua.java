package models.pokemones;
import java.util.List;
import models.ataques.Ataque;

public class PokemonAgua extends Pokemon {
    public PokemonAgua(String nombre, int puntos_de_salud,List<Ataque> ataques, int ataque, int defensa, int ataqueEspecial, int defensaEspecial, int velocidad) {
        super(nombre, puntos_de_salud, TipoPokemon.AGUA, ataques, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad);
    }
}
    

