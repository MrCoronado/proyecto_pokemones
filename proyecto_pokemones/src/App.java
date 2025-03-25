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
        

        // Pokemones con listas de ataques
        Pokemon[] disponibles = {
            new PokemonPlanta("Roselia", 100, List.of(latigoCepa)),
            new PokemonFuego("Charmander", 100, List.of(lanzallamas)),
            new PokemonElectrico("Pikachu", 100, List.of(trueno)),
            new PokemonAgua("Squirtle", 100, List.of(hidrobomba))
        };

        System.out.print("Ingresa el nombre del primer entrenador: ");
        String nombreJugador = scanner.nextLine();
        Entrenador jugador = new Entrenador(nombreJugador);

        System.out.print("Ingresa el nombre del segundo entrenador: ");
        String nombreRival = scanner.nextLine();
        Entrenador rival = new Entrenador(nombreRival);

        System.out.println("\n" + nombreJugador + ", ¿quieres seleccionar tu equipo manualmente o aleatorio?");
        System.out.println("1. Manualmente");
        System.out.println("2. Aleatorio");
        System.out.print("Opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        if (opcion == 1) {
            System.out.println("Selecciona tus Pokémon (máximo 3):");
            for (int i = 0; i < disponibles.length; i++) {
                System.out.println((i + 1) + ". " + disponibles[i].getNombre());
            }

            while (jugador.getEquipo().size() < 3) {
                System.out.print("Ingresa el número de un Pokémon: ");
                int eleccion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer después de nextInt()
                if (eleccion >= 1 && eleccion <= disponibles.length) {
                    jugador.agregarPokemon(crearNuevoPokemon(disponibles[eleccion - 1]));
                } else {
                    System.out.println("Selección inválida.");
                }
            }
        } else {
            while (jugador.getEquipo().size() < 3) {
                jugador.agregarPokemon(crearNuevoPokemon(disponibles[random.nextInt(disponibles.length)]));
            }
        }

        while (rival.getEquipo().size() < 3) {
            rival.agregarPokemon(crearNuevoPokemon(disponibles[random.nextInt(disponibles.length)]));
        }

        System.out.println("\n¡Comienza la batalla Pokémon!\n");

        Pokemon pokemonJugador = jugador.getEquipo().get(0);
        Pokemon pokemonRival = rival.getEquipo().get(0);

        while (!jugador.equipoDerrotado() && !rival.equipoDerrotado()) {
            System.out.println("\n--- Estado actual ---");
            System.out.println(jugador.getNombre() + ": " + pokemonJugador.getNombre() + " (HP: " + pokemonJugador.getPuntos_de_salud() + ")");
            System.out.println(rival.getNombre() + ": " + pokemonRival.getNombre() + " (HP: " + pokemonRival.getPuntos_de_salud() + ")\n");

            // Turno del jugador
            System.out.println("Turno de " + pokemonJugador.getNombre() + ": Elige un ataque:");
            for (int i = 0; i < pokemonJugador.getAtaques().size(); i++) {
                System.out.println((i + 1) + ". " + pokemonJugador.getAtaques().get(i).getNombre());
            }
            System.out.print("Opción: ");
            int ataqueJugador = scanner.nextInt() - 1;
            scanner.nextLine(); // Limpiar buffer

            if (ataqueJugador >= 0 && ataqueJugador < pokemonJugador.getAtaques().size()) {
                pokemonJugador.atacar(pokemonRival, ataqueJugador);
            } else {
                System.out.println("Selección inválida. Se pierde el turno.");
            }

            if (pokemonRival.getPuntos_de_salud() <= 0) {
                System.out.println("\n" + pokemonRival.getNombre() + " ha sido derrotado.");
                rival.getEquipo().remove(pokemonRival);
                if (!rival.equipoDerrotado()) {
                    pokemonRival = rival.getEquipo().get(0);
                    System.out.println(rival.getNombre() + " envía a " + pokemonRival.getNombre() + " a la batalla.");
                }
            }

            if (rival.equipoDerrotado()) break;

            // Turno del rival
            System.out.println("\nTurno de " + pokemonRival.getNombre() + ":");
            int ataqueRival = random.nextInt(pokemonRival.getAtaques().size());
            pokemonRival.atacar(pokemonJugador, ataqueRival);

            if (pokemonJugador.getPuntos_de_salud() <= 0) {
                System.out.println("\n" + pokemonJugador.getNombre() + " ha sido derrotado.");
                jugador.getEquipo().remove(pokemonJugador);
                if (!jugador.equipoDerrotado()) {
                    System.out.println("Elige tu próximo Pokémon:");
                    for (int i = 0; i < jugador.getEquipo().size(); i++) {
                        System.out.println((i + 1) + ". " + jugador.getEquipo().get(i).getNombre());
                    }
                    System.out.print("Opción: ");
                    int nuevoPokemon = scanner.nextInt() - 1;
                    scanner.nextLine(); // Limpiar buffer

                    while (nuevoPokemon < 0 || nuevoPokemon >= jugador.getEquipo().size()) {
                        System.out.println("Selección inválida. Intenta de nuevo.");
                        System.out.print("Opción: ");
                        nuevoPokemon = scanner.nextInt() - 1;
                        scanner.nextLine();
                    }
                    
                    pokemonJugador = jugador.getEquipo().get(nuevoPokemon);
                    System.out.println(jugador.getNombre() + " envía a " + pokemonJugador.getNombre() + " a la batalla.");
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

    public static Pokemon crearNuevoPokemon(Pokemon original) {
        return switch (original.getTipo()) {
            case AGUA -> new PokemonAgua(original.getNombre(), original.getPuntos_de_salud(), new ArrayList<>(original.getAtaques()));
            case FUEGO -> new PokemonFuego(original.getNombre(), original.getPuntos_de_salud(), new ArrayList<>(original.getAtaques()));
            case PLANTA -> new PokemonPlanta(original.getNombre(), original.getPuntos_de_salud(), new ArrayList<>(original.getAtaques()));
            case ELECTRICO -> new PokemonElectrico(original.getNombre(), original.getPuntos_de_salud(), new ArrayList<>(original.getAtaques()));
            case HIELO -> new PokemonHielo(original.getNombre(), original.getPuntos_de_salud(), new ArrayList<>(original.getAtaques()));
            case TIERRA -> new PokemonTierra(original.getNombre(), original.getPuntos_de_salud(), new ArrayList<>(original.getAtaques()));
        };
    }
}
