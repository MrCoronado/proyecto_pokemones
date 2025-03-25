import models.ataques.Ataque;
import models.entrenadores.Entrenador;
import models.pokemones.*;
import java.util.*;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Ataques
        Ataque latigoCepa = new Ataque("Látigo Cepa", 10, "Físico", "Planta");
        Ataque lanzallamas = new Ataque("Lanzallamas", 10, "Especial", "Fuego");
        Ataque trueno = new Ataque("Trueno", 10, "Especial", "Electrico");
        Ataque hidrobomba = new Ataque("Hidrobomba", 10, "Especial", "Agua");
        Ataque rayoHielo = new Ataque("Rayo Hielo", 10, "Especial", "Hielo");
        Ataque terremoto = new Ataque("Terremoto", 10, "Físico", "Tierra");

        // Pokemones con listas de ataques
        Pokemon[] disponibles = {
            new PokemonPlanta("Roselia", 100, List.of(latigoCepa)),
            new PokemonFuego("Charmander", 100, List.of(lanzallamas)),
            new PokemonElectrico("Pikachu", 100, List.of(trueno)),
            new PokemonAgua("Squirtle", 100, List.of(hidrobomba)),
            new PokemonHielo("Articuno", 100, List.of(rayoHielo)),
            new PokemonTierra("Garchomp", 100, List.of(terremoto))
        };

        System.out.print("Ingresa el nombre del primer entrenador: ");
        String nombreJugador = scanner.nextLine();
        Entrenador jugador = new Entrenador(nombreJugador);
        seleccionarEquipo(scanner, jugador, disponibles);

        System.out.print("Ingresa el nombre del segundo entrenador: ");
        String nombreRival = scanner.nextLine();
        Entrenador rival = new Entrenador(nombreRival);
        seleccionarEquipo(scanner, rival, disponibles);

        System.out.println("\n¡Comienza la batalla Pokémon!\n");

        while (!jugador.equipoDerrotado() && !rival.equipoDerrotado()) {
            Pokemon pokemonJugador = jugador.getEquipo().get(0);
            Pokemon pokemonRival = rival.getEquipo().get(0);

            System.out.println("\n--- Estado actual ---");
            System.out.println(jugador.getNombre() + ": " + pokemonJugador.getNombre() + " (HP: " + pokemonJugador.getPuntos_de_salud() + ")");
            System.out.println(rival.getNombre() + ": " + pokemonRival.getNombre() + " (HP: " + pokemonRival.getPuntos_de_salud() + ")\n");

            ejecutarTurno(scanner, jugador.getNombre(), pokemonJugador, pokemonRival);
            if (pokemonRival.getPuntos_de_salud() <= 0) {
                System.out.println("\n" + pokemonRival.getNombre() + " ha sido derrotado.");
                rival.getEquipo().remove(pokemonRival);
                if (!rival.equipoDerrotado()) {
                    System.out.println(rival.getNombre() + " envía a su próximo Pokémon.");
                }
            }

            if (rival.equipoDerrotado()) break;

            ejecutarTurno(scanner, rival.getNombre(), pokemonRival, pokemonJugador);
            if (pokemonJugador.getPuntos_de_salud() <= 0) {
                System.out.println("\n" + pokemonJugador.getNombre() + " ha sido derrotado.");
                jugador.getEquipo().remove(pokemonJugador);
                if (!jugador.equipoDerrotado()) {
                    System.out.println(jugador.getNombre() + " envía a su próximo Pokémon.");
                }
            }
        }

        if (jugador.equipoDerrotado()) {
            System.out.println("\n¡" + rival.getNombre() + " gana la batalla!");
        } else {
            System.out.println("\n¡" + jugador.getNombre() + " gana la batalla!");
        }

        scanner.close();
    }

    public static void seleccionarEquipo(Scanner scanner, Entrenador entrenador, Pokemon[] disponibles) {
        System.out.println("\n" + entrenador.getNombre() + ", selecciona 3 Pokémon:");
        for (int i = 0; i < disponibles.length; i++) {
            System.out.println((i + 1) + ". " + disponibles[i].getNombre());
        }
        
        while (entrenador.getEquipo().size() < 3) {
            System.out.print("Ingresa el número de un Pokémon: ");
            int eleccion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer después de nextInt()
            if (eleccion >= 1 && eleccion <= disponibles.length) {
                entrenador.agregarPokemon(crearNuevoPokemon(disponibles[eleccion - 1]));
            } else {
                System.out.println("Selección inválida.");
            }
        }
    }

    public static void ejecutarTurno(Scanner scanner, String entrenador, Pokemon atacante, Pokemon defensor) {
        System.out.println("\nTurno de " + entrenador + " con " + atacante.getNombre());
        System.out.println("Elige un ataque:");
        for (int i = 0; i < atacante.getAtaques().size(); i++) {
            System.out.println((i + 1) + ". " + atacante.getAtaques().get(i).getNombre());
        }
        System.out.print("Opción: ");
        int ataqueElegido = scanner.nextInt() - 1;
        scanner.nextLine();
        
        if (ataqueElegido >= 0 && ataqueElegido < atacante.getAtaques().size()) {
            atacante.atacar(defensor, ataqueElegido);
        } else {
            System.out.println("Selección inválida. Se pierde el turno.");
        }
    }

    public static Pokemon crearNuevoPokemon(Pokemon original) {
        return switch (original.getTipo()) {
            case PLANTA -> new PokemonPlanta(original.getNombre(), original.getPuntos_de_salud(), original.getAtaques());
            case FUEGO -> new PokemonFuego(original.getNombre(), original.getPuntos_de_salud(), original.getAtaques());
            case ELECTRICO -> new PokemonElectrico(original.getNombre(), original.getPuntos_de_salud(), original.getAtaques());
            case AGUA -> new PokemonAgua(original.getNombre(), original.getPuntos_de_salud(), original.getAtaques());
            case HIELO -> new PokemonHielo(original.getNombre(), original.getPuntos_de_salud(), original.getAtaques());
            case TIERRA -> new PokemonTierra(original.getNombre(), original.getPuntos_de_salud(), original.getAtaques());
        };
    }
}
