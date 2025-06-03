package vista;

import models.ataques.Ataque;
import models.entrenadores.Entrenador;
import models.pokemones.CreacionPokemones;
import models.pokemones.Pokemon;
import controlador.Batalla;

import java.util.List;
import java.util.Scanner;

public class VistaConsola implements Vista {

    private Scanner scanner;

    public VistaConsola() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    @Override
    public void mostrarEstadoPokemon(String nombreEntrenador, Pokemon pokemon) {
        System.out.println(nombreEntrenador + ": " + pokemon.getNombre() + " (HP: " + pokemon.getPuntos_de_salud() + ")");
    }

    @Override
    public int pedirOpcionAtaque(Pokemon atacante) {
        System.out.println("Selecciona un ataque para " + atacante.getNombre() + ":");
        List<Ataque> ataques = atacante.getAtaques();
        for (int i = 0; i < ataques.size(); i++) {
            System.out.println((i + 1) + ". " + ataques.get(i).getNombre());
        }

        System.out.print("Opción: ");
        int opcion = scanner.nextInt() - 1;
        scanner.nextLine(); // limpiar buffer
        return opcion;
    }

    @Override
    public void mostrarVictoria(String nombreGanador) {
        System.out.println("\n🎉 ¡" + nombreGanador + " ha ganado la batalla! 🎉\n");
    }

    public static void main(String[] args) {
        VistaConsola vista = new VistaConsola();
        Scanner scanner = new Scanner(System.in);

        Pokemon[] disponibles = CreacionPokemones.obtenerPokemonesDisponibles();

        while (true) {
            System.out.print("Ingresa el nombre del primer entrenador: ");
            Entrenador jugador = new Entrenador(scanner.nextLine());

            System.out.print("Ingresa el nombre del segundo entrenador: ");
            Entrenador rival = new Entrenador(scanner.nextLine());

            jugador.asignarEquipoAleatorio(disponibles);
            rival.asignarEquipoAleatorio(disponibles);

            Batalla batalla = new Batalla(jugador, rival, vista);

            vista.mostrarMensaje("\n¡-----------------Bienvenido a la batalla Pokémon--------------------\n");
            vista.mostrarMensaje("Entrenador " + jugador.getNombre() + " 🆚 Entrenador " + rival.getNombre());

            vista.mostrarMensaje(jugador.getNombre() + " recibió su equipo:");
            jugador.getEquipo().forEach(p -> vista.mostrarMensaje(p.toString()));

            vista.mostrarMensaje(rival.getNombre() + " recibió su equipo:");
            rival.getEquipo().forEach(p -> vista.mostrarMensaje(p.toString()));

            vista.mostrarMensaje("\n¡⚔️ La batalla comienza! ⚔️\n");

            while (!jugador.equipoDerrotado() && !rival.equipoDerrotado()) {
                Pokemon p1 = jugador.obtenerPokemonActivo();
                Pokemon p2 = rival.obtenerPokemonActivo();

                int opcionJugador = vista.pedirOpcionAtaque(p1);
                int opcionRival = vista.pedirOpcionAtaque(p2); 

                if (p1.getVelocidad() >= p2.getVelocidad()) {
                    vista.mostrarMensaje(p1.atacar(p2, opcionJugador));
                    vista.mostrarEstadoPokemon(rival.getNombre(), p2);

                    if (p2.getPuntos_de_salud() > 0) {
                        vista.mostrarMensaje(p2.atacar(p1, opcionRival));
                        vista.mostrarEstadoPokemon(jugador.getNombre(), p1);
                    }
                } else {
                    vista.mostrarMensaje(p2.atacar(p1, opcionRival));
                    vista.mostrarEstadoPokemon(jugador.getNombre(), p1);

                    if (p1.getPuntos_de_salud() > 0) {
                        vista.mostrarMensaje(p1.atacar(p2, opcionJugador));
                        vista.mostrarEstadoPokemon(rival.getNombre(), p2);
                    }
                }

                if (p1.getPuntos_de_salud() <= 0) {
                    vista.mostrarMensaje(p1.getNombre() + " ha sido derrotado.");
                    jugador.getEquipo().remove(p1);
                }

                if (p2.getPuntos_de_salud() <= 0) {
                    vista.mostrarMensaje(p2.getNombre() + " ha sido derrotado.");
                    rival.getEquipo().remove(p2);
                }
            }

            if (jugador.equipoDerrotado()) {
                vista.mostrarVictoria(rival.getNombre());
            } else {
                vista.mostrarVictoria(jugador.getNombre());
            }

            System.out.print("¿Deseas jugar otra vez? (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (!respuesta.equals("s")) {
                System.out.println("\n👋 ¡Gracias por jugar! Hasta luego.");
                break;
            }

            System.out.println("\n========== Reiniciando la batalla ==========\n");
        }
    }
}
    
