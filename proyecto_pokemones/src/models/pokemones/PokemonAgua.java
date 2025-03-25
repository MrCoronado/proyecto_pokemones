package models.pokemones;
import models.ataques.Ataque;

public class PokemonAgua  extends Pokemon{
    public PokemonAgua(String nombre, int puntos_de_salud, int ataques, Ataque ataque) {
        super(nombre, puntos_de_salud, ataques, TipoPokemon.AGUA, ataque);
        this.ataque = ataque;
    }
    
}
