package controlador;

import java.util.Stack;

import models.entrenadores.Entrenador;
import models.pokemones.Pokemon;
import vista.Vista;


public class Batalla {

    private Entrenador jugador;
    private Entrenador rival;
    private Vista vista;
    private boolean turnoJugador;
    private Stack<String> historialMovimientos = new Stack<>();

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
    }

    public void realizarTurno(){
        if(turnoJugador){
            Pokemon pokemonActivoJuagador = jugador.obtenerPokemonActivo();
            Pokemon pokemonActivoRival = rival.obtenerPokemonActivo();
                
            vista.mostrarMensaje("\n--- Estado actual ---");
            vista.mostrarEstadoPokemon(jugador.getNombre(), pokemonActivoJuagador);
            vista.mostrarEstadoPokemon(rival.getNombre(), pokemonActivoRival);
    
            if (pokemonActivoJuagador.getVelocidad() >= pokemonActivoRival.getVelocidad()) {
                ejecutarTurno(jugador, pokemonActivoJuagador, pokemonActivoRival);
                vista.mostrarEstadoPokemon(rival.getNombre(), rival.obtenerPokemonActivo());
                    
                if (pokemonActivoRival.getPuntos_de_salud() > 0) {
                    ejecutarTurno(rival, pokemonActivoRival, pokemonActivoJuagador);
                    vista.mostrarEstadoPokemon(jugador.getNombre(), jugador.obtenerPokemonActivo());
                }
            } else {
                ejecutarTurno(rival, pokemonActivoRival, pokemonActivoJuagador);
                vista.mostrarEstadoPokemon(jugador.getNombre(), jugador.obtenerPokemonActivo());
                    
                if (pokemonActivoJuagador.getPuntos_de_salud() > 0) {
                    ejecutarTurno(jugador, pokemonActivoJuagador, pokemonActivoRival);
                    vista.mostrarEstadoPokemon(rival.getNombre(), rival.obtenerPokemonActivo());
                }
            }
                
            if (pokemonActivoRival.getPuntos_de_salud() <= 0) {
                vista.mostrarMensaje("\n" + pokemonActivoRival.getNombre() + " ha sido derrotado.");
                rival.getEquipo().remove(pokemonActivoRival);
                if (!rival.equipoDerrotado()) {
                    vista.mostrarMensaje(rival.getNombre() + " envía a su próximo Pokémon.");
                }
            }

            if (pokemonActivoJuagador.getPuntos_de_salud() <= 0) {
                vista.mostrarMensaje("\n" + pokemonActivoJuagador.getNombre() + " ha sido derrotado.");
                jugador.getEquipo().remove(pokemonActivoJuagador);
                if (!jugador.equipoDerrotado()) {
                    vista.mostrarMensaje(jugador.getNombre() + " envía a su próximo Pokémon.");
                }
            }
        
        }
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
            vista.mostrarMensaje("\n Historial de movimientos:");
            Stack<String> copia = (Stack<String>) historialMovimientos.clone();
            while (!copia.isEmpty()) {
                vista.mostrarMensaje(copia.pop());
            }
        }
    }
}