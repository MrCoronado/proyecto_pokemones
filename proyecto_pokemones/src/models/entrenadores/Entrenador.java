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
        if (equipo.size() < 3) {
            equipo.add(pokemon);
        } else {
            System.out.println("El equipo ya tiene 3 Pokémon. No puedes agregar más.");
        }
    }

    public Pokemon elegirPokemon(int indice) {
        if (indice >= 0 && indice < equipo.size()) {
            return equipo.get(indice);
        } else {
            System.out.println("Selección inválida.");
            return null;
        }
    }
    
    public boolean equipoDerrotado() {
        return equipo.isEmpty();
    }
}

