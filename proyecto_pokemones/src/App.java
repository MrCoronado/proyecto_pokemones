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

        // Pokemones
        Pokemon[] disponibles = {
            new PokemonPlanta("Roselia", 100, 10, latigoCepa),
            new PokemonFuego("Charmander", 100, 10, lanzallamas),
            new PokemonElectrico("Pikachu", 100, 10, trueno),
            new PokemonAgua("Squirtle", 100, 10, hidrobomba)
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

        if (opcion == 1) {
            System.out.println("Selecciona tus Pokémon (máximo 3):");
            for (int i = 0; i < disponibles.length; i++) {
                System.out.println((i + 1) + ". " + disponibles[i].getNombre());
            }

            while (jugador.getEquipo().size() < 3) {
                System.out.print("Ingresa el número de un Pokémon: ");
                int eleccion = scanner.nextInt();
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
            System.out.println("Turno de " + pokemonJugador.getNombre() + ": Presiona ENTER para atacar...");
            scanner.nextLine();
            scanner.nextLine();
            pokemonJugador.atacar(pokemonRival);

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
            pokemonRival.atacar(pokemonJugador);

            if (pokemonJugador.getPuntos_de_salud() <= 0) {
                System.out.println("\n" + pokemonJugador.getNombre() + " ha sido derrotado.");
                jugador.getEquipo().remove(pokemonJugador);
                if (!jugador.equipoDerrotado()) {
                    System.out.println("Elige tu próximo Pokémon:");
                    for (int i = 0; i < jugador.getEquipo().size(); i++) {
                        System.out.println((i + 1) + ". " + jugador.getEquipo().get(i).getNombre());
                    }
                    System.out.print("Opción: ");
                    pokemonJugador = jugador.getEquipo().get(scanner.nextInt() - 1);
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

    
    /*
     * Este metodo funciona como solucion al problema cuando pelean dos pokemones iguales 
     * (crea otra instancia del pokemon y java los toma como diferente para que no se hagan el doble de daño)
     */
    public static Pokemon crearNuevoPokemon(Pokemon original) {
        return switch (original.getTipo()) {
            case AGUA -> new PokemonAgua(original.getNombre(), original.getPuntos_de_salud(), original.getAtaques(),
                    new Ataque(original.getAtaque().getNombre(), original.getAtaque().getDano(), original.getAtaque().getTipoDanio(), original.getAtaque().getTipoAtaque()));
            case FUEGO -> new PokemonFuego(original.getNombre(), original.getPuntos_de_salud(), original.getAtaques(),
                    new Ataque(original.getAtaque().getNombre(), original.getAtaque().getDano(), original.getAtaque().getTipoDanio(), original.getAtaque().getTipoAtaque()));
            case PLANTA -> new PokemonPlanta(original.getNombre(), original.getPuntos_de_salud(), original.getAtaques(),
                    new Ataque(original.getAtaque().getNombre(), original.getAtaque().getDano(), original.getAtaque().getTipoDanio(), original.getAtaque().getTipoAtaque()));
            case ELECTRICO -> new PokemonElectrico(original.getNombre(), original.getPuntos_de_salud(), original.getAtaques(),
                    new Ataque(original.getAtaque().getNombre(), original.getAtaque().getDano(), original.getAtaque().getTipoDanio(), original.getAtaque().getTipoAtaque()));
        };
    }
}



