package models.pokemones;

import models.ataques.Ataque;

public abstract class Pokemon {
    public enum TipoPokemon { 
        AGUA,
        FUEGO,
        PLANTA,
        ELECTRICO;
    }

    protected String nombre;
    protected int puntos_de_salud;
    protected int ataques;
    protected TipoPokemon tipo;
    protected Ataque ataque;

    public Pokemon(String nombre, int puntos_de_salud, int ataques, TipoPokemon tipo, Ataque ataque) {
        this.nombre = nombre;
        this.puntos_de_salud = puntos_de_salud;
        this.ataques = ataques;
        this.tipo = tipo;
        this.ataque = ataque;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntos_de_salud() {
        return puntos_de_salud;
    }

    public void setPuntos_de_salud(int puntos_de_salud) {
        this.puntos_de_salud = puntos_de_salud;
    }

    public int getAtaques() {
        return ataques;
    }

    public void setAtaques(int ataques) {
        this.ataques = ataques;
    }

    public TipoPokemon getTipo() {
        return tipo;
    }

    public void setTipo(TipoPokemon tipo) {
        this.tipo = tipo;
    }

    public Ataque getAtaque() {
        return ataque;
    }

    public void setAtaque(Ataque ataque) {
        this.ataque = ataque;
    }


    public void atacar(Pokemon enemigo) {
        int danioFinal = calcularDanio(enemigo);
        System.out.println(nombre + " ataca a " + enemigo.getNombre() + " con " + ataque.getNombre() +
                ", causando " + danioFinal + " puntos de daño.");
        enemigo.setPuntos_de_salud(enemigo.getPuntos_de_salud() - danioFinal);
    }

    // Ventajas de tipo
    public static boolean tieneVentaja(TipoPokemon atacante, TipoPokemon defensor) {
        return (atacante == TipoPokemon.FUEGO && defensor == TipoPokemon.PLANTA) ||
               (atacante == TipoPokemon.AGUA && defensor == TipoPokemon.FUEGO) ||
               (atacante == TipoPokemon.PLANTA && defensor == TipoPokemon.AGUA) ||
               (atacante == TipoPokemon.ELECTRICO && defensor == TipoPokemon.AGUA);
    }

    public int calcularDanio(Pokemon enemigo) {
        double multiplicador = 1.0;
        if (tieneVentaja(this.tipo, enemigo.getTipo())) {
            multiplicador = 1.3;
        }
        return (int)(ataque.getDano() * multiplicador);
    }
}


