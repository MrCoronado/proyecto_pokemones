package models.pokemones;
import models.ataques.Ataque;
import java.util.List;
import java.util.ArrayList;

public class Pokemon { 
    public enum TipoPokemon {
        AGUA,
        FUEGO,
        PLANTA,
        ELECTRICO,
        HIELO,
        TIERRA;
    }

    private String nombre;
    private int puntos_de_salud;
    private TipoPokemon tipo;
    private List<Ataque> ataques = new ArrayList<>();
    private int ataque;
    private int defensa;
    private int ataqueEspecial;
    private int defensaEspecial;
    private int velocidad;

    public Pokemon(String nombre, int puntos_de_salud, TipoPokemon tipo, List<Ataque> ataques, int ataque, int defensa, int ataqueEspecial, int defensaEspecial, int velocidad) {
        this.nombre = nombre;
        this.puntos_de_salud = puntos_de_salud;
        this.tipo = tipo;
        this.ataques = ataques;
        this.ataque = ataque;
        this.defensa = defensa;
        this.ataqueEspecial = ataqueEspecial;
        this.defensaEspecial = defensaEspecial;
        this.velocidad = velocidad;
    }

    public String toString(){
        return nombre + "(HP: " + puntos_de_salud + ", Tipo: " + tipo + ", Ataque: " + ataque + ", Defensa: " + defensa +
                ", Ataque Especial: " + ataqueEspecial + ", Defensa Especial: " + defensaEspecial +
                ", Velocidad: " + velocidad + ")";
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getPuntos_de_salud() { return puntos_de_salud; }
    public void setPuntos_de_salud(int puntos_de_salud) { this.puntos_de_salud = puntos_de_salud; }

    public List<Ataque> getAtaques() { return ataques; }
    public void setAtaques(List<Ataque> ataques) { this.ataques = ataques; }

    public TipoPokemon getTipo() { return tipo; }

    public int getAtaque() { return ataque; }
    public void setAtaque(int ataque) { this.ataque = ataque; }

    public int getDefensa() { return defensa; }
    public void setDefensa(int defensa) { this.defensa = defensa; }

    public int getAtaqueEspecial() { return ataqueEspecial; }
    public void setAtaqueEspecial(int ataqueEspecial) { this.ataqueEspecial = ataqueEspecial; }

    public int getDefensaEspecial() { return defensaEspecial; }
    public void setDefensaEspecial(int defensaEspecial) { this.defensaEspecial = defensaEspecial; }

    public int getVelocidad() { return velocidad; }
    public void setVelocidad(int velocidad) { this.velocidad = velocidad; }


    public String atacar(Pokemon enemigo, int indice) {
        if (indice < 0 || indice >= ataques.size()) {
            return "Índice de ataque inválido.";
        }
        Ataque ataqueSeleccionado = ataques.get(indice);
        return ataqueSeleccionado.aplicarAtaque(this, enemigo); 
    }

    public static boolean tieneVentaja(TipoPokemon atacante, TipoPokemon defensor) {
        return (atacante == TipoPokemon.FUEGO && (defensor == TipoPokemon.PLANTA || defensor == TipoPokemon.HIELO)) ||
               (atacante == TipoPokemon.AGUA && (defensor == TipoPokemon.FUEGO || defensor == TipoPokemon.TIERRA)) ||
               (atacante == TipoPokemon.PLANTA && (defensor == TipoPokemon.AGUA || defensor == TipoPokemon.TIERRA)) ||
               (atacante == TipoPokemon.ELECTRICO && (defensor == TipoPokemon.AGUA || defensor == TipoPokemon.HIELO)) ||
               (atacante == TipoPokemon.HIELO && (defensor == TipoPokemon.PLANTA || defensor == TipoPokemon.TIERRA)) ||
               (atacante == TipoPokemon.TIERRA && (defensor == TipoPokemon.FUEGO || defensor == TipoPokemon.ELECTRICO));
    }

    public void recibirDanio(int danio) {
    if (danio < 0) {
        danio = 0; // Evitar daño negativo que "cure" (por si acaso)
    }

    this.puntos_de_salud -= danio;

    if (this.puntos_de_salud < 0) {
        this.puntos_de_salud = 0;
    }
}

    public int calcularDanio(Ataque ataqueSeleccionado, Pokemon enemigo) {
    int nivel = 50;
    int potencia = ataqueSeleccionado.getDano();

    int statAtaque, statDefensa;  
    if (ataqueSeleccionado.getTipoDanio().equalsIgnoreCase("Fisico")) { 
        statAtaque = this.ataque; 
        statDefensa = enemigo.defensa; 
    } else { 
        statAtaque = this.ataqueEspecial;
        statDefensa = enemigo.defensaEspecial;
    }

    // Evitar división por cero 
    if (statDefensa == 0) {
        statDefensa = 1;
    }

    // Fórmula base de daño
    double baseDanio = (((2 * nivel / 5.0 + 2) * potencia * ((double)statAtaque / statDefensa)) / 50.0) + 2; 

    // Multiplicador de ventaja de tipo
    double multiplicador = 1.0;
    if (tieneVentaja(this.tipo, enemigo.getTipo())) {
        multiplicador = 1.3;
    } else if (tieneVentaja(enemigo.getTipo(), this.tipo)) {
        multiplicador = 0.7;
    }

    return (int)(baseDanio * multiplicador);
}

    public String calcularMensajeEfectividad(Pokemon enemigo) {
        if (tieneVentaja(this.tipo, enemigo.getTipo())) {
            return " ¡Es súper efectivo!";
        } else if (tieneVentaja(enemigo.getTipo(), this.tipo)) {
            return " No es muy efectivo..";
        }
        return "";
    }
    
}

