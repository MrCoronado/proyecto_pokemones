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
    protected int ataque;
    protected int defensa;
    protected int ataqueEspecial;
    protected int defensaEspecial;
    protected int velocidad;
    
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
    protected int ataque;
    protected int defensa;
    protected int ataqueEspecial;
    protected int defensaEspecial;
    protected int velocidad;

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

    public String atacarConAtaque(Pokemon objetivo, Ataque ataque) {
        int danio = calcularDanio(ataque, objetivo);
        objetivo.recibirDanio(danio);
        return this.getNombre() + " usa " + ataque.getNombre() + " contra " + objetivo.getNombre() + " y causa " + danio + " de daño.\n";
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getAtaqueEspecial() {
        return ataqueEspecial;
    }

    public void setAtaqueEspecial(int ataqueEspecial) {
        this.ataqueEspecial = ataqueEspecial;
    }

    public int getDefensaEspecial() {
        return defensaEspecial;
    }

    public void setDefensaEspecial(int defensaEspecial) {
        this.defensaEspecial = defensaEspecial;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getVelocidad() { return velocidad; }
    public void setVelocidad(int velocidad) { this.velocidad = velocidad; }


    public String atacar(Pokemon enemigo, int indice) {
        if (indice < 0 || indice >= ataques.size()) {
            return "Índice de ataque inválido.";
        }
        Ataque ataqueSeleccionado = ataques.get(indice);
        int danioFinal = calcularDanio(ataqueSeleccionado, enemigo);
        
        System.out.println(nombre + " ataca a " + enemigo.getNombre() + " con " + ataqueSeleccionado.getNombre() +
            ", causando " + danioFinal + " puntos de daño.");
            
            enemigo.recibirDanio(danioFinal);
        }

    // Ventajas de tipo
        ataqueSeleccionado.aplicarAtaque(this, enemigo); 
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
        this.puntos_de_salud -= danio;
        if (this.puntos_de_salud < 0) {
            this.puntos_de_salud = 0;
        }
    }

    // Método para calcular el daño por ataque
    public int calcularDanio(Ataque ataqueSeleccionado, Pokemon enemigo) {
        int nivel = 50;
        int potencia = ataqueSeleccionado.getDano();
    
        // Stats correspondientes según el tipo de ataque
        int statAtaque, statDefensa;  
        if (ataqueSeleccionado.getTipoDanio().equalsIgnoreCase("Fisico")) { 
            statAtaque = this.ataque; 
            statDefensa = enemigo.defensa; 
        } else { 
            statAtaque = this.ataqueEspecial;
            statDefensa = enemigo.defensaEspecial;
        }
    
        // Fórmula base de daño
        double baseDanio = (((2 * nivel / 5.0 + 2) * potencia * ((double)statAtaque / statDefensa)) / 50.0) + 2; 
    
        // Multiplicador de ventaja de tipo
        double multiplicador = 1.0; // 1.0 (sin ventaja)
        if (tieneVentaja(this.tipo, enemigo.getTipo())) {
            multiplicador = 1.3; // Aumenta el daño en un 30%
            System.out.println("¡Es súper efectivo!"); 
        } else if (tieneVentaja(enemigo.getTipo(), this.tipo)) {
            multiplicador = 0.7; // Reduce el daño en un 30%
            System.out.println("No es muy efectivo...");
        }
    
        return (int)(baseDanio * multiplicador);
    }
    
}

