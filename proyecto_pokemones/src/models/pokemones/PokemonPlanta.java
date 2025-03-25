package models.pokemones;
import models.ataques.Ataque;

public class PokemonPlanta extends Pokemon{
    public PokemonPlanta(String nombre, int puntos_de_salud, int ataques, Ataque ataque){
        super(nombre, puntos_de_salud, ataques, TipoPokemon.PLANTA, ataque);
        this.ataque= ataque;
    }

}

