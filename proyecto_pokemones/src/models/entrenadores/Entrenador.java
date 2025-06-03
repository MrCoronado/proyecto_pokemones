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

    public Pokemon obtenerPokemonActivo() {
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

    public void asignarEquipoAleatorio(Pokemon[] disponibles) {
        int[] indicesUsados = new int[disponibles.length];
        int contador = 0;

        while (contador < 3) {
            int indice = (int)(Math.random() * disponibles.length);
            boolean repetido = false;

            for (int i = 0; i < contador; i++) {
                if (indicesUsados[i] == indice) {
                    repetido = true;
                    break;
                }
            }

            if (!repetido) {
                indicesUsados[contador++] = indice;
                agregarPokemon(models.pokemones.CreacionPokemones.crearNuevoPokemon(disponibles[indice]));
            }
        }
    }
    // Verifica si el entrenador tiene un Pokémon específico en su equipo
    public boolean tienePokemon(Pokemon p) {
        return equipo.contains(p);
    } 
}


