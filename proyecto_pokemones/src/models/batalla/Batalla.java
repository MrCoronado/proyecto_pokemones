package models.batalla;

import models.entrenadores.Entrenador;
import models.pokemones.Pokemon;
import vista.Vista;

public class Batalla {

    private Entrenador jugador;
    private Entrenador rival;
    private Vista vista;

    public Batalla(Entrenador jugador, Entrenador rival) {
        this.jugador = jugador;
        this.rival = rival;
        this.vista = new Vista();
    }

    public void iniciar() {
        vista.mostrarMensaje("\n¡Comienza la batalla Pokémon!\n");

        while (!jugador.equipoDerrotado() && !rival.equipoDerrotado()) {
            Pokemon p1 = jugador.getEquipo().get(0);
            Pokemon p2 = rival.getEquipo().get(0);

            vista.mostrarMensaje("\n--- Estado actual ---");
            vista.mostrarEstadoPokemon(jugador.getNombre(), p1);
            vista.mostrarEstadoPokemon(rival.getNombre(), p2);

            if (p1.getVelocidad() >= p2.getVelocidad()) {
                ejecutarTurno(jugador, p1, p2);
                if (p2.getPuntos_de_salud() > 0) ejecutarTurno(rival, p2, p1);
            } else {
                ejecutarTurno(rival, p2, p1);
                if (p1.getPuntos_de_salud() > 0) ejecutarTurno(jugador, p1, p2);
            }

            if (p2.getPuntos_de_salud() <= 0) {
                vista.mostrarMensaje("\n" + p2.getNombre() + " ha sido derrotado.");
                rival.getEquipo().remove(p2);
                if (!rival.equipoDerrotado()) {
                    vista.mostrarMensaje(rival.getNombre() + " envía a su próximo Pokémon.");
                }
            }

            if (p1.getPuntos_de_salud() <= 0) {
                vista.mostrarMensaje("\n" + p1.getNombre() + " ha sido derrotado.");
                jugador.getEquipo().remove(p1);
                if (!jugador.equipoDerrotado()) {
                    vista.mostrarMensaje(jugador.getNombre() + " envía a su próximo Pokémon.");
                }
            }
        }

        if (jugador.equipoDerrotado()) {
            vista.mostrarMensaje("\n¡" + rival.getNombre() + " gana la batalla!");
        } else {
            vista.mostrarMensaje("\n¡" + jugador.getNombre() + " gana la batalla!");
        }
    }

    private void ejecutarTurno(Entrenador entrenador, Pokemon atacante, Pokemon defensor) {
        int opcion = vista.pedirOpcionAtaque(atacante);
        if (opcion >= 0 && opcion < atacante.getAtaques().size()) {
            String resultado = atacante.atacar(defensor, opcion);
            vista.mostrarMensaje(resultado);
        } else {
            vista.mostrarMensaje("Selección inválida. Se pierde el turno.");
        }
    }
}