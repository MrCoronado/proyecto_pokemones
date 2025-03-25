package models.ataques;
import models.pokemones.Pokemon;

public class Ataque {
    private String nombre;
    private int dano;
    private String tipoDanio; // "Físico" o "Especial"
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public String getTipoDanio() {
        return tipoDanio;
    }

    public void setTipoDanio(String tipoDanio) {
        this.tipoDanio = tipoDanio;
    }

    public String getTipoAtaque() {
        return tipoAtaque;
    }

    public void setTipoAtaque(String tipoAtaque) {
        this.tipoAtaque = tipoAtaque;
    }

    
}
