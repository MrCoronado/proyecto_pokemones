package models.bases_de_datos;

import models.entrenadores.Entrenador;
import models.pokemones.Pokemon;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Persistencia {

    private static final String ARCHIVO_POKEMONES = "src/models/bases_de_datos/pokemones.txt";

    public static List<Pokemon> leerPokemones(){
        List<Pokemon> lista = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_POKEMONES))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                Pokemon pokemon = Pokemon.deserializar(linea);
                lista.add(pokemon);
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        }
        return lista;
    }

    public static void guardarEntrenador(Entrenador entrenador, String nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write(entrenador.getNombre());
            writer.newLine();
            for (Pokemon p : entrenador.getEquipo()) {
                writer.write(p.serializar());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar archivo: " + e.getMessage());
        }
    }

    public static Entrenador cargarEntrenador(String nombreArchivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String nombreEntrenador = reader.readLine();
            Entrenador entrenador = new Entrenador(nombreEntrenador);

            String linea;
            while ((linea = reader.readLine()) != null) {
                Pokemon p = Pokemon.deserializar(linea);
                entrenador.agregarPokemon(p);
            }

            return entrenador;
        } catch (IOException e) {
            System.out.println("Error al cargar archivo: " + e.getMessage());
        }
        return null;
    }
}