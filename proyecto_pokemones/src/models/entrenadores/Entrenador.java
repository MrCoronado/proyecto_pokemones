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
            Pokemon seleccionado = equipo.get(indice);
            if (seleccionado.getPuntos_de_salud() > 0) {
                return seleccionado;
            } else {
                System.out.println(seleccionado.getNombre() + " está debilitado. Elige otro Pokémon.");
                return null;
            }
        } else {
            System.out.println("Selección inválida.");
            return null;
        }
    }

    public boolean equipoDerrotado() {
        actualizarEquipo(); // Elimina Pokémon con 0 HP
        return equipo.isEmpty();
    }

    private void actualizarEquipo() {
        equipo.removeIf(pokemon -> pokemon.getPuntos_de_salud() <= 0);
    }
}
