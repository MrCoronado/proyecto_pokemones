package vista;

import models.pokemones.*;
//import java.util.Scanner;


public interface Vista {

    void mostrarMensaje(String mensaje);
    int pedirOpcionAtaque(Pokemon atacante);
    void mostrarEstadoPokemon(String nombreEntrenador, Pokemon pokemon);
} 


