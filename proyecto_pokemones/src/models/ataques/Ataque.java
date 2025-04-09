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

    public void aplicarAtaque(Pokemon objetivo) {
        System.out.println(objetivo.getNombre() + " recibe " + dano + " de daño por " + nombre);
        objetivo.recibirDanio(dano);
    }
}
