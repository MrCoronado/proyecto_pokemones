package models.pokemones;
import models.ataques.Ataque;

public class PokemonFuego extends Pokemon{
    private Ataque ataque;

    //constructor
    public PokemonFuego(String nombre, int puntos_de_salud, int ataques, Ataque ataque) {
        super(nombre, puntos_de_salud, ataques);
        this.ataque = ataque;
    }


    @Override
    public void atacar(Pokemon enemigo) {
        ataque.ejecutar(this,enemigo);        
    }


}
