package models.pokemones;
import models.ataques.Ataque;

public class PokemonElectrico extends Pokemon{
    public PokemonElectrico(String nombre, int puntos_de_salud, int ataques, Ataque ataque) {
        super(nombre, puntos_de_salud, ataques, TipoPokemon.ELECTRICO, ataque);
        this.ataque = ataque;
    }

}