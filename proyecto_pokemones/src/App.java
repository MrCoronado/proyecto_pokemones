import models.ataques.Ataque;
import models.entrenadores.Entrenador;
import models.pokemones.*;
import java.util.*;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Ataques
        Ataque latigoCepa = new Ataque("Látigo Cepa", 10, "Especial", "Planta");
        Ataque lanzallamas = new Ataque("Lanzallamas", 10, "Especial", "Fuego");
        Ataque trueno = new Ataque("Trueno", 10, "Especial", "Electrico");
        Ataque hidrobomba = new Ataque("Hidrobomba", 10, "Especial", "Agua");
        Ataque rayoHielo = new Ataque("Rayo Hielo", 10, "Especial", "Hielo");
        Ataque terremoto = new Ataque("Terremoto", 10, "Especial", "Tierra");

        // Pokemones con atributos completos
        Pokemon[] disponibles = {
            new PokemonPlanta("Roselia", 100, List.of(latigoCepa), 50, 45, 65, 50, 60),
            new PokemonFuego("Charmander", 100, List.of(lanzallamas), 52, 43, 60, 50, 65),
            new PokemonElectrico("Pikachu", 100, List.of(trueno), 55, 40, 50, 50, 90),
            new PokemonAgua("Squirtle", 100, List.of(hidrobomba), 48, 65, 50, 64, 43),
            new PokemonHielo("Articuno", 100, List.of(rayoHielo), 85, 100, 95, 125, 85),
            new PokemonTierra("Garchomp", 100, List.of(terremoto), 130, 95, 80, 85, 102)
        };
        

        System.out.print("Ingresa el nombre del primer entrenador: ");
        String nombreJugador = scanner.nextLine();
        Entrenador jugador = new Entrenador(nombreJugador);
        seleccionarEquipoAleatorio(jugador, disponibles);

        System.out.print("Ingresa el nombre del segundo entrenador: ");
        String nombreRival = scanner.nextLine();
        Entrenador rival = new Entrenador(nombreRival);
        seleccionarEquipoAleatorio(rival, disponibles);

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

    /*Este metodo funciona como solucion al problema cuando pelean dos pokemones iguales
     * (crea otra instancia del pokemon y java los toma com difertentes para que no hagan doble daño)
     */
    public static Pokemon crearNuevoPokemon(Pokemon original) {
        return switch (original.getTipo()) {
            case PLANTA -> new PokemonPlanta(
                original.getNombre(), original.getPuntos_de_salud(), original.getAtaques(),
                original.getAtaque(), original.getDefensa(), original.getAtaqueEspecial(),
                original.getDefensaEspecial(), original.getVelocidad());
    
            case FUEGO -> new PokemonFuego(
                original.getNombre(), original.getPuntos_de_salud(), original.getAtaques(),
                original.getAtaque(), original.getDefensa(), original.getAtaqueEspecial(),
                original.getDefensaEspecial(), original.getVelocidad());
    
            case ELECTRICO -> new PokemonElectrico(
                original.getNombre(), original.getPuntos_de_salud(), original.getAtaques(),
                original.getAtaque(), original.getDefensa(), original.getAtaqueEspecial(),
                original.getDefensaEspecial(), original.getVelocidad());
    
            case AGUA -> new PokemonAgua(
                original.getNombre(), original.getPuntos_de_salud(), original.getAtaques(),
                original.getAtaque(), original.getDefensa(), original.getAtaqueEspecial(),
                original.getDefensaEspecial(), original.getVelocidad());
    
            case HIELO -> new PokemonHielo(
                original.getNombre(), original.getPuntos_de_salud(), original.getAtaques(),
                original.getAtaque(), original.getDefensa(), original.getAtaqueEspecial(),
                original.getDefensaEspecial(), original.getVelocidad());
    
            case TIERRA -> new PokemonTierra(
                original.getNombre(), original.getPuntos_de_salud(), original.getAtaques(),
                original.getAtaque(), original.getDefensa(), original.getAtaqueEspecial(),
                original.getDefensaEspecial(), original.getVelocidad());
        };
    }
}