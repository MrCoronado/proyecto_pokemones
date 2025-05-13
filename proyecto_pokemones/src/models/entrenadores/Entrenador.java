package models.entrenadores;
import models.pokemones.Pokemon;
import java.util.ArrayList;
import java.util.List;

public class Entrenador {
    private String nombre;
    private List<Pokemon> equipo;

    public Entrenador(String nombre) {
        this.nombre = nombre;
        this.equipo = new ArrayList<>(); 
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Pokemon> getEquipo() {
        return equipo;
    }

    public void agregarPokemon(Pokemon pokemon) {
        equipo.add(pokemon); 
    }
    
    public Pokemon obtenerPokemonActivo(){ 
        if (!equipo.isEmpty() && equipo.get(0).getPuntos_de_salud() > 0) {
            return equipo.get(0);
        }
        return null;
    }

    public boolean equipoDerrotado() {
        actualizarEquipo(); // Elimina Pokémon con 0 HP
        return equipo.isEmpty();
    }

    private void actualizarEquipo() {
        equipo.removeIf(pokemon -> pokemon.getPuntos_de_salud() <= 0);
    }
}
