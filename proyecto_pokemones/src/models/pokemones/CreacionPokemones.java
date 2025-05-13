package models.pokemones;

import models.ataques.Ataque;
import java.util.ArrayList;
import java.util.List;

public class CreacionPokemones {

    public static Pokemon[] obtenerPokemonesDisponibles() {
        
        // Crear ataques
        Ataque latigoCepa = new Ataque("Látigo Cepa", 10, "Especial", "Planta");
        Ataque lanzallamas = new Ataque("Lanzallamas", 10, "Especial", "Fuego");
        Ataque trueno = new Ataque("Trueno", 10, "Especial", "Electrico");
        Ataque hidrobomba = new Ataque("Hidrobomba", 10, "Especial", "Agua");
        Ataque rayoHielo = new Ataque("Rayo Hielo", 10, "Especial", "Hielo");
        Ataque terremoto = new Ataque("Terremoto", 10, "Especial", "Tierra");

        // Crear Pokémon con esos ataques y atributos completos
        return new Pokemon[]{
            new Pokemon("Roselia", 100, Pokemon.TipoPokemon.PLANTA, List.of(new Ataque(latigoCepa)), 50, 45, 65, 50, 60),
            new Pokemon("Charmander", 100, Pokemon.TipoPokemon.FUEGO, List.of(new Ataque(lanzallamas)), 52, 43, 60, 50, 65),
            new Pokemon("Pikachu", 100, Pokemon.TipoPokemon.ELECTRICO, List.of(new Ataque(trueno)), 55, 40, 50, 50, 90),
            new Pokemon("Squirtle", 100, Pokemon.TipoPokemon.AGUA, List.of(new Ataque(hidrobomba)), 48, 65, 50, 64, 43),
            new Pokemon("Articuno", 100, Pokemon.TipoPokemon.HIELO, List.of(new Ataque(rayoHielo)), 85, 100, 95, 125, 85),
            new Pokemon("Garchomp", 100, Pokemon.TipoPokemon.TIERRA, List.of(new Ataque(terremoto)), 130, 95, 80, 85, 102)
        };
    }

    // Este método clona un Pokémon para evitar referencias duplicadas en batalla
    public static Pokemon crearNuevoPokemon(Pokemon original) {
        List<Ataque> copiaAtaques = new ArrayList<>();
        for (Ataque ataque : original.getAtaques()) {
            copiaAtaques.add(new Ataque(ataque)); // Clonar cada ataque
        }

        return new Pokemon(
            original.getNombre(),
            original.getPuntos_de_salud(),
            original.getTipo(),
            copiaAtaques,
            original.getAtaque(),
            original.getDefensa(),
            original.getAtaqueEspecial(),
            original.getDefensaEspecial(),
            original.getVelocidad()
        );
    }
}