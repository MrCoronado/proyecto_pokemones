package vista;

import models.ataques.Ataque;
import models.entrenadores.Entrenador;
import models.pokemones.CreacionPokemones;
import models.pokemones.Pokemon;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import controlador.Batalla;

public class VistaConsola implements Vista {

    private Scanner scanner;

    public VistaConsola() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public int pedirOpcionAtaque(Pokemon atacante) {
        System.out.println("Selecciona un ataque para " + atacante.getNombre() + ":");
        List<Ataque> ataques = atacante.getAtaques();
        for (int i = 0; i < ataques.size(); i++) {
            System.out.println((i + 1) + ". " + ataques.get(i).getNombre());
        }
        System.out.print("Opción: ");
        int opcion = scanner.nextInt() - 1;
        scanner.nextLine(); // Limpiar buffer
        return opcion;
    }

    public void mostrarEstadoPokemon(String nombreEntrenador, Pokemon pokemon) {
        System.out.println(nombreEntrenador + ": " + pokemon.getNombre() + " (HP: " + pokemon.getPuntos_de_salud() + ")");
    }

    public static void main(String[] args) {
        VistaConsola vista = new VistaConsola();

        while (true) {
            Scanner scanner = new Scanner(System.in);
            Pokemon[] disponibles = CreacionPokemones.obtenerPokemonesDisponibles();

            System.out.print("Ingresa el nombre del primer entrenador: ");
            Entrenador jugador = new Entrenador(scanner.nextLine());

            System.out.print("Ingresa el nombre del segundo entrenador: ");
            Entrenador rival = new Entrenador(scanner.nextLine());

            for (int i = 0; i < 3; i++) {
                jugador.agregarPokemon(CreacionPokemones.crearNuevoPokemon(disponibles[i]));
                rival.agregarPokemon(CreacionPokemones.crearNuevoPokemon(disponibles[i + 3]));
            }

            Batalla batalla = new Batalla(jugador, rival, vista);

            vista.mostrarMensaje("\n¡-----------------Bienvenido a la batalla Pokémon--------------------\n");
            vista.mostrarMensaje("Entrenador " + jugador.getNombre() + " 🆚 Entrenador " + rival.getNombre());

            vista.mostrarMensaje(jugador.getNombre() + " recibió su equipo:");
            for (Pokemon p : jugador.getEquipo()) {
                vista.mostrarMensaje(p.toString());
            }

            vista.mostrarMensaje(rival.getNombre() + " recibió su equipo:");
            for (Pokemon p : rival.getEquipo()) {
                vista.mostrarMensaje(p.toString());
            }

            vista.mostrarMensaje("\n¡⚔️ La batalla comienza! ⚔️\n");

            while (!jugador.equipoDerrotado() && !rival.equipoDerrotado()) {
                Pokemon atacante = jugador.obtenerPokemonActivo();
                Pokemon defensor = rival.obtenerPokemonActivo();

                int opcion = vista.pedirOpcionAtaque(atacante);
                String mensajeAtaque = atacante.atacar(defensor, opcion);
                vista.mostrarMensaje(mensajeAtaque);
                vista.mostrarEstadoPokemon(rival.getNombre(), defensor);

                if (!rival.equipoDerrotado()) {
                    Ataque ataqueRival = defensor.getAtaques().get(0);
                    String mensajeRival = ataqueRival.aplicarAtaque(defensor, atacante);
                    vista.mostrarMensaje(mensajeRival);
                    vista.mostrarEstadoPokemon(jugador.getNombre(), atacante);
                }
            }

            if (jugador.equipoDerrotado()) {
                vista.mostrarMensaje("\n🎉 ¡" + rival.getNombre() + " ha ganado la batalla! 🎉\n");
            } else {
                vista.mostrarMensaje("\n🎉 ¡" + jugador.getNombre() + " ha ganado la batalla! 🎉\n");
            }

            System.out.print("¿Deseas jugar otra vez? (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (!respuesta.equals("s")) {
                System.out.println("\n👋 ¡Gracias por jugar! Hasta luego.");
                break;
            }
            System.out.println("\n========== Reiniciando la batalla ==========");
        }
    }
}
