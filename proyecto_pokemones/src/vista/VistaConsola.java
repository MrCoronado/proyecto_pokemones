package vista;

import models.ataques.Ataque;
import models.entrenadores.Entrenador;
import models.pokemones.CreacionPokemones;
import models.pokemones.Pokemon;
import controlador.Batalla;
import excepciones.ExcepcionDeEntrenador;
import excepciones.ExcepcionDeAtaque;

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
        List<Ataque> ataques = atacante.getAtaques();

        while (true) {
            System.out.println("Selecciona un ataque para " + atacante.getNombre() + ":");
            for (int i = 0; i < ataques.size(); i++) {
                System.out.println((i + 1) + ". " + ataques.get(i).getNombre());
            }

            System.out.print("Opción: ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine()) - 1;

                if (opcion < 0 || opcion >= ataques.size()) {
                    throw new ExcepcionDeAtaque("❌ Opción inválida. Por favor elige un número entre 1 y " + ataques.size());
                }

                return opcion;
            } catch (NumberFormatException e) {
                System.out.println("❌ Debes ingresar un número válido.");
            } catch (ExcepcionDeAtaque e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void mostrarVictoria(String nombreGanador) {
        System.out.println("\n🎉 ¡" + nombreGanador + " ha ganado la batalla! 🎉\n");
    }

    // (El resto del método main sigue igual al anterior con la excepción del nombre del entrenador)
}

    
