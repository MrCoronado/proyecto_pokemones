package models.pokemones;
import models.ataques.Ataque;
import java.util.List;
import java.util.ArrayList;

public abstract class Pokemon {
    public enum TipoPokemon { 
        AGUA,
        FUEGO,
        PLANTA,
        ELECTRICO,
        HIELO,
        TIERRA;
    }

    protected String nombre;
    protected int puntos_de_salud;
    protected TipoPokemon tipo;
    protected List<Ataque> ataques = new ArrayList<>();
    
    public Pokemon(String nombre, int puntos_de_salud, TipoPokemon tipo, List<Ataque> ataques) {
            this.nombre = nombre;
            this.puntos_de_salud = puntos_de_salud;
            this.tipo = tipo;
            this.ataques = ataques;
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

    public List<Ataque> getAtaques() {
        return ataques;
    }

    public TipoPokemon getTipo() {
        return tipo;
    }

    public void setAtaques(List<Ataque> ataques) {
        this.ataques = ataques;
    }

    public void atacar(Pokemon enemigo, int indice) {
        if (indice < 0 || indice >= ataques.size()) {
            System.out.println("Índice de ataque inválido.");
            return;
        }
        Ataque ataqueSeleccionado = ataques.get(indice);
        int danioFinal = calcularDanio(ataqueSeleccionado, enemigo);
        
        System.out.println(nombre + " ataca a " + enemigo.getNombre() + " con " + ataqueSeleccionado.getNombre() +
            ", causando " + danioFinal + " puntos de daño.");
        enemigo.setPuntos_de_salud(enemigo.getPuntos_de_salud() - danioFinal);
    }

    // Ventajas de tipo
    public static boolean tieneVentaja(TipoPokemon atacante, TipoPokemon defensor) {
        return (atacante == TipoPokemon.FUEGO && (defensor == TipoPokemon.PLANTA || defensor == TipoPokemon.HIELO)) ||
               (atacante == TipoPokemon.AGUA && (defensor == TipoPokemon.FUEGO || defensor == TipoPokemon.TIERRA)) ||
               (atacante == TipoPokemon.PLANTA && (defensor == TipoPokemon.AGUA || defensor == TipoPokemon.TIERRA)) ||
               (atacante == TipoPokemon.ELECTRICO && (defensor == TipoPokemon.AGUA || defensor == TipoPokemon.HIELO)) ||
               (atacante == TipoPokemon.HIELO && (defensor == TipoPokemon.PLANTA || defensor == TipoPokemon.TIERRA)) ||
               (atacante == TipoPokemon.TIERRA && (defensor == TipoPokemon.FUEGO || defensor == TipoPokemon.ELECTRICO));
    }
    //Metodo recibir dano
    public void recibirDanio(int danio) {
        this.puntos_de_salud -= danio;
        if (this.puntos_de_salud < 0) {
            this.puntos_de_salud = 0; // No permitir valores negativos
        }
    }

    // 🔹 Método faltante: calcular el daño del ataque
    public int calcularDanio(Ataque ataqueSeleccionado, Pokemon enemigo) {
        double multiplicador = 1.0;
        if (tieneVentaja(this.tipo, enemigo.getTipo())) {
            multiplicador = 1.3; // Daño extra si el tipo tiene ventaja
        }
        return (int)(ataqueSeleccionado.getDano() * multiplicador);
    }
}


