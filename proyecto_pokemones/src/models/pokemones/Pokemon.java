package models.pokemones;
import models.ataques.Ataque;
import java.util.List;
import java.util.ArrayList;

public abstract class Pokemon { 
    public enum TipoPokemon { //Enum de los tipos de pokemones
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

    public String atacarConAtaque(Pokemon objetivo, Ataque ataque) {
        int danio = calcularDanio(ataque, objetivo);
        objetivo.recibirDanio(danio);
        return this.getNombre() + " usa " + ataque.getNombre() + " contra " + objetivo.getNombre() + " y causa " + danio + " de daño.\n";
    }


    public void atacar(Pokemon enemigo, int indice) {
        if (indice < 0 || indice >= ataques.size()) {
            System.out.println("Índice de ataque inválido.");
            return;
        }
        Ataque ataqueSeleccionado = ataques.get(indice);
        ataqueSeleccionado.aplicarAtaque(this, enemigo); // Llama al método aplicarAtaque de la clase Ataque
       /*  int danioFinal = calcularDanio(ataqueSeleccionado, enemigo);
        ataqueSeleccionado.aplicarAtaque(this, enemigo); // Llama al método aplicarAtaque de la clase Ataque
       /*  int danioFinal = calcularDanio(ataqueSeleccionado, enemigo);
        
        System.out.println(nombre + " ataca a " + enemigo.getNombre() + " con " + ataqueSeleccionado.getNombre() +
            ", causando " + danioFinal + " puntos de daño.");
<<<<<<< Updated upstream
        enemigo.setPuntos_de_salud(enemigo.getPuntos_de_salud() - danioFinal);
    }
=======
            
            enemigo.recibirDanio(danioFinal);
            */
        
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


    public int calcularDanio(Ataque ataqueSeleccionado, Pokemon enemigo) {
        double multiplicador = 1.0;
        if (tieneVentaja(this.tipo, enemigo.getTipo())) {
            multiplicador = 1.3; // Daño extra si el tipo tiene ventaja
        }
        return (int)(ataqueSeleccionado.getDano() * multiplicador);
    }
}


