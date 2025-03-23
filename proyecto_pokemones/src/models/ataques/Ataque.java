package models.ataques;
import models.pokemones.Pokemon;

public class Ataque {
    private String nombre;
    private int dano;

    public Ataque(String nombre, int dano) {
        this.nombre = nombre;
        this.dano = dano;
    }

    public void ejecutar(Pokemon atacante, Pokemon enemigo) {
        System.out.println(atacante.getNombre() + " atacando a " + enemigo.getNombre() + " con " + nombre + " y causando " + dano + " puntos de daño");
        enemigo.setPuntos_de_salud(enemigo.getPuntos_de_salud() - dano);
    }

    
    
}
