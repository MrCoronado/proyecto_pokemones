package controlador;
import models.ataques.Ataque;
import models.entrenadores.Entrenador;
import models.pokemones.CreacionPokemones;
import models.pokemones.Pokemon;
import models.pokemones.Pokemon.TipoPokemon;
import models.batalla.Batalla;

import java.util.*;


public class App {
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
        Batalla batalla = new Batalla(jugador, rival);
        batalla.iniciar(scanner);

        scanner.close();
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

            entrenador.agregarPokemon(crearNuevoPokemon(disponibles[indice]));
        }

        System.out.println(entrenador.getNombre() + " ha recibido su equipo aleatorio:");
        for (Pokemon p : entrenador.getEquipo()) {
            System.out.println("- " + p.getNombre());
        }
    }

}
