package models.pokemones;
import java.util.ArrayList;
import java.util.List;
import models.ataques.Ataque;

public class PokemonAgua extends Pokemon {
    public PokemonAgua(String nombre, int puntos_de_salud,List  <Ataque> ataques) {
        super(nombre, puntos_de_salud, TipoPokemon.AGUA, ataques);
    }
}
    

