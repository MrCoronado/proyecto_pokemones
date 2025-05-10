package models.ataques;
import models.pokemones.Pokemon;

public class Ataque {
    private String nombre;
    private int dano;
    private String tipoDanio; // "Físico" y "Especial"
    private String tipoAtaque; // "Fuego", "Agua", etc.

    public Ataque(String nombre, int dano, String tipoDanio, String tipoAtaque) {
        this.nombre = nombre;
        this.dano = dano;
        this.tipoDanio = tipoDanio;
        this.tipoAtaque = tipoAtaque;
    }
    public String getNombre() {
        return nombre;
    }
    public int getDano() {
        return dano;
    }
    public String getTipoDanio() {
        return tipoDanio;
    }
    public String getTipoAtaque() {
        return tipoAtaque;
    }
    
    public Ataque(Ataque otro) {
    this.nombre = otro.nombre;
    this.dano = otro.dano;
    this.tipoDanio = otro.tipoDanio;
    this.tipoAtaque = otro.tipoAtaque;
}
    // Método para calcular el daño
    // basado en el tipo de ataque y el tipo del Pokémon objetivo
    public String aplicarAtaque(Pokemon atacante,Pokemon objetivo) {
        int danio = atacante.calcularDanio(this, objetivo);
        objetivo.recibirDanio(danio);

        String efectividad = atacante.calcularMensajeEfectividad(objetivo);
        return atacante.getNombre() + " usa " + nombre + 
        " contra " + objetivo.getNombre() + 
        " y causa " + danio + " de daño" + efectividad;      
    }
}
