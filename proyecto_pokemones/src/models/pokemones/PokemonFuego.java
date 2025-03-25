package models.pokemones;
import models.ataques.Ataque;

public class PokemonFuego extends Pokemon{
    public PokemonFuego(String nombre, int puntos_de_salud, int ataques, Ataque ataque) {
        super(nombre, puntos_de_salud, ataques, TipoPokemon.FUEGO, ataque);
        this.ataque = ataque;
    }

}
