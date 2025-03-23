package models.pokemones;

public abstract class Pokemon {
    protected String nombre;
    protected int puntos_de_salud;
    protected int ataques;

    public Pokemon(String nombre, int puntos_de_salud, int ataques) {
        this.nombre = nombre;
        this.puntos_de_salud = puntos_de_salud;
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

    public int getAtaques() {
        return ataques;
    }

    public void setAtaques(int ataques) {
        this.ataques = ataques;
    }

    public abstract void atacar(Pokemon enemigo);
    


    
}
