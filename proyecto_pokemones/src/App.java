import models.ataques.Ataque;
import models.pokemones.Pokemon;
import models.pokemones.PokemonFuego;
import models.pokemones.PokemonPlanta;
import models.pokemones.PokemonElectrico;
import models.pokemones.PokemonAgua;


public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");


        Ataque latigoCepa = new Ataque("latigoCepa",10);
        Pokemon roselia = new PokemonPlanta("Roselia", 100, 10, latigoCepa);
        Ataque lanzallamas = new Ataque("lanzallamas", 10);
        Pokemon charmander = new PokemonFuego("Charmander", 100, 10, lanzallamas);
        Ataque trueno = new Ataque("trueno", 10);
        Pokemon pikachu = new PokemonElectrico("Pikachu", 100, 10, trueno);
        Ataque hidrobomba = new Ataque("hidrobomba", 10);
        Pokemon squirtle = new PokemonAgua("Squirtle", 100, 10, hidrobomba);

        squirtle.atacar(squirtle);
        roselia.atacar(roselia);
        charmander.atacar(charmander);
        pikachu.atacar(pikachu);

        
    
    
    }
        
    
        



    }

