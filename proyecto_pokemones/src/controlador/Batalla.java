package controlador;

import java.util.HashMap;
import java.util.Stack;

import models.bases_de_datos.HashPokemones;
import models.bases_de_datos.ListaTurnos;
import models.entrenadores.Entrenador;
import models.pokemones.Pokemon;
import vista.Vista;

public class Batalla {

    private Entrenador jugador;
    private Entrenador rival;
    private Vista vista;
    private boolean turnoJugador;
    private Stack<String> historialMovimientos = new Stack<>();
    private ListaTurnos listaTurnos = new ListaTurnos();
    private HashPokemones hashPokemones = new HashPokemones();

    public Batalla(Entrenador jugador, Entrenador rival, Vista vista) {
        this.jugador = jugador;
        this.rival = rival;
        this.vista = vista;
        this.turnoJugador = true;
    }

    public void iniciar() {
        vista.mostrarMensaje("\n¡Comienza la batalla Pokémon!\n");

        Pokemon p1 = jugador.obtenerPokemonActivo();
        Pokemon p2 = rival.obtenerPokemonActivo();

        vista.mostrarMensaje("--- Estado inicial ---");
        vista.mostrarEstadoPokemon(jugador.getNombre(), p1);
        vista.mostrarEstadoPokemon(rival.getNombre(), p2);

        cargarPokemonesEnHash();
    }
    
    private void cargarPokemonesEnHash() {
        for (Pokemon p : jugador.getEquipo()) {
            hashPokemones.agregarPokemon(p);
        }
        for (Pokemon p : rival.getEquipo()) {
            hashPokemones.agregarPokemon(p);
        }
    }

    public Pokemon buscarPokemonPorNombre(String nombre) {
        return hashPokemones.buscarPorNombre(nombre);
    }

    public void realizarTurno() {
        if (turnoJugador) {
            Pokemon pokemonJugador = jugador.obtenerPokemonActivo();
            Pokemon pokemonRival = rival.obtenerPokemonActivo();

            vista.mostrarMensaje("\n--- Estado actual ---");
            vista.mostrarEstadoPokemon(jugador.getNombre(), pokemonJugador);
            vista.mostrarEstadoPokemon(rival.getNombre(), pokemonRival);

            // Reinicia y llena la lista enlazada con los Pokémon activos
            listaTurnos = new ListaTurnos();
            listaTurnos.agregarPorVelocidad(pokemonJugador);
            listaTurnos.agregarPorVelocidad(pokemonRival);

            // Ejecuta los ataques según el orden en la lista
            while (!listaTurnos.estaVacia()) {
                Pokemon atacante = listaTurnos.removerPrimero();
                Entrenador dueño = jugador.tienePokemon(atacante) ? jugador : rival;
                Entrenador oponente = (dueño == jugador) ? rival : jugador;
                Pokemon defensor = oponente.obtenerPokemonActivo();

                if (atacante.getPuntos_de_salud() > 0 && defensor.getPuntos_de_salud() > 0) {
                    ejecutarTurno(dueño, atacante, defensor);
                    vista.mostrarEstadoPokemon(oponente.getNombre(), defensor);
                }
            }

            // Verifica si un Pokémon ha sido derrotado
            if (pokemonRival.getPuntos_de_salud() <= 0) {
                vista.mostrarMensaje("\n" + pokemonRival.getNombre() + " ha sido derrotado.");
                rival.getEquipo().remove(pokemonRival);
                if (!rival.equipoDerrotado()) {
                    vista.mostrarMensaje(rival.getNombre() + " envía a su próximo Pokémon.");
                }
            }

            if (pokemonJugador.getPuntos_de_salud() <= 0) {
                vista.mostrarMensaje("\n" + pokemonJugador.getNombre() + " ha sido derrotado.");
                jugador.getEquipo().remove(pokemonJugador);
                if (!jugador.equipoDerrotado()) {
                    vista.mostrarMensaje(jugador.getNombre() + " envía a su próximo Pokémon.");
                }
            }
        }

        // Verifica si la batalla ha terminado
        if (jugador.equipoDerrotado()) {
            vista.mostrarVictoria(rival.getNombre());
            mostrarHistorialDeMovimientos();
        } else if (rival.equipoDerrotado()) {
            vista.mostrarVictoria(jugador.getNombre());
            mostrarHistorialDeMovimientos();
        }
    }

    private void ejecutarTurno(Entrenador entrenador, Pokemon atacante, Pokemon defensor) {
        int opcion = vista.pedirOpcionAtaque(atacante);
        if (opcion >= 0 && opcion < atacante.getAtaques().size()) {
            String resultado = atacante.atacar(defensor, opcion);
            vista.mostrarMensaje(resultado);
            historialMovimientos.push(atacante.getNombre() + " usa " + atacante.getAtaques().get(opcion).getNombre() + " contra " + defensor.getNombre());
        } else {
            vista.mostrarMensaje("Selección inválida. Se pierde el turno.");
        }
    }

    public void mostrarHistorialDeMovimientos() {
        if (historialMovimientos.isEmpty()) {
            vista.mostrarMensaje("No hay movimientos registrados.");
        } else {
            vista.mostrarMensaje("\nHistorial de movimientos:");
            Stack<String> copia = (Stack<String>) historialMovimientos.clone();
            while (!copia.isEmpty()) {
                vista.mostrarMensaje(copia.pop());
            }
        }
    }
}
