package vista;


import models.ataques.Ataque;
import models.entrenadores.Entrenador;
import models.pokemones.CreacionPokemones;
import models.pokemones.Pokemon;


//import models.pokemones.Pokemon.TipoPokemon;

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
        scanner.nextLine(); // aca Limpia el buffer
        return opcion;
    }

    public void mostrarEstadoPokemon(String nombreEntrenador, Pokemon pokemon) {
        System.out.println(nombreEntrenador + ": " + pokemon.getNombre() + " (HP: " + pokemon.getPuntos_de_salud() + ")");
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pokemon[] disponibles = CreacionPokemones.obtenerPokemonesDisponibles();

        // Entrenadores
        System.out.print("Ingresa el nombre del primer entrenador: ");
        Entrenador jugador = new Entrenador(scanner.nextLine());
        seleccionarEquipoAleatorio(jugador, disponibles);

        System.out.print("Ingresa el nombre del segundo entrenador: ");
        Entrenador rival = new Entrenador(scanner.nextLine());
        seleccionarEquipoAleatorio(rival, disponibles);

        // Iniciar batalla
        VistaConsola vistaConsola = new VistaConsola();
        Batalla batalla = new Batalla(jugador, rival, vistaConsola);
        batalla.iniciar();

        //scanner.close();
    }

    // Método para seleccionar un equipo aleatorio de Pokémon
    // para un entrenador, asegurando que no se repitan
    public static void seleccionarEquipoAleatorio(Entrenador entrenador, Pokemon[] disponibles) { 
        List<Integer> indicesUsados = new ArrayList<>();
        Random random = new Random();

        while (entrenador.getEquipo().size() < 3) {
            int indice;
            do {
                indice = random.nextInt(disponibles.length);
            } while (indicesUsados.contains(indice));
            indicesUsados.add(indice);

            entrenador.agregarPokemon(CreacionPokemones.crearNuevoPokemon(disponibles[indice]));
        }

        System.out.println(entrenador.getNombre() + " ha recibido su equipo aleatorio:");
        for (Pokemon p : entrenador.getEquipo()) {
            System.out.println("- " + p.getNombre());
        }
    }

}
