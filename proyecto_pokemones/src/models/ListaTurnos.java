package models;

import models.pokemones.Pokemon;

public class ListaTurnos {

    private Nodo inicio; // Nodo inicial de la lista

    private class Nodo { 
        Pokemon pokemon;
        Nodo siguiente;

        Nodo(Pokemon pokemon) { 
            this.pokemon = pokemon;
            this.siguiente = null;
        }
    }

    // Agrega un Pokémon ordenado por velocidad (mayor primero)
    public void agregarPorVelocidad(Pokemon nuevo) {
        Nodo nuevoNodo = new Nodo(nuevo);

        if (inicio == null || nuevo.getVelocidad() > inicio.pokemon.getVelocidad()) {
            nuevoNodo.siguiente = inicio;
            inicio = nuevoNodo;
        } else {
            Nodo actual = inicio;
            while (actual.siguiente != null && actual.siguiente.pokemon.getVelocidad() >= nuevo.getVelocidad()) {
                actual = actual.siguiente;
            }
            nuevoNodo.siguiente = actual.siguiente;
            actual.siguiente = nuevoNodo;
        }
    }

    // Devuelve el Pokémon mas rapido (el primero de la lista)
    public Pokemon obtenerPrimero() {
        return (inicio != null) ? inicio.pokemon : null;  //Si inicio != null, devuelve el pokemon del primer nodo, sino devuelve null. (? es un if-else)
    }

    // Eliminar y devolver el primer Pokémon de la lista
    public Pokemon removerPrimero() {
        if (inicio == null) return null;
        Pokemon primero = inicio.pokemon;
        inicio = inicio.siguiente;
        return primero;
    }

    // Verificar si la lista está vacía
    public boolean estaVacia() {
        return inicio == null;
    }
}
